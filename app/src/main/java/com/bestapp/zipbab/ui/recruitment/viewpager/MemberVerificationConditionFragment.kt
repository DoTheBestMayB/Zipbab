package com.bestapp.zipbab.ui.recruitment.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bestapp.zipbab.databinding.FragmentMemberVerificationConditionBinding

class MemberVerificationConditionFragment : Fragment() {

    private var _binding: FragmentMemberVerificationConditionBinding? = null
    private val binding: FragmentMemberVerificationConditionBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentMemberVerificationConditionBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    companion object {
        const val STEP = 4
    }
}