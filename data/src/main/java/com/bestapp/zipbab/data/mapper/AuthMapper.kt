package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.model.remote.auth.AuthStateDto
import com.bestapp.zipbab.domain.model.auth.AuthState

fun AuthStateDto.toDomain(): AuthState {
    return when (this) {
        AuthStateDto.AlreadyUsedEmail -> AuthState.AlreadyUsedEmail
        AuthStateDto.Fail -> AuthState.Fail
        AuthStateDto.FailAtSendVerificationEmail -> AuthState.FailAtSendVerificationEmail
        AuthStateDto.PasswordTooShort -> AuthState.PasswordTooShort
        AuthStateDto.Success -> AuthState.Success
    }
}
