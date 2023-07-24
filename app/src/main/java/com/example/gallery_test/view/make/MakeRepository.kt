package com.example.gallery_test.view.make

import com.example.gallery_test.view.make.Room.ImageDao
import com.example.gallery_test.view.make.Room.ImageEntity

class MakeRepository(private val imageDao: ImageDao) {

    suspend fun saveImageToDatabase(name: String, description: String, uri: String) {
        val imageEntity = ImageEntity(name = name, description = description, imageUri = uri)
        imageDao.insertImage(imageEntity)
    }
}
