package com.bestapp.zipbab.data.remote.datasource

import android.net.Uri
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface StorageRemoteDataSource {

    suspend fun uploadImage(pathPrefix: String, imageUri: Uri): Result<String, NetworkError>
}
