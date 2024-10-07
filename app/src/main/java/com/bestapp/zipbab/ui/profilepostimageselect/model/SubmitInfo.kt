package com.bestapp.zipbab.ui.profilepostimageselect.model

import com.bestapp.zipbab.args.ImagePostSubmitArgs

data class SubmitInfo(
    val userDocumentID: String,
    val images: List<String>,
) {
    fun toArgs() = ImagePostSubmitArgs(
        userDocumentID = userDocumentID,
        images = images,
    )
}