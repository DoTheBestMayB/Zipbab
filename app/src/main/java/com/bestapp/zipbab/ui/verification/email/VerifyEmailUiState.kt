package com.bestapp.zipbab.ui.verification.email

data class VerifyEmailUiState(
    val isAddressValid: Boolean = false,
    val email: String = "",
    val step: Step = Step.DEFAULT,
)

enum class Step {
    DEFAULT, EMAIL_CHECK, VERIFICATION_CODE_CHECK
}
