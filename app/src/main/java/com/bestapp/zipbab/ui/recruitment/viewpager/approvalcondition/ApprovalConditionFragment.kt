package com.bestapp.zipbab.ui.recruitment.viewpager.approvalcondition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bestapp.zipbab.R
import com.bestapp.zipbab.databinding.FragmentApprovalConditionBinding
import com.bestapp.zipbab.ui.recruitment.StepSharedViewModel
import kotlinx.coroutines.launch

class ApprovalConditionFragment : Fragment() {

    private var _binding: FragmentApprovalConditionBinding? = null
    private val binding: FragmentApprovalConditionBinding
        get() = _binding!!

    private val sharedViewModel: StepSharedViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApprovalConditionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
        setListener()
        setObserve()
    }

    private fun setView() = with(binding) {
        joinImmediate.tvHeader.setText(R.string.recruit_condition_immediate_header)
        joinImmediate.tvBody.setText(R.string.recruit_condition_immediate_body)

        joinAfterApproval.tvHeader.setText(R.string.recruit_condition_approval_header)
        joinAfterApproval.tvBody.setText(R.string.recruit_condition_approval_body)
    }

    private fun setListener() = with(binding) {
        joinImmediate.root.setOnClickListener {
            sharedViewModel.updateApprovalCondition(false)
        }

        joinAfterApproval.root.setOnClickListener {
            sharedViewModel.updateApprovalCondition(true)
        }
    }

    private fun setObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.stepState.collect { state ->
                    val (checked, unchecked) = if (state.isApprovalRequired == null) {
                        binding.joinAfterApproval.rb.isChecked = false
                        binding.joinAfterApproval.root.setBackgroundResource(R.drawable.background_squre_content_view)

                        binding.joinImmediate.rb.isChecked = false
                        binding.joinImmediate.root.setBackgroundResource(R.drawable.background_squre_content_view)
                        return@collect
                    } else if (state.isApprovalRequired) {
                        binding.joinAfterApproval to binding.joinImmediate
                    } else {
                        binding.joinImmediate to binding.joinAfterApproval
                    }

                    checked.rb.isChecked = true
                    checked.root.setBackgroundResource(R.drawable.background_squre_content_view_selected)

                    unchecked.rb.isChecked = false
                    unchecked.root.setBackgroundResource(R.drawable.background_squre_content_view)
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
        const val STEP = 3
    }
}