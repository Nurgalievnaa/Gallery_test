package com.example.gallery_test.view

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gallery_test.api.ApiService
import com.example.gallery_test.viewModel.LoginViewModel

class LoginViewModelFactory(
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(apiService, sharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown")
    }
}


