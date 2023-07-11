package com.example.gallery_test.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gallery_test.view.home.HomeNewRepository
import com.example.gallery_test.view.home.HomeNewViewModel
import javax.inject.Inject

class HomeNewViewModelFactory @Inject constructor(
    private val homeNewRepository: HomeNewRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeNewViewModel::class.java)) {
            return HomeNewViewModel(homeNewRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
