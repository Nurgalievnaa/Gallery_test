package com.example.gallery_test.view.authorization

import com.example.gallery_test.api.ApiService
import com.example.gallery_test.model.TokenResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class LoginRepository@Inject constructor(
    private val apiService: ApiService
) {
    fun login(email: String, password: String): Single<TokenResponse> {
        return apiService.getToken(
            grantType = "password",
            username = email,
            password = password
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}