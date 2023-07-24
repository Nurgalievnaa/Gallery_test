package com.example.gallery_test.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gallery_test.view.home.HomeRepository
import com.example.gallery_test.view.home.HomeViewModel
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(private val homeNewPopularRepository: HomeRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(homeNewPopularRepository) as T
    }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
}
}