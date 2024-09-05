package com.bestapp.zipbab.ui.recruitment.viewpager.detailinfo

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bestapp.zipbab.R
import com.bestapp.zipbab.databinding.FragmentDetailInfoBinding
import com.bestapp.zipbab.ui.recruitment.StepSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailInfoFragment : Fragment() {

    private var _binding: FragmentDetailInfoBinding? = null
    private val binding: FragmentDetailInfoBinding
        get() = _binding!!

    private val sharedViewModel: StepSharedViewModel by viewModels({ requireParentFragment() })

    private val participantCountInputFilter =
        InputFilter { source, _, _, dest, _, _ ->
            val fullText = dest.toString() + source.toString()
            if (fullText == "") {
                return@InputFilter null
            }

            try {
                val input = Integer.parseInt(fullText)
                if (input in resources.getInteger(R.integer.min_participant_count)..resources.getInteger(
                        R.integer.max_participant_count
                    )
                ) {
                    return@InputFilter null
                }
            } catch (_: NumberFormatException) {
            }

            return@InputFilter ""
        }

    private val costInputFilter =
        InputFilter { source, start, end, dest, dstart, dend ->
            val fullText = dest.toString() + source.toString()
            if (fullText == "") {
                return@InputFilter null
            }

            try {
                val input = Integer.parseInt(fullText)
                if (input in resources.getInteger(R.integer.min_recruit_cost)..resources.getInteger(
                        R.integer.max_recruit_cost
                    )
                ) {
                    return@InputFilter null
                }
            } catch (_: NumberFormatException) {
            }

            return@InputFilter ""
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEditTextFilter()
        setListener()
    }

    private fun setEditTextFilter() {
        binding.tilParticipantCount.editText?.filters = arrayOf(participantCountInputFilter)
        binding.tilCost.editText?.filters = arrayOf(costInputFilter)
    }

    private fun setListener() = with(binding) {
        tilName.editText?.doOnTextChanged { text, _, _, _ ->
            sharedViewModel.updateName(text.toString())
        }
        tilParticipantCount.editText?.doOnTextChanged { text, _, _, _ ->
            val count = text.toString().toIntOrNull() ?: -1
            sharedViewModel.updateParticipantCount(count)
        }
        tilCost.editText?.doOnTextChanged { text, _, _, _ ->
            val cost = text.toString().toIntOrNull() ?: -1
            sharedViewModel.updateCost(cost)
        }
        tilDescription.editText?.doOnTextChanged { text, _, _, _ ->
            val description = text.toString()
            sharedViewModel.updateDescription(description)
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
        const val STEP = 1
    }
}