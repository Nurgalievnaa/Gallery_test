package com.example.gallery_test.view

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gallery_test.App
import com.example.gallery_test.R
import com.example.gallery_test.view.home.HomePageFragment
import com.example.gallery_test.view.splashscreen.SplashFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SplashFragment())
                .commit()
        }
        window.statusBarColor = Color.WHITE
    }
}
