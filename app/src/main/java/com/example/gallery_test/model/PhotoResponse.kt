package com.example.gallery_test.model

data class PhotoResponse(
    val totalItems: Int,
    val itemsPerPage: Int,
    val countOfPages: Int,
    val data: List<PhotoItem>
)
