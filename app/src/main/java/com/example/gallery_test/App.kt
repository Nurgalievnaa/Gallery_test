package com.example.gallery_test

import android.app.Application
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.gallery_test.view.ApplicationComponent
import com.example.gallery_test.view.DaggerApplicationComponent


class App: Application() {

     val applicationComponent: ApplicationComponent by lazy {DaggerApplicationComponent.factory().create(this)}

}
