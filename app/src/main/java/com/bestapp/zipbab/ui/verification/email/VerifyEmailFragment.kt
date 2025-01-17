package com.bestapp.zipbab.ui.verification.email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bestapp.zipbab.R
import com.bestapp.zipbab.databinding.FragmentVerifyEmailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerifyEmailFragment : Fragment() {

    private var _binding: FragmentVerifyEmailBinding? = null
    private val binding: FragmentVerifyEmailBinding
        get() = _binding!!

    private val viewModel: VerifyEmailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyEmailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        setObserve()
    }

    private fun setListener() {
        binding.mt.setNavigationOnClickListener {
            if (!findNavController().popBackStack()) {
                requireActivity().finish()
            }
        }
        binding.btnEmailVerificationCode.setOnClickListener {
            viewModel.sendCode()
        }
        binding.tilEmail.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChange(text.toString())
        }
        binding.btnCheckVerificationStatus.setOnClickListener {
            viewModel.checkVerificationStatus()
        }
    }

    private fun setObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        when (state.step) {
                            Step.DEFAULT -> {
                                binding.tilEmail.isEnabled = true
                                binding.btnCheckVerificationStatus.isVisible = false
                            }

                            Step.EMAIL_CHECK -> binding.btnEmailVerificationCode.isEnabled =
                                state.isAddressValid

                            Step.VERIFICATION_CODE_CHECK -> {
                                binding.tilEmail.isEnabled = false
                                binding.btnCheckVerificationStatus.isVisible = true
                            }
                        }
                    }
                }
                launch {
                    viewModel.message.collect { messageType ->
                        val message = when (messageType) {
                            VerifyEmailMessage.FAIL_SEND_CODE -> getString(R.string.send_email_verification_fail)
                            VerifyEmailMessage.SUCCESS_SEND_CODE -> getString(R.string.send_email_verification_success)
                            VerifyEmailMessage.FAIL_UPDATE_AUTH_STATE -> {
                                val msg = getString(R.string.update_email_auth_state_fail)
                                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                                findNavController().popBackStack()
                                return@collect
                            }

                            VerifyEmailMessage.ALREADY_USED_EMAIL -> getString(R.string.already_used_email)
                            VerifyEmailMessage.PASS_WORD_TOO_SHORT -> getString(R.string.password_too_short)
                        }
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
                launch {
                    viewModel.isVerified.collect { isVerified ->
                        if (isVerified) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.email_verification_done),
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }
}