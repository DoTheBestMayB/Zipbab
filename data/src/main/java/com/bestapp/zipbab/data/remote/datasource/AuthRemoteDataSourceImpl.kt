package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.auth.AuthStateDto
import com.bestapp.zipbab.data.model.remote.auth.SignOutForbiddenResponse
import com.bestapp.zipbab.data.model.remote.auth.SignUpRequest
import com.bestapp.zipbab.data.model.remote.user.TemperatureResponse
import com.bestapp.zipbab.data.model.remote.user.UserPrivateResponse
import com.bestapp.zipbab.data.model.remote.user.UserResponse
import com.bestapp.zipbab.data.networking.safeFirebaseCall
import com.bestapp.zipbab.data.remote.firestoreDB.FirestoreDB
import com.bestapp.zipbab.data.remote.util.doneSuccessful
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Transaction
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.ZonedDateTime
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val firestoreDB: FirestoreDB,
) : AuthRemoteDataSource {

    private var renewAuth: (isVerified: Boolean) -> Unit = {}

    override suspend fun login(id: String, pw: String): Result<Boolean, NetworkError> {
        return safeFirebaseCall<Boolean> {
            firestoreDB.getUserPrivateDB()
                .whereEqualTo("id", id)
                .whereEqualTo("pw", pw)
                .get()
                .await()
                .isEmpty
                .not()
        }
    }

    override suspend fun signUpUser(signUpRequest: SignUpRequest): Result<AuthStateDto, NetworkError> {
        return safeFirebaseCall {
            // 아이디 중복 가입 확인
            val existingUsers = firestoreDB.getUserDB()
                .whereEqualTo("id", signUpRequest.id)
                .get()
                .await()

            if (existingUsers.isEmpty.not()) {
                return Result.Success(AuthStateDto.AlreadyUsedEmail)
            }

            // 계정 등록
            val userPrivate = with(signUpRequest) {
                UserPrivateResponse(
                    id = id,
                    pw = pw,
                    email = email,
                    phone = phone,
                    notifications = notifications,
                    joinedMeetings = joinedMeetings
                )
            }
            val user = with(signUpRequest) {
                UserResponse(
                    id = id,
                    nickname = nickname,
                    temperatures = listOf(TemperatureResponse(recordedAt = ZonedDateTime.now())),
                )
            }
            val res = firestoreDB.runTransaction { transaction: Transaction ->
                val userDocRef = firestoreDB.getUserDB().document()
                transaction.set(userDocRef, user)

                val userPrivateRef = firestoreDB.getUserPrivateDB().document()
                transaction.set(userPrivateRef, userPrivate)
            }.doneSuccessful()

            if (res) {
                AuthStateDto.Success
            } else {
                AuthStateDto.Fail
            }
        }
    }

    override suspend fun checkSignOutIsNotAllowed(userId: String): Result<Boolean, NetworkError> {
        return safeFirebaseCall {
            val documentSnapShot = firestoreDB.getPolicyDB()
                .document("ForbiddenForDelete")
                .get()
                .await()

            val notAllowedIds = documentSnapShot.toObject<SignOutForbiddenResponse>()?.notAllowedIds
            notAllowedIds?.contains(userId) ?: false
        }
    }

    override suspend fun signOutUser(userId: String): Result<Boolean, NetworkError> {
        return safeFirebaseCall {
            firestoreDB.getUserDB().document(userId)
                .delete()
                .doneSuccessful()
        }
    }

    override suspend fun sendCodeToEmail(
        email: String,
        password: String
    ): Result<AuthStateDto, NetworkError> {
        return safeFirebaseCall {
            val user = Firebase.auth.currentUser

            if (user == null) {
                return@safeFirebaseCall try {
                    // 사용자가 입력한 메일로 User를 생성한다.
                    val isSuccess =
                        Firebase.auth.createUserWithEmailAndPassword(email, password)
                            .doneSuccessful()
                    if (isSuccess) {
                        // 사용자 생성에 성공하면 인증 메일을 보낸다.
                        val sendEmailResult =
                            Firebase.auth.currentUser?.sendEmailVerification()?.doneSuccessful()
                                ?: false
                        if (sendEmailResult) {
                            AuthStateDto.Success
                        } else {
                            AuthStateDto.FailAtSendVerificationEmail
                        }
                    } else {
                        AuthStateDto.Fail
                    }
                } catch (e: FirebaseAuthUserCollisionException) { // 이미 가입된 사용자인 경우
                    AuthStateDto.AlreadyUsedEmail
                } catch (e: FirebaseAuthWeakPasswordException) { // 비밀번호가 6자리보다 짭은 경우
                    AuthStateDto.PasswordTooShort
                }
            } else { // 이미 등록된 계정에서 이메일을 변경하는 경우
                if (user.email == email) { // 현재 등록된 메일과 동일한 메일을 입력한 경우
                    return@safeFirebaseCall AuthStateDto.AlreadyUsedEmail
                }
                try {
                    // 새로운 메일로 인증 메일을 보낸다.
                    val isSuccess = user.verifyBeforeUpdateEmail(email).doneSuccessful()
                    return@safeFirebaseCall if (isSuccess) {
                        AuthStateDto.Success
                    } else {
                        AuthStateDto.Fail
                    }
                } catch (_: FirebaseAuthInvalidUserException) {
                    resetAuthState()
                    return@safeFirebaseCall AuthStateDto.Fail
                } catch (_: FirebaseAuthRecentLoginRequiredException) { // https://firebase.google.com/docs/auth/android/manage-users?hl=en#re-authenticate_a_user
                    // 로그인한지 시간이 많이 경과된 경우, 이메일을 변경하기 위해서는 로그인 상태를 갱신해야 합니다.
                    val credential = EmailAuthProvider.getCredential(user.email ?: "", password)

                    // 재인증 이후에도 실패할 경우 Server 단에서 처리하도록 Fail을 리턴했습니다.
                    return@safeFirebaseCall try {
                        user.reauthenticate(credential).await()
                        val isSuccess = user.verifyBeforeUpdateEmail(email).doneSuccessful()
                        if (isSuccess) {
                            AuthStateDto.Success
                        } else {
                            AuthStateDto.Fail
                        }
                    } catch (_: Exception) {
                        AuthStateDto.Fail
                    }
                }
            }
        }
    }

    override fun resetAuthState() {
        Firebase.auth.signOut()
    }

    override fun getEmailAuthState(email: String): Flow<Boolean> {
        return callbackFlow {
            val authStateListener = FirebaseAuth.AuthStateListener {
                // 이메일 인증 여부에 대한 최신 정보를 얻기 위해 reload 함수를 반드시 호출해야 한다.
                Firebase.auth.currentUser?.reload()?.addOnCompleteListener {
                    val isVerified = Firebase.auth.currentUser?.let { user ->
                        user.email == email && user.isEmailVerified
                    } ?: false
                    trySend(isVerified)
                }
            }
            renewAuth = { isVerified -> trySend(isVerified) }

            Firebase.auth.addAuthStateListener(authStateListener)
            awaitClose {
                Firebase.auth.removeAuthStateListener(authStateListener)
            }
        }
    }

    override suspend fun renewAuthState(email: String, password: String) {
        // 다른 이메일로 변경하면 currentUser는 null이 되기 때문에 재로그인 필요
        try {
            Firebase.auth.currentUser?.reload()?.await() ?: run {
                Firebase.auth.signInWithEmailAndPassword(email, password).await()
            }
        } catch (_: FirebaseAuthInvalidUserException) {
            try {
                Firebase.auth.signInWithEmailAndPassword(email, password).await()
            } catch (_: FirebaseAuthInvalidUserException) {
                renewAuth(false)
                return
            }
        }

        val isVerified = Firebase.auth.currentUser?.let { user ->
            user.email == email && user.isEmailVerified
        } ?: false
        renewAuth(isVerified)
    }

    override suspend fun removeAuth(password: String): Result<Boolean, NetworkError> {
        return safeFirebaseCall {
            Firebase.auth.currentUser?.let { user ->
                try {
                    user.delete().doneSuccessful()
                } catch (_: FirebaseAuthInvalidUserException) {
                    true
                } catch (e: FirebaseAuthRecentLoginRequiredException) { // https://firebase.google.com/docs/auth/android/manage-users?hl=en#re-authenticate_a_user
                    val email = user.email ?: ""
                    val credential = EmailAuthProvider.getCredential(email, password)

                    // 재인증 이후에도 실패할 경우 Server 단에서 처리하도록 pass
                    try {
                        user.reauthenticate(credential).await()
                        user.delete().doneSuccessful()
                    } catch (_: Exception) {
                        // TODO : Firebase Analytics 기록
                        true
                    }
                }
            } ?: true
        }
    }
}
