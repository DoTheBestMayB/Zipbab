package com.bestapp.zipbab.data.remote.upload

import kotlinx.serialization.Serializable

sealed interface UploadStateDto {

    val tempPostDocumentId: String

    @Serializable
    data class Pending(
        override val tempPostDocumentId: String
    ): UploadStateDto

    @Serializable
    data class ProcessImage(
        override val tempPostDocumentId: String,
        val currentProgressOrder: Int, // 현재 업로드 중인 이미지 순서, 1부터 시작
        val maxOrder: Int, // 전체 이미지 순서
    ): UploadStateDto

    @Serializable
    data class ProcessPost(
        override val tempPostDocumentId: String,
    ): UploadStateDto

    @Serializable
    data class Fail(
        override val tempPostDocumentId: String,
    ): UploadStateDto

    @Serializable
    data class SuccessPost(
        override val tempPostDocumentId: String,
        val postDocumentId: String,
    ): UploadStateDto
}
