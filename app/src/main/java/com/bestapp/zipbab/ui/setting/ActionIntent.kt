package com.bestapp.zipbab.ui.setting

sealed interface ActionIntent {

    data object Default : ActionIntent
    data object NotYetImplemented : ActionIntent
    data object PrivacyPolicy : ActionIntent
    data object LocationPolicy : ActionIntent
    data object SignOutTry : ActionIntent

    data class DirectToRequestDelete(
        val url: String,
    ) : ActionIntent
}