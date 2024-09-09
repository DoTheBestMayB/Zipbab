package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.remote.datasource.VerifyRemoteDataSource
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class VerifyRepositoryImpl @Inject constructor(
    private val verifyRemoteDataSource: VerifyRemoteDataSource,
): VerifyRepository {

    override suspend fun sendCode(email: String): Boolean {
        return verifyRemoteDataSource.sendCode(email)
    }

    override fun getEmailAuthState(): Flow<Boolean> {
        return callbackFlow {
            val authStateListener = AuthStateListener {
                trySend(Firebase.auth.currentUser?.isEmailVerified == true)
            }
            Firebase.auth.addAuthStateListener(authStateListener)
            awaitClose {
                Firebase.auth.removeAuthStateListener(authStateListener)
            }
        }
    }
}