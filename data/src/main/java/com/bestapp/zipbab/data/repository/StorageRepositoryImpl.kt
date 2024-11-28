package com.bestapp.zipbab.data.repository

import android.net.Uri
import com.bestapp.zipbab.data.remote.datasource.StorageRemoteDataSource
import com.bestapp.zipbab.domain.repository.StorageRepository
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import javax.inject.Inject


internal class StorageRepositoryImpl @Inject constructor(
    private val storageRemoteDataSource: StorageRemoteDataSource,
) : StorageRepository {
    override suspend fun uploadImage(pathPrefix: String, imageUri: String): Result<String, NetworkError> {
        return storageRemoteDataSource.uploadImage(pathPrefix, Uri.parse(imageUri))
    }
}
