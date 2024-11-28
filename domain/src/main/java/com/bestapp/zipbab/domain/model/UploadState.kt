package com.bestapp.zipbab.domain.model

sealed interface UploadState {

    val tempPostDocumentId: String

    data class Pending(
        override val tempPostDocumentId: String
    ): UploadState

    data class ProcessImage(
        override val tempPostDocumentId: String,
        val currentProgressOrder: Int, // 현재 업로드 중인 이미지 순서, 1부터 시작
        val maxOrder: Int, // 전체 이미지 순서
    ): UploadState

    data class ProcessPost(
        override val tempPostDocumentId: String,
    ): UploadState

    data class Fail(
        override val tempPostDocumentId: String,
    ): UploadState

    data class SuccessPost(
        override val tempPostDocumentId: String,
        val postDocumentId: String,
    ): UploadState
}
