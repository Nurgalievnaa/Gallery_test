package com.example.gallery_test.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gallery_test.view.make.MakeRepository
import com.example.gallery_test.view.make.MakeViewModel
import com.example.gallery_test.view.make.Room.ImageDao

class MakeViewModelFactory(private val imageDao: ImageDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MakeViewModel::class.java)) {
            val makeRepository = MakeRepository(imageDao)
            return MakeViewModel(makeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

