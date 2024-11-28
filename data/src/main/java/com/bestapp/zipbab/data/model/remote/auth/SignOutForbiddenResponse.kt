package com.bestapp.zipbab.data.model.remote.auth

data class SignOutForbiddenResponse(
    val notAllowedIds: List<String> = listOf(),
)
