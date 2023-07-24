package com.example.gallery_test.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoItem(
    val id: Int,
    val name: String,
    val dateCreate: String,
    val description: String,
    val new: Boolean,
    val popular: Boolean,
    val image: ImageItem,
    val user: String
) : Parcelable

@Parcelize
data class ImageItem(
    val id: Int, val name: String
) : Parcelable
