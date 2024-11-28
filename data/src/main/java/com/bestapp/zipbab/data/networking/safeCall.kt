package com.bestapp.zipbab.data.networking

import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.firestore.FirebaseFirestoreException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse,
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> safeFirebaseCall(
    execute: () -> T?,
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: FirebaseFirestoreException) {
        return Result.Error(NetworkError.SERVER_ERROR)
    } catch (e: FirebaseTooManyRequestsException) {
        return Result.Error(NetworkError.TOO_MANY_REQUESTS)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }

    return response?.let { Result.Success(it) } ?: Result.Error(NetworkError.SERIALIZATION)
}
