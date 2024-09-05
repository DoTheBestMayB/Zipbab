package com.bestapp.zipbab.ui.recruitment.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bestapp.zipbab.databinding.FragmentLocationAndDateBinding


class LocationAndDateFragment : Fragment() {

    private var _binding: FragmentLocationAndDateBinding? = null
    private val binding: FragmentLocationAndDateBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationAndDateBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }
}