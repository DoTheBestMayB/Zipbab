package com.bestapp.zipbab.ui.addressfinder

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressInfo(
    val location: String = "",
    val zipCode: String = "",
): Parcelable