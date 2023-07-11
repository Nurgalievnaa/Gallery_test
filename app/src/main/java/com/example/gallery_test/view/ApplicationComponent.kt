package com.example.gallery_test.view

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.gallery_test.modules.NetworkModule
import com.example.gallery_test.modules.ViewModelModule
import com.example.gallery_test.view.authorization.LoginFragment
import com.example.gallery_test.view.authorization.RegistrationFragment
import com.example.gallery_test.view.home.HomeNewFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        NetworkModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun inject(fragment: LoginFragment)
    fun inject(registerFragment: RegistrationFragment)
    fun inject(homeNewFragment: HomeNewFragment)


    fun getMap(): Map<Class<*>, ViewModel>

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
