package com.bestapp.zipbab.ui.recruitment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bestapp.zipbab.ui.recruitment.viewpager.CategorySelectFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.DetailInfoFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.JoinConditionFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.LocationAndDateFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.MeetupProfilePictureSelectFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.MemberVerificationConditionFragment

class RecruitStateAdapter(fragment: Fragment, private val maxStep: Int): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = maxStep

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CategorySelectFragment()
            1 -> DetailInfoFragment()
            2 -> LocationAndDateFragment()
            3 -> JoinConditionFragment()
            4 -> MemberVerificationConditionFragment()
            5 -> MeetupProfilePictureSelectFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

}