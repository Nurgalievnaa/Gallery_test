package com.example.gallery_test.model

data class PhotoItem(
    val id: Int,
    val name: String,
    val dateCreate: String,
    val description: String,
    val new: Boolean,
    val popular: Boolean,
    val image: ImageItem,
    val user: String
)
data class ImageItem(
    val id: Int,
    val name: String
)
