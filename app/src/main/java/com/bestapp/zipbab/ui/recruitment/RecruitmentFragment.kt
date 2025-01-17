package com.bestapp.zipbab.ui.recruitment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.bestapp.zipbab.R
import com.bestapp.zipbab.args.ImageArgs
import com.bestapp.zipbab.databinding.FragmentRecruitmentBinding
import com.bestapp.zipbab.ui.addressfinder.AddressFinderFragment
import com.bestapp.zipbab.ui.addressfinder.AddressInfo
import com.bestapp.zipbab.ui.profileimageselect.ProfileImageSelectFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.approvalcondition.ApprovalConditionFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.categoryselect.CategorySelectFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.detailinfo.DetailInfoFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.locationanddate.LocationAndDateFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.memberverificationcondition.MemberVerificationConditionFragment
import com.bestapp.zipbab.ui.recruitment.viewpager.profile.MeetupProfilePictureSelectFragment
import com.bestapp.zipbab.util.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecruitmentFragment : Fragment() {
    private var _binding: FragmentRecruitmentBinding? = null
    private val binding: FragmentRecruitmentBinding
        get() = _binding!!

    private val recruitmentViewModel: RecruitmentViewModel by viewModels()
    private val stepSharedViewModel: StepSharedViewModel by viewModels()

    private val stepAdapter = StepAdapter()

    private val stepItemDecoration: ItemDecoration by lazy {
        object : ItemDecoration() {

            private val marginSize =
                binding.root.context.resources.getDimension(R.dimen.default_margin8).toInt()

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)

                outRect.set(marginSize, 0, marginSize, 0)
            }
        }
    }

    private lateinit var viewPagerAdapter: RecruitStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRecruitmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setListener()
        setObserve()
    }

    private fun setRecyclerView() {
        viewPagerAdapter = RecruitStateAdapter(this, RecruitmentState.MAX_STEP + 1)
        binding.pager.adapter = viewPagerAdapter
        binding.pager.isUserInputEnabled = false // 가로 swipe 방지

        binding.rvStep.adapter = stepAdapter
        binding.rvStep.addItemDecoration(stepItemDecoration)
    }

    private fun setListener() = with(binding) {
        btnNext.setOnClickListener {
            recruitmentViewModel.onNext()
        }
        btnBefore.setOnClickListener {
            recruitmentViewModel.onBefore()
        }
        mt.setNavigationOnClickListener {
            if (!findNavController().popBackStack()) {
                requireActivity().finish()
            }
        }
    }

    private fun setObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    recruitmentViewModel.uiState.collect { state ->
                        setVisibilityByStep(state.currentStep, state.steps)
                    }
                }

                launch {
                    recruitmentViewModel.createMeetingTrigger.collect {
                        stepSharedViewModel.createMeeting()
                    }
                }

                launch {
                    stepSharedViewModel.message.collect { message ->
                        val msg = when (message) {
                            Message.LOGIN_REQUIRED -> getString(R.string.recruitment_login_required)
                            Message.CREATION_COMPLETE -> {
                                stepSharedViewModel.onComplete()
                                getString(R.string.recruitment_create_complete)
                            }
                            Message.CREATION_FAIL -> getString(R.string.recruitment_create_fail)
                        }
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    }
                }

                launch {
                    stepSharedViewModel.stepState.collect { state ->
                        // 로딩 처리
                        setLoadingUi(state.isLoading)
                        // step에 따라 다음 버튼의 활성화 여부를 변경
                        when (state.lastModifiedStep) {
                            CategorySelectFragment.STEP -> {
                                binding.btnNext.isEnabled = state.isCategorySelectValid()
                            }

                            DetailInfoFragment.STEP -> {
                                binding.btnNext.isEnabled = state.isDetailInfoInputValid()
                            }

                            LocationAndDateFragment.STEP -> {
                                binding.btnNext.isEnabled = state.isLocationAndDateValid()
                            }

                            ApprovalConditionFragment.STEP -> {
                                binding.btnNext.isEnabled = state.isApprovalConditionValid()
                            }

                            MemberVerificationConditionFragment.STEP -> {
                                binding.btnNext.isEnabled =
                                    state.isMemberVerificationConditionValid()
                            }

                            MeetupProfilePictureSelectFragment.STEP -> {
                                binding.btnNext.isEnabled = state.isProfilePictureValid()
                            }
                        }
                    }
                }

                launch {
                    stepSharedViewModel.requestAction.collect { actionType ->
                        when (actionType) {
                            ActionType.ADDRESS -> {
                                val action =
                                    RecruitmentFragmentDirections.actionRecruitmentFragmentToAddressFinderFragment()
                                action.safeNavigate(this@RecruitmentFragment)
                            }

                            ActionType.VERIFICATION -> {
                                val action =
                                    RecruitmentFragmentDirections.actionRecruitmentFragmentToVerificationFragment()
                                action.safeNavigate(this@RecruitmentFragment)
                            }

                            ActionType.PROFILE_IMAGE -> {
                                val action =
                                    RecruitmentFragmentDirections.actionRecruitmentFragmentToProfileImageSelectFragment()
                                action.safeNavigate(this@RecruitmentFragment)
                            }

                            ActionType.DONE -> {
                                if (!findNavController().popBackStack()) {
                                    requireActivity().finish()
                                }
                            }
                        }
                    }
                }

                launch {
                    findNavController().currentBackStackEntry?.savedStateHandle?.getStateFlow(
                        AddressFinderFragment.ADDRESS_RESULT_KEY,
                        AddressInfo()
                    )?.collect { info ->
                        if (info.location.isNotBlank()) {
                            stepSharedViewModel.updateLocation(
                                info.location,
                                info.zipCode,
                            )
                        }
                        findNavController().currentBackStackEntry?.savedStateHandle?.remove<AddressInfo>(
                            AddressFinderFragment.ADDRESS_RESULT_KEY
                        )
                    }
                }

                launch {
                    findNavController().currentBackStackEntry?.savedStateHandle?.getStateFlow(
                        ProfileImageSelectFragment.PROFILE_IMAGE_SELECT_KEY,
                        ImageArgs(),
                    )?.collect { imageArgs ->
                        stepSharedViewModel.updateProfileImage(imageArgs.uri.toString())
                        findNavController().currentBackStackEntry?.savedStateHandle?.remove<ImageArgs>(
                            ProfileImageSelectFragment.PROFILE_IMAGE_SELECT_KEY
                        )
                    }
                }
            }
        }
    }

    private fun setLoadingUi(isLoading: Boolean) {
        binding.vModalBackground.isVisible = isLoading
        binding.cpiLoading.isVisible = isLoading
    }

    private fun setVisibilityByStep(currentStep: Int, steps: List<RecruitViewPagerStep>) {
        binding.pager.setCurrentItem(currentStep, false)

        when (currentStep) {
            RecruitmentState.MIN_STEP -> binding.btnBefore.isVisible = false
            RecruitmentState.MAX_STEP -> {
                // MIN_STEP에서 MAX_STEP으로 바로 이동하도록 STEP 갯수가 바뀌는 경우를 위한 코드
                binding.btnBefore.isVisible = true
                binding.btnNext.text = getString(R.string.create_meeting)
            }

            else -> {
                binding.btnBefore.isVisible = true
                binding.btnNext.text = getString(R.string.next)
            }
        }

        stepAdapter.submitList(steps)
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }
}
