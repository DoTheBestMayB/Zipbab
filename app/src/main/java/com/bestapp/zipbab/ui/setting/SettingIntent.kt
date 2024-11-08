package com.bestapp.zipbab.ui.setting

sealed interface SettingIntent {
    data object Default : SettingIntent
    data object SignOut : SettingIntent
    data object SignOutTry : SettingIntent
    data object Logout : SettingIntent
    data object Login : SettingIntent
    data object Profile : SettingIntent
    data object Meeting : SettingIntent
    data object SignUp : SettingIntent
    data object RequestDelete : SettingIntent
    data object NotYetImplemented : SettingIntent
    data object PrivacyPolicy: SettingIntent
    data object LocationPolicy: SettingIntent
}