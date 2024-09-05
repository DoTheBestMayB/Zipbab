package com.bestapp.zipbab.ui.recruitment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.viewpager2.widget.ViewPager2
import com.bestapp.zipbab.R
import com.bestapp.zipbab.databinding.FragmentRecruitmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecruitmentFragment : Fragment() {
    private var _binding: FragmentRecruitmentBinding? = null
    private val binding: FragmentRecruitmentBinding
        get() = _binding!!

    private val recruitmentViewModel: RecruitmentViewModel by viewModels()

    private val stepAdapter = StepAdapter()

    private val stepItemDecoration: ItemDecoration by lazy {
        object: ItemDecoration() {

            private val marginSize = binding.root.context.resources.getDimension(R.dimen.default_margin8).toInt()

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
        viewPagerAdapter = RecruitStateAdapter(this, RecruitmentState.MAX_STEP)
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
    }

    private fun setObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    recruitmentViewModel.uiState.collect { state ->
                        handleCurrentStep(state)
                    }
                }
            }
        }
    }

    private fun handleCurrentStep(state: RecruitmentState) {
        binding.pager.setCurrentItem(state.currentStep, false)

        when (state.currentStep) {
            RecruitmentState.MIN_STEP -> binding.btnBefore.isVisible = false
            RecruitmentState.MAX_STEP -> {
                binding.btnBefore.isVisible = true // MIN_STEP에서 MAX_STEP으로 바로 이동하도록 STEP 갯수가 바뀌는 경우를 위한 코드
                binding.btnNext.text = getString(R.string.create_meeting)
            }
            else -> {
                binding.btnBefore.isVisible = true
                binding.btnNext.text = getString(R.string.next)
            }
        }
        stepAdapter.submitList(state.getStepInfo())
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }
}