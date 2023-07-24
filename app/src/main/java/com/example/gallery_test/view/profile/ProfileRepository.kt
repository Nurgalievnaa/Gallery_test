package com.example.gallery_test.view.profile

import com.example.gallery_test.model.UserData
import com.example.gallery_test.view.make.Room.ImageDao
import com.example.gallery_test.view.make.Room.ImageEntity

class ProfileRepository(private val imageDao: ImageDao) {
    suspend fun getAllImages(): List<ImageEntity> = imageDao.getAllImages()

    suspend fun updateUserData(username: String, email: String, birthday: String) {
        val user = UserData(username = username, email = email, birthday = birthday, password = "")
    }
}
