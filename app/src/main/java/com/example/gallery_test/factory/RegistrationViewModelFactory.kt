package com.example.gallery_test.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gallery_test.api.ApiService
import com.example.gallery_test.view.authorization.RegistrationViewModel
import javax.inject.Inject

class RegistrationViewModelFactory @Inject constructor(
    //private val sharedPreferences: SharedPreferences,
    private val apiService: ApiService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel( apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
