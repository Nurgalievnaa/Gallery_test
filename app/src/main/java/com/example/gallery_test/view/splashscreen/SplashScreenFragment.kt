package com.example.gallery_test.view.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.gallery_test.BaseFragment
import com.example.gallery_test.R
import com.example.gallery_test.view.welcomepage.WelcomePageFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_splash_screen, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delaySplashScreen()
    }

    private fun delaySplashScreen() {
        lifecycleScope.launch {
            delay(SPLASH_DELAY)
            replaceFragment(R.id.fragment_container, WelcomePageFragment())        }
    }

    companion object {
        private const val SPLASH_DELAY: Long = 2000
    }
}
