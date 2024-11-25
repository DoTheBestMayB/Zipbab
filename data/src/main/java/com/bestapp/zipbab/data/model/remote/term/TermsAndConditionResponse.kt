package com.bestapp.zipbab.data.model.remote.term

import kotlinx.serialization.Serializable

@Serializable
data class TermsAndConditionResponse(
    val title: String = "",
    val content: String = "",
)
