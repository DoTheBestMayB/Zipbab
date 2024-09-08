package com.bestapp.zipbab.ui.verification.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bestapp.zipbab.databinding.FragmentVerifyPhoneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyPhoneFragment : Fragment() {

    private var _binding: FragmentVerifyPhoneBinding? = null
    private val binding: FragmentVerifyPhoneBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyPhoneBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

}