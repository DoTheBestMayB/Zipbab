package com.bestapp.zipbab.ui.recruitment.viewpager.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.bestapp.zipbab.databinding.FragmentMeetupProfilePictureSelectBinding
import com.bestapp.zipbab.ui.recruitment.StepSharedViewModel
import kotlinx.coroutines.launch

class MeetupProfilePictureSelectFragment : Fragment() {

    private var _binding: FragmentMeetupProfilePictureSelectBinding? = null
    private val binding: FragmentMeetupProfilePictureSelectBinding
        get() = _binding!!

    private val sharedViewModel: StepSharedViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeetupProfilePictureSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        setObserve()
    }

    private fun setListener() {
        binding.ivProfile.setOnClickListener {
            sharedViewModel.requestProfileImage()
        }
    }

    private fun setObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    sharedViewModel.stepState.collect { state ->
                        // 대표 이미지 설정
                        if (state.profileUri.isNotBlank()) {
                            binding.ivPreviewProfile.load(state.profileUri)
                        }
                        // 모임명 설정
                        binding.tvPreviewTitle.text = state.meetingName

                        // 모임 설명 설정
                        binding.tvPreviewDescription.text = state.description

                        // 장소 설정
                        binding.tvPreviewLocation.text = state.address

                        // 메인 메뉴 설정
                        binding.tvPreviewMenu.text = state.selectedCategories.first()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        sharedViewModel.updateStep(STEP)
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    companion object {
        const val STEP = 5
    }
}