package com.example.gallery_test.view.home

import com.example.gallery_test.api.ApiService
import com.example.gallery_test.model.PhotoResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeNewRepository @Inject constructor(
    private val photoApiService: ApiService
) {
    fun getPhotos(isNew: Boolean?, isPopular: Boolean?): Single<PhotoResponse> {
        return photoApiService.getPhotos(isNew, isPopular)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}

