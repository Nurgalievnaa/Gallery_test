package com.example.gallery_test.view.make

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MakeViewModel(private val makeRepository: MakeRepository) : ViewModel() {
    private val _capturedPhotoUri = MutableLiveData<Uri?>()
    val capturedPhotoUri: LiveData<Uri?> get() = _capturedPhotoUri

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> get() = _description

    fun setCapturedPhotoUri(uri: Uri?) {
        _capturedPhotoUri.value = uri
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setDescription(description: String){
        _description.value = description
    }

    fun saveImageToDatabase(name: String, description: String, uri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            makeRepository.saveImageToDatabase(name, description, uri.toString())
        }
    }
}
