package com.bestapp.zipbab.data.remote.upload

import kotlinx.serialization.Serializable

sealed interface UploadStateEntity {

    val tempPostDocumentID: String

    @Serializable
    data class Pending(
        override val tempPostDocumentID: String
    ): UploadStateEntity

    @Serializable
    data class ProcessImage(
        override val tempPostDocumentID: String,
        val currentProgressOrder: Int, // 현재 업로드 중인 이미지 순서, 1부터 시작
        val maxOrder: Int, // 전체 이미지 순서
    ): UploadStateEntity

    @Serializable
    data class ProcessPost(
        override val tempPostDocumentID: String,
    ): UploadStateEntity

    @Serializable
    data class Fail(
        override val tempPostDocumentID: String,
    ): UploadStateEntity

    @Serializable
    data class SuccessPost(
        override val tempPostDocumentID: String,
        val postDocumentID: String,
    ): UploadStateEntity
}
