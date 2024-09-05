package com.bestapp.zipbab.ui.recruitment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bestapp.zipbab.ui.recruitment.viewpager.detailinfo.DetailInfoFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.JoinConditionFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.LocationAndDateFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.MeetupProfilePictureSelectFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.MemberVerificationConditionFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.categoryselect.CategorySelectFragment

class RecruitStateAdapter(fragment: Fragment, private val maxStep: Int) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = maxStep

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            CategorySelectFragment.STEP -> CategorySelectFragment()
            DetailInfoFragment.STEP -> DetailInfoFragment()
            LocationAndDateFragment.STEP -> LocationAndDateFragment()
            JoinConditionFragment.STEP -> JoinConditionFragment()
            MemberVerificationConditionFragment.STEP -> MemberVerificationConditionFragment()
            MeetupProfilePictureSelectFragment.STEP -> MeetupProfilePictureSelectFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

}