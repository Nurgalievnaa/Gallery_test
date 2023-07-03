package com.example.gallery_test.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gallery_test.R

class SplashFragment : Fragment() {
//TODO move to const
    private val SPLASH_DELAY: Long = 2000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//TODO refactoring delay
        Handler(Looper.getMainLooper()).postDelayed({
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WelcomePageFragment())
                .commit()
        }, SPLASH_DELAY)
    }
}
