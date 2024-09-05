package com.bestapp.zipbab.ui.recruitment.viewpager.categoryselect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bestapp.zipbab.R
import com.bestapp.zipbab.databinding.FragmentCategorySelectBinding
import com.bestapp.zipbab.model.FilterUiState
import com.bestapp.zipbab.ui.recruitment.StepSharedViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CategorySelectFragment : Fragment() {

    private var _binding: FragmentCategorySelectBinding? = null
    private val binding: FragmentCategorySelectBinding
        get() = _binding!!

    private val viewModel: CategorySelectViewModel by viewModels()
    private val sharedViewModel: StepSharedViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategorySelectBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        setObserve()
    }

    private fun setListener() {
        binding.cg.setOnCheckedStateChangeListener { chipGroup, checkIds ->
            val selectedChips = mutableListOf<String>()

            for (checkId in checkIds) {
                selectedChips.add(chipGroup.findViewById<Chip>(checkId).text.toString())
            }
            viewModel.onCheckedStateChange(selectedChips)
            sharedViewModel.updateCategory(selectedChips)
        }
    }

    private fun setObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        setCategory(state.categories)
                    }
                }
            }
        }
    }

    private fun setCategory(categories: List<FilterUiState.FoodUiState>) {
        for (category in categories) {
            // layout을 inflate 해서 Chip을 생성한 후 ChipGroup에 추가하면 ChipGroup의 SingleSelection이 각 Chip에 대해 개별적으로 적용됨
            // 참고자료 : https://github.com/material-components/material-components-android/issues/1178
//            val chip = layoutInflater.inflate(R.layout.single_chip, binding.cg, false) as Chip
//            chip.text = category.name

            val chip = Chip(requireContext()).apply {
                text = category.name
                isCheckable = true
                setTextColor(
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.selector_text_color
                    )
                )
                layoutDirection = View.LAYOUT_DIRECTION_LOCALE
                setChipBackgroundColorResource(R.color.selector_chip_background_color)
            }
            binding.cg.addView(chip)
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
        const val STEP = 0
    }
}