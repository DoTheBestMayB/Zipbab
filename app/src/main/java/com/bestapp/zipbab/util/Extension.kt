package com.bestapp.zipbab.util

 import android.widget.ImageView
 import androidx.annotation.DrawableRes
 import androidx.fragment.app.Fragment
 import androidx.navigation.NavDirections
 import androidx.navigation.fragment.findNavController
 import coil.load
 import com.bestapp.zipbab.R

fun ImageView.loadOrDefault(
    imageUrl: String?,
    @DrawableRes defaultRes: Int = R.drawable.sample_profile_image
) {
    if (imageUrl.isNullOrBlank()) {
        this.load(defaultRes)
        return
    }
    this.load(imageUrl) {
        placeholder(defaultRes)
    }
}

fun NavDirections.safeNavigate(origin: Fragment) {
    if (origin.parentFragmentManager.fragments.last() != origin) {
        return
    }
    // A15와 같은 저사양 기기에서는 위 if문 만으로는 부족함
    try {
        origin.findNavController().navigate(this)
    } catch (_: IllegalArgumentException) {
    }
}