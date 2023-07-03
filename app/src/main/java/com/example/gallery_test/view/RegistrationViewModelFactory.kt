package com.example.gallery_test.view

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gallery_test.api.ApiService
import com.example.gallery_test.viewModel.RegistrationViewModel

class RegistrationViewModelFactory(
    private val sharedPreferences: SharedPreferences,
    private val apiService: ApiService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(sharedPreferences, apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
