package com.example.gallery_test.adapter

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gallery_test.view.home.HomeFragment

private const val NUM_TABS = 2

class HomePageAdapter(fragment: Fragment, private val lifecycle: Lifecycle) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment(isNew = true, isPopular = false)
            1 -> HomeFragment(isNew = false, isPopular = true)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}
