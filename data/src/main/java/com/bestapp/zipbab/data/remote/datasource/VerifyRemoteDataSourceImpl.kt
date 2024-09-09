package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.BuildConfig
import com.bestapp.zipbab.data.remote.util.doneSuccessful
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.util.nextAlphanumericString
import javax.inject.Inject
import kotlin.random.Random

class VerifyRemoteDataSourceImpl @Inject constructor(): VerifyRemoteDataSource {

    private val random = Random(System.currentTimeMillis())

    private val actionCodeSettings: ActionCodeSettings = actionCodeSettings {
        url = BuildConfig.FIREBASE_DYNAMIC_LINK
        handleCodeInApp = true
        setAndroidPackageName(
            "com.bestapp.zipbab",
            true,
            "10"
        )
    }

    override suspend fun sendCode(email: String): Boolean {
        val user = Firebase.auth.currentUser

        if (user == null) {
            val randomPassword = random.nextAlphanumericString(RANDOM_PASSWORD_LENGTH)
            val isSuccess = Firebase.auth.createUserWithEmailAndPassword(email, randomPassword).doneSuccessful()
            if (isSuccess) {
                return Firebase.auth.currentUser?.sendEmailVerification(actionCodeSettings)?.doneSuccessful() ?: return false
            } else {
                return false
            }
        }
        return user.sendEmailVerification(actionCodeSettings).doneSuccessful()
    }

    companion object {
        private const val RANDOM_PASSWORD_LENGTH = 12
    }
}