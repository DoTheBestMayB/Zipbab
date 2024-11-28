package com.bestapp.zipbab.data.remote.datasource

import android.net.Uri
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface StorageRemoteDataSource {

    /**
     * @param pathPrefix 업로드 하는 이미지 폴더
     * @param imageUri 업로드 하려는 이미지 Uri
     * @return 업로드 된 이미지의 path
     */
    suspend fun uploadImage(pathPrefix: String, imageUri: Uri): Result<String, NetworkError>

    /**
     * @param imageUrl 이미지 path
     * @return 성공 여부
     */
    suspend fun deleteImage(imageUrl: String): Result<Boolean, NetworkError>
}
