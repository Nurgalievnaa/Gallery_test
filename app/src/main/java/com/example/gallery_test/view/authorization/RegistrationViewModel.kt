package com.example.gallery_test.view.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallery_test.api.ApiService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _registerLiveData = MutableLiveData<LoginResponse>()
    val registerLiveData: LiveData<LoginResponse> get() = _registerLiveData

    private val registrationRepository = RegistrationRepository(apiService)

    fun register(
        username: String,
        email: String,
        birthday: String,
        password: String,
        confirmPassword: String
    ): Single<Boolean> {
        return registrationRepository.registerUser(username, email, birthday, password, confirmPassword)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
