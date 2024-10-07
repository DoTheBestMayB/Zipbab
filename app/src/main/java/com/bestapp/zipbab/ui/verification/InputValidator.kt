package com.bestapp.zipbab.ui.verification

import javax.inject.Inject

class InputValidator @Inject constructor() {

    private val emailRegex =
        """^[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{3,})$""".toRegex()

    fun isEmailValid(address: String): Boolean = emailRegex.matches(address)
}