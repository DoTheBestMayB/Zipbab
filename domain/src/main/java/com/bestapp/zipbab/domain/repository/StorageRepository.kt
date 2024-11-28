package com.bestapp.zipbab.domain.repository

import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface StorageRepository {
    /**
     * @param pathPrefix 업로드 하는 이미지 폴더
     * @param imageUri 업로드 하려는 이미지 Uri
     * @return 업로드 된 이미지의 path
     */
    suspend fun uploadImage(pathPrefix: String, imageUri: String): Result<String, NetworkError>

    /**
     * @param pathPrefix 업로드 하는 이미지 폴더
     * @param imageUrl 이미지 path
     * @return 성공 여부
     */
    suspend fun deleteImage(pathPrefix: String, imageUrl: String): Result<Boolean, NetworkError>
}
