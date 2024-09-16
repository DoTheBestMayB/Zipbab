package com.bestapp.zipbab.ui.verification.phone

data class VerifyPhoneUiState(
    val step: Step = Step.DEFAULT,
    val remainVerifyTime: Int = 0,
    val phone: String = "",
)

enum class Step {
    DEFAULT, VERIFICATION_CODE_CHECK, VERIFICATION_CODE_EXPIRED, VERIFICATION_CONFIRMED
}
