package com.example.gallery_test.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.viewpager2.widget.ViewPager2
import com.example.gallery_test.BaseFragment
import com.example.gallery_test.adapter.HomePageAdapter
import com.example.gallery_test.databinding.FragmentHomePageBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomePageFragment : BaseFragment() {
    var binding: FragmentHomePageBinding? = null
    private var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager: ViewPager2? = binding?.viewpager2
        val tabLayout = binding?.tabs
        searchView = binding?.searchView

        val adapter = HomePageAdapter(this, lifecycle)
        viewPager?.adapter = adapter

        if (viewPager != null) {
            TabLayoutMediator(tabLayout!!, viewPager) { tab, position -> tab.text = pagesArray[position] }.attach()
        }

        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 1) {
                    searchView?.setQuery("", false)
                    searchView?.clearFocus()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        searchView = null
    }

    companion object {
        private val pagesArray = arrayOf(
            "New",
            "Popular"
        )
    }
}
