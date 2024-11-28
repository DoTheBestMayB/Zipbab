package com.bestapp.zipbab.domain.model.auth

sealed interface AuthState {

    data object Success : AuthState
    data object AlreadyUsedEmail : AuthState
    data object FailAtSendVerificationEmail : AuthState
    data object Fail : AuthState
    data object PasswordTooShort : AuthState
}
