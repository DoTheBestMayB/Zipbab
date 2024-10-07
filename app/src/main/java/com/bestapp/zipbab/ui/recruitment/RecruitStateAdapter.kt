package com.bestapp.zipbab.ui.recruitment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bestapp.zipbab.ui.recruitment.viewpager.profile.MeetupProfilePictureSelectFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.approvalcondition.ApprovalConditionFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.categoryselect.CategorySelectFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.detailinfo.DetailInfoFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.locationanddate.LocationAndDateFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.memberverificationcondition.MemberVerificationConditionFragment

class RecruitStateAdapter(fragment: Fragment, private val itemCount: Int) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = itemCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            CategorySelectFragment.STEP -> CategorySelectFragment()
            DetailInfoFragment.STEP -> DetailInfoFragment()
            LocationAndDateFragment.STEP -> LocationAndDateFragment()
            ApprovalConditionFragment.STEP -> ApprovalConditionFragment()
            MemberVerificationConditionFragment.STEP -> MemberVerificationConditionFragment()
            MeetupProfilePictureSelectFragment.STEP -> MeetupProfilePictureSelectFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

}