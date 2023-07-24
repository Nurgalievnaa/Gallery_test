package com.example.gallery_test.view.profile

import androidx.lifecycle.ViewModel
import com.example.gallery_test.view.make.Room.ImageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {
    private lateinit var imageAdapter: ImageAdapter

    fun setImageAdapter(adapter: ImageAdapter) {
        imageAdapter = adapter
    }

    fun loadImagesFromDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val images: List<ImageEntity> = profileRepository.getAllImages()
            CoroutineScope(Dispatchers.Main).launch {
                imageAdapter.updateData(images)
            }
        }
    }

    fun updateUserData(username: String, email: String, birthday: String) {
        CoroutineScope(Dispatchers.IO).launch {
            profileRepository.updateUserData(username, email, birthday)
        }
    }
}
