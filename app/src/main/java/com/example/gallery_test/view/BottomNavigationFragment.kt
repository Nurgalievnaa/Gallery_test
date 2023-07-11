package com.example.gallery_test.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gallery_test.BaseFragment
import com.example.gallery_test.R
import com.example.gallery_test.databinding.FragmentBottomNavigationBinding
import com.example.gallery_test.view.home.HomePageFragment
import com.example.gallery_test.view.make.MakeFragment
import com.example.gallery_test.view.profile.ProfileFragment

class BottomNavigationFragment : BaseFragment() {
    private var binding: FragmentBottomNavigationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomNavigationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            bottomNavigationView.setOnItemSelectedListener  { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_home -> {
                        replaceFragment(R.id.fragmentContainer, HomePageFragment())
                        true
                    }
                    R.id.navigation_make -> {
                        replaceFragment(R.id.fragmentContainer, MakeFragment())
                        true
                    }
                    R.id.navigation_profile -> {
                        replaceFragment(R.id.fragmentContainer, ProfileFragment())
                        true
                    }
                    else -> false
                }
            }
        }
        replaceFragment(R.id.fragmentContainer,HomePageFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
