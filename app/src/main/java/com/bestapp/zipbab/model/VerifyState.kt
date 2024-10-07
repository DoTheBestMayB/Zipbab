package com.bestapp.zipbab.model

sealed interface VerifyState {

    data object Success : VerifyState
    data object AlreadyUsedEmail : VerifyState
    data object FailAtSendVerificationEmail : VerifyState
    data object Fail : VerifyState
    data object PasswordTooShort : VerifyState
}