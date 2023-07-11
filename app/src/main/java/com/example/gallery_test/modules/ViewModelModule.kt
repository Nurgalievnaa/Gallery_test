package com.example.gallery_test.modules

import androidx.lifecycle.ViewModel
import com.example.gallery_test.view.authorization.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(LoginViewModel::class)
    @IntoMap
    abstract fun loginViewModel(loginViewModel: LoginViewModel): ViewModel

}