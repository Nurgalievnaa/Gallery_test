package com.example.gallery_test.viewModel

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery_test.repository.HomeNewRepository
import com.example.gallery_test.model.PhotoResponse
import io.reactivex.rxjava3.disposables.CompositeDisposable

class HomeNewViewModel(private val homeNewRepository: HomeNewRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _photoList = MutableLiveData<PhotoResponse>()
    val photoList: LiveData<PhotoResponse> get() = _photoList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    //private lateinit var searchView: SearchView

    fun fetchPhotos(isNew: Boolean?, isPopular: Boolean?) {
        homeNewRepository.getPhotos(isNew, isPopular)
            .subscribe({ response ->
                _photoList.value = response
            }, { error ->
                showError(error)
            })
            .let { compositeDisposable.add(it) }
    }
    fun showError(error:Throwable){
        _error.value = error.message
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
