package com.example.gallery_test.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.gallery_test.api.ApiService
import io.reactivex.rxjava3.core.Single

class LoginViewModel(
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun login(email: String, password: String): Single<Boolean> {
        return apiService.getToken(
            grantType = "password",
            username = email,
            password = password
        ).map { tokenResponse ->
            val accessToken = tokenResponse.accessToken
            sharedPreferences.edit().putString("token", accessToken).apply()
            true
        }.onErrorReturnItem(false)
    }
}
