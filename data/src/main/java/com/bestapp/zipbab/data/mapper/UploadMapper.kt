package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.remote.upload.UploadStateDto
import com.bestapp.zipbab.domain.model.UploadState

fun UploadStateDto.toDomain(): UploadState {
    return when(this) {
        is UploadStateDto.Fail -> UploadState.Fail(tempPostDocumentId)
        is UploadStateDto.Pending -> UploadState.Pending(tempPostDocumentId)
        is UploadStateDto.ProcessImage -> UploadState.ProcessImage(tempPostDocumentId, currentProgressOrder, maxOrder)
        is UploadStateDto.ProcessPost -> UploadState.ProcessPost(tempPostDocumentId)
        is UploadStateDto.SuccessPost -> UploadState.SuccessPost(tempPostDocumentId, postDocumentId)
    }
}
