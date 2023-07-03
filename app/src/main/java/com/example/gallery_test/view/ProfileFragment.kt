package com.example.gallery_test.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gallery_test.R
import com.example.gallery_test.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val setting = binding.settingTextView
        setting.setOnClickListener{
            (activity as MainActivity).supportFragmentManager.beginTransaction().
            replace(R.id.fragment_container, ProfileSettingFragment())
                .commit()
        }
    }
}