package com.example.gallery_test.view.authorization

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _loginLiveData = MutableLiveData<LoginResponse>()
    val loginLiveData: LiveData<LoginResponse> get() = _loginLiveData

    fun login(email: String, password: String) {
        loginRepository.login(email, password)
            .subscribe({ tokenResponse ->
                val accessToken = tokenResponse.accessToken
                sharedPreferences.edit().putString("token", accessToken).apply()
                _loginLiveData.value = LoginResponse.Success(tokenResponse)
            }, { error ->
                _loginLiveData.value = LoginResponse.Error(error)
            })
            .let { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
