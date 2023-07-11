package com.example.gallery_test

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    fun replaceFragment(containerId: Int, fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }
}
