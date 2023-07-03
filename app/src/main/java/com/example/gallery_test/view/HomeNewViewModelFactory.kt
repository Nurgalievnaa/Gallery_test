package com.example.gallery_test.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gallery_test.repository.HomeNewRepository
import com.example.gallery_test.viewModel.HomeNewViewModel

class HomeNewViewModelFactory(private val homeNewRepository: HomeNewRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeNewViewModel::class.java)) {
            return HomeNewViewModel(homeNewRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
