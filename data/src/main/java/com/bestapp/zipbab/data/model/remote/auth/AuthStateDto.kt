package com.bestapp.zipbab.data.model.remote.auth

sealed interface AuthStateDto {

    data object Success : AuthStateDto
    data object AlreadyUsedEmail : AuthStateDto
    data object FailAtSendVerificationEmail : AuthStateDto
    data object Fail : AuthStateDto
    data object PasswordTooShort : AuthStateDto
}
