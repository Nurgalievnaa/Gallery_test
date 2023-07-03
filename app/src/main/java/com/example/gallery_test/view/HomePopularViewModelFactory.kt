package com.example.gallery_test.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gallery_test.repository.HomePopularRepository
import com.example.gallery_test.viewModel.HomePopularViewModel

class HomePopularViewModelFactory(private val homePopularRepository: HomePopularRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomePopularViewModel::class.java)){
            return HomePopularViewModel(homePopularRepository) as T
    }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
}
}