package com.bestapp.zipbab.ui.recruitment.viewpager.locationanddate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bestapp.zipbab.R
import com.bestapp.zipbab.databinding.FragmentLocationAndDateBinding
import com.bestapp.zipbab.ui.addressfinder.AddressFinderFragment
import com.bestapp.zipbab.ui.recruitment.StepSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LocationAndDateFragment : Fragment() {

    private var _binding: FragmentLocationAndDateBinding? = null
    private val binding: FragmentLocationAndDateBinding
        get() = _binding!!

    private val stepSharedViewModel: StepSharedViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationAndDateBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        setObserve()
    }


    private fun setListener() {
        binding.btnLocation.setOnClickListener {
            stepSharedViewModel.requestAddressFinder()
        }
    }

    private fun setObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                stepSharedViewModel.stepState.collect { state ->
                    binding.btnLocation.text = state.address.ifBlank {
                        getString(R.string.recruit_location_hint)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        stepSharedViewModel.updateStep(STEP)
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    override fun onDestroy() {
        clearFragmentResultListener(AddressFinderFragment.ADDRESS_RESULT_KEY)

        super.onDestroy()
    }

    companion object {
        const val STEP = 2
    }
}