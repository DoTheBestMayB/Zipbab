package com.bestapp.zipbab.ui.recruitment.viewpager.memberverificationcondition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bestapp.zipbab.R
import com.bestapp.zipbab.databinding.FragmentMemberVerificationConditionBinding
import com.bestapp.zipbab.databinding.SquareContentViewBinding
import com.bestapp.zipbab.ui.recruitment.StepSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MemberVerificationConditionFragment : Fragment() {

    private var _binding: FragmentMemberVerificationConditionBinding? = null
    private val binding: FragmentMemberVerificationConditionBinding
        get() = _binding!!

    private val viewModel: MemberVerificationConditionViewModel by viewModels()
    private val sharedViewModel: StepSharedViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentMemberVerificationConditionBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
        setListener()
        setObserve()
    }

    private fun setView() = with(binding) {
        layoutLeaderVerificationRequired.tvBody.isVisible = false
        layoutLeaderVerificationRequired.rb.isVisible = false
        layoutLeaderVerificationRequired.tvHeader.text =
            getString(R.string.recruit_leader_verification)
        layoutLeaderVerificationRequired.tvHeader.updateLayoutParams<ConstraintLayout.LayoutParams> {
            bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        }

        layoutVerificationNotRequired.tvHeader.text =
            getString(R.string.recruit_no_verification_required_header)
        layoutVerificationNotRequired.tvBody.text =
            getString(R.string.recruit_no_verification_required_body)

        layoutEmailVerificationRequired.tvHeader.text =
            getString(R.string.recruit_email_verification_required_header)
        layoutEmailVerificationRequired.tvBody.text =
            getString(R.string.recruit_email_verification_required_body)

        layoutPhoneVerificationRequired.tvHeader.text =
            getString(R.string.recruit_phone_verification_required_header)
        layoutPhoneVerificationRequired.tvBody.text =
            getString(R.string.recruit_phone_verification_required_body)
    }

    private fun setListener() {
        binding.layoutVerificationNotRequired.root.setOnClickListener {
            sharedViewModel.updateVerification(Verification.NONE)
        }
        binding.layoutEmailVerificationRequired.root.setOnClickListener {
            sharedViewModel.updateVerification(Verification.EMAIL)
        }
        binding.layoutPhoneVerificationRequired.root.setOnClickListener {
            sharedViewModel.updateVerification(Verification.PHONE)
        }
        binding.layoutLeaderVerificationRequired.root.setOnClickListener {
            sharedViewModel.requestVerification()
        }
    }

    private fun setObserve() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    sharedViewModel.stepState.collect { state ->
                        viewModel.checkLeaderValidation(state.verification)

                        val (checkVerifications, uncheckedVerifications) = when (state.verification) {
                            Verification.NONE -> listOf(layoutVerificationNotRequired) to listOf(
                                layoutEmailVerificationRequired,
                                layoutPhoneVerificationRequired
                            )

                            Verification.EMAIL -> listOf(layoutEmailVerificationRequired) to listOf(
                                layoutVerificationNotRequired,
                                layoutPhoneVerificationRequired
                            )

                            Verification.PHONE -> listOf(layoutPhoneVerificationRequired) to listOf(
                                layoutVerificationNotRequired,
                                layoutEmailVerificationRequired
                            )

                            null -> listOf<SquareContentViewBinding>() to listOf(
                                layoutVerificationNotRequired,
                                layoutEmailVerificationRequired,
                                layoutPhoneVerificationRequired
                            )
                        }

                        for (checkVerification in checkVerifications) {
                            checkVerification.rb.isChecked = true
                            checkVerification.root.setBackgroundResource(R.drawable.background_squre_content_view_selected)
                        }

                        for (uncheckedVerification in uncheckedVerifications) {
                            uncheckedVerification.rb.isChecked = false
                            uncheckedVerification.root.setBackgroundResource(R.drawable.background_squre_content_view)
                        }
                    }
                }

                launch {
                    viewModel.verificationRequireState.collect { state ->
                        when (state.requiredVerification) {
                            Verification.NONE -> {
                                layoutLeaderVerificationRequired.root.isVisible = false
                                tvLeaderVerificationHeader.isVisible = false
                                sharedViewModel.updateLeaderVerification(true)
                            }

                            Verification.EMAIL, Verification.PHONE -> {
                                layoutLeaderVerificationRequired.root.isVisible = true
                                tvLeaderVerificationHeader.isVisible = true
                                sharedViewModel.updateLeaderVerification(false)
                            }
                        }
                    }
                }

                launch {
                    viewModel.userUiState.collect {
                        // 이메일, 전화번호 인증을 완료한 후 이 화면으로 돌아왔을 때
                        // 인증 상태 갱신을 위해 데이터를 다시 불러오도록 하기 위한 목적의 collect 함수(WhileSubscribed 이용함)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        sharedViewModel.updateStep(STEP)
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    companion object {
        const val STEP = 4
    }
}