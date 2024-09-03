package com.bestapp.zipbab.data.remote.util

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.tasks.await

suspend fun <T> Task<T>.doneSuccessful(): Boolean {
    await()
    return isSuccessful
}

