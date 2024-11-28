package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.local.datasource.UserPrivateLocalDataSource
import com.bestapp.zipbab.data.mapper.toDomain
import com.bestapp.zipbab.data.model.remote.auth.SignUpRequest
import com.bestapp.zipbab.data.remote.datasource.AuthRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.UserPrivateRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.UserRemoteDataSource
import com.bestapp.zipbab.domain.model.auth.AuthState
import com.bestapp.zipbab.domain.model.user.SignUp
import com.bestapp.zipbab.domain.repository.AuthRepository
import com.bestapp.zipbab.domain.repository.FlashMeetingRepository
import com.bestapp.zipbab.domain.repository.ProfilePostRepository
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.bestapp.zipbab.domain.util.map
import com.bestapp.zipbab.domain.util.onError
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val flashMeetingRepository: FlashMeetingRepository,
    private val profilePostRepository: ProfilePostRepository,
    private val userPrivateLocalDataSource: UserPrivateLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userPrivateRemoteDataSource: UserPrivateRemoteDataSource,
) : AuthRepository {

    private fun hashPassword(pw: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(pw.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    override suspend fun login(id: String, pw: String): Result<Boolean, NetworkError> {
        return authRemoteDataSource.login(id, hashPassword(pw))
    }

    override suspend fun signUpUser(
        signUp: SignUp
    ): Result<AuthState, NetworkError> {
        val request = SignUpRequest(
            id = signUp.email,
            pw = hashPassword(signUp.pw),
            nickname = signUp.nickName,
        )
        return authRemoteDataSource.signUpUser(request).map {
            it.toDomain()
        }
    }

    override suspend fun signOutUser(userId: String): Result<Boolean, NetworkError> {
        // 회원탈퇴가 허용되지 않은 아이디인지 확인
        when (authRemoteDataSource.checkSignOutIsNotAllowed(userId)) {
            is Result.Error -> return Result.Error(NetworkError.NOT_AVAILABLE)
            is Result.Success -> Unit
        }
        val user = when (val response = userRemoteDataSource.getUser(userId)) {
            is Result.Error -> return response
            is Result.Success -> response.data
        }
        val userPrivate = when (val response = userPrivateRemoteDataSource.getUser(userId)) {
            is Result.Error -> return response
            is Result.Success -> response.data
        }

        // 인증 정보 삭제하기
        authRemoteDataSource.removeAuth(userPrivate.pw).onError {
            // TODO : Firebase Analytics
        }

        // 참여중인 모임 정리하기
        val meetings = when (val response = flashMeetingRepository.getMeetingAsHost(userId)) {
            is Result.Error -> return response
            is Result.Success -> response.data
        }

        for (meeting in meetings) {
            if (meeting.hostUser == userId) {
                // 모임에 참여 중인 멤버를 모임에서 우선 제거
                meeting.members.forEach { member ->
                    flashMeetingRepository.removeMember(meeting.uuid, member.id)
                }
                flashMeetingRepository.deleteMeeting(meeting.uuid)
            } else {
                flashMeetingRepository.removeMember(meeting.uuid, userId)
            }
        }
        // TODO : 모임 가입 신청 정보 삭제하기

        // 작성한 프로필 포스트 삭제하기
        for (postId in user.posts) {
            profilePostRepository.deletePost(userId, postId)
        }

        // 로컬에서 사용자 로그인 정보 삭제하기
        userPrivateLocalDataSource.removePrivateData()

        // 회원 탈퇴하기
        return authRemoteDataSource.signOutUser(userId)
    }

    override suspend fun sendCode(
        email: String,
        password: String
    ): Result<AuthState, NetworkError> {
        return authRemoteDataSource.sendCodeToEmail(email, password).map { it.toDomain() }
    }

    override fun getEmailAuthState(email: String): Flow<Boolean> {
        return authRemoteDataSource.getEmailAuthState(email)
    }

    override fun resetAuthState() {
        authRemoteDataSource.resetAuthState()
    }

    override suspend fun renewAuthState(email: String, password: String) {
        authRemoteDataSource.renewAuthState(email, password)
    }

    override suspend fun removeAuth(password: String): Result<Boolean, NetworkError> {
        return authRemoteDataSource.removeAuth(password)
    }
}
