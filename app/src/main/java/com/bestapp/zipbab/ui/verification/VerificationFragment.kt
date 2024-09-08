package com.bestapp.zipbab.ui.verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bestapp.zipbab.R
import com.bestapp.zipbab.databinding.FragmentVerificationBinding
import com.bestapp.zipbab.util.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerificationFragment : Fragment() {

    private var _binding: FragmentVerificationBinding? = null
    val binding: FragmentVerificationBinding
        get() = _binding!!

    private val viewModel: VerificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerificationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        setObserve()
    }

    private fun setListener() {
        binding.tvEmailAction.setOnClickListener {
            val action = VerificationFragmentDirections.actionVerificationFragmentToVerifyEmailFragment()
            action.safeNavigate(this)
        }

        binding.tvPhoneAction.setOnClickListener {
            val action = VerificationFragmentDirections.actionVerificationFragmentToVerifyPhoneFragment()
            action.safeNavigate(this)
        }

        binding.mt.setNavigationOnClickListener {
            if (!findNavController().popBackStack()) {
                requireActivity().finish()
            }
        }
    }

    private fun setObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.userUiState.collect { state ->
                        setView(state.email, state.phone)
                    }
                }
            }
        }
    }

    private fun setView(email: String?, phone: String?) {
        binding.tvEmailBody.text =
            email?.ifBlank { getString(R.string.verification_not_verified_value) }
        binding.tvEmailAction.text = if (email.isNullOrBlank()) {
            getString(R.string.verification_register)
        } else {
            getString(R.string.verification_change)
        }

        binding.tvPhoneBody.text =
            phone?.ifBlank { getString(R.string.verification_not_verified_value) }
        binding.tvPhoneAction.text = if (phone.isNullOrBlank()) {
            getString(R.string.verification_register)
        } else {
            getString(R.string.verification_change)
        }
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }
}