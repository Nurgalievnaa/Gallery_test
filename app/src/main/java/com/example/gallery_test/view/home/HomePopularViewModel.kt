package com.example.gallery_test.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery_test.model.PhotoResponse
import io.reactivex.rxjava3.disposables.CompositeDisposable

class HomePopularViewModel(private val homePopularRepository: HomePopularRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _photoList = MutableLiveData<PhotoResponse>()
    val photoList: LiveData<PhotoResponse> get() = _photoList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchPhotos(isNew: Boolean?, isPopular: Boolean?) {
        homePopularRepository.getPhotos(isNew, isPopular)
            .subscribe({ response ->
                _photoList.value = response
            }, { error ->
                handleError(error)
            })
            .let { compositeDisposable.add(it) }
    }
    fun handleError(error:Throwable){
        _error.value = error.message
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
