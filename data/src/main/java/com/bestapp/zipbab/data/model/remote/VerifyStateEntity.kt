package com.bestapp.zipbab.data.model.remote

sealed interface VerifyStateEntity {

    data object Success : VerifyStateEntity
    data object AlreadyUsedEmail : VerifyStateEntity
    data object FailAtSendVerificationEmail : VerifyStateEntity
    data object Fail : VerifyStateEntity
    data object PasswordTooShort : VerifyStateEntity
}