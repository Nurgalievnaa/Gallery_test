package com.example.gallery_test.view.home

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery_test.model.PhotoItem

class PhotoDetailViewModel : ViewModel() {
    private val _photoItem = MutableLiveData<PhotoItem>()
    val photoItem: LiveData<PhotoItem> get() = _photoItem

    fun setPhotoItem(item: PhotoItem) {
        _photoItem.value = item
    }

}
