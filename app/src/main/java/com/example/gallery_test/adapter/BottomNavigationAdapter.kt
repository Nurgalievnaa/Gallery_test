package com.example.gallery_test.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.gallery_test.view.HomePageFragment
import com.example.gallery_test.view.MakeFragment
import com.example.gallery_test.view.ProfileFragment

class BottomNavigationAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomePageFragment()
            1 -> MakeFragment()
            else -> ProfileFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Home"
            1 -> "Make"
            else -> "Profile"
        }
    }
}
