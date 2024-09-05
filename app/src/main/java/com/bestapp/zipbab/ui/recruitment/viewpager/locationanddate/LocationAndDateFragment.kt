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
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


@AndroidEntryPoint
class LocationAndDateFragment : Fragment() {

    private var _binding: FragmentLocationAndDateBinding? = null
    private val binding: FragmentLocationAndDateBinding
        get() = _binding!!

    private val stepSharedViewModel: StepSharedViewModel by viewModels({ requireParentFragment() })

    private val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())

    private val calendarConstraints = CalendarConstraints.Builder()
        .setValidator(DateValidatorPointForward.now())

    private val datePicker: MaterialDatePicker<Long> by lazy {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.recruit_date_picker_title))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(calendarConstraints.build())
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .setTheme(R.style.ThemeOverlay_App_DatePicker)
            .build()
    }

    private val timePicker: MaterialTimePicker by lazy {
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(10)
            .setMinute(10)
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
            .setTitleText(R.string.recruit_time_picker_title)
            .setTheme(R.style.ThemeOverlay_App_TimePicker)
            .build()
    }

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
        binding.btnDate.setOnClickListener {
            if (datePicker.isAdded.not()){
                datePicker.show(parentFragmentManager, null)
            }
        }
        datePicker.addOnPositiveButtonClickListener {
            stepSharedViewModel.updateDate(it)
        }
        binding.btnTime.setOnClickListener {
            if (timePicker.isAdded.not()) {
                timePicker.show(parentFragmentManager, null)
            }
        }
        timePicker.addOnPositiveButtonClickListener {
            stepSharedViewModel.updateTime(timePicker.hour, timePicker.minute)
        }
    }

    private fun setObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                stepSharedViewModel.stepState.collect { state ->
                    binding.btnLocation.text = state.address.ifBlank {
                        getString(R.string.recruit_location_hint)
                    }

                    binding.btnDate.text = if (state.date != 0L) {
                        simpleDateFormat.format(state.date)
                    } else {
                        getString(R.string.recruit_date_hint)
                    }

                    binding.btnTime.text = if (state.hour != -1 && state.minute != -1) {
                        "${state.hour}:${state.minute}"
                    } else {
                        getString(R.string.recruit_time_hint)
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