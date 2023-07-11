package com.example.gallery_test.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.gallery_test.adapter.HomePageAdapter
import com.example.gallery_test.databinding.FragmentHomePageBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomePageFragment : Fragment() {
    private var binding: FragmentHomePageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager: ViewPager2? = binding?.viewpager2
        val tabLayout = binding?.tabs

        val adapter = HomePageAdapter(this, lifecycle)
        viewPager?.adapter = adapter

        if (viewPager != null) {
            TabLayoutMediator(tabLayout!!, viewPager) { tab, position ->
                tab.text = pagesArray[position]
            }.attach()
        }
    }

    companion object {
        private val pagesArray = arrayOf(
            "New",
            "Popular"
        )
    }
}
