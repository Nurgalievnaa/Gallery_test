package com.example.gallery_test.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gallery_test.R
import com.example.gallery_test.databinding.FragmentBottomNavigationBinding

class BottomNavigationFragment : Fragment() {
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
            bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_home -> {
                        replaceFragment(HomePageFragment())
                        true
                    }
                    R.id.navigation_make -> {
                        replaceFragment(MakeFragment())
                        true
                    }
                    R.id.navigation_profile -> {
                        replaceFragment(ProfileFragment())
                        true
                    }
                    else -> false
                }
            }
        }
        replaceFragment(HomePageFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
