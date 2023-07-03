package com.example.gallery_test.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gallery_test.databinding.FragmentProfileSettingBinding


class ProfileSettingFragment : Fragment() {
    private var binding:FragmentProfileSettingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileSettingBinding.inflate(inflater, container, false)
        return binding?.root

    }

}