package com.example.gallery_test.view

import android.content.Context
import com.example.gallery_test.modules.NetworkModule
import com.example.gallery_test.modules.ViewModelModule
import com.example.gallery_test.view.authorization.LoginFragment
import com.example.gallery_test.view.authorization.RegistrationFragment
import com.example.gallery_test.view.home.HomeFragment
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
    fun inject(homePopularFragment: HomeFragment)


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
