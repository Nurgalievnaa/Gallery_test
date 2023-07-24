package com.example.gallery_test.view.authorization

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationRepository: RegistrationRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _isBirthdayValid = MutableLiveData<Boolean>()
    val isBirthdayValid: LiveData<Boolean> get() = _isBirthdayValid

    private val compositeDisposable = CompositeDisposable()

    private val _registerLiveData = MutableLiveData<RegisterResponse>()
    val registerLiveData: LiveData<RegisterResponse> get() = _registerLiveData

    fun register(
        username: String,
        email: String,
        birthday: String,
        password: String,
        confirmPassword: String
    ) {
        registrationRepository.registerUser( username,
            email,
            birthday,
            password,
            confirmPassword)
            .subscribe({ response ->
                val accessToken = response.accessToken
                sharedPreferences.edit().putString("token", accessToken).apply()
                _registerLiveData.value = RegisterResponse.Success(response)
            }, { error ->
                _registerLiveData.value = RegisterResponse.Error(error)
                Log.d("check", error.toString())
            })
            .let { compositeDisposable.add(it) }
    }

    fun validateBirthday(date: String?) {
        if (date.isNullOrEmpty()) {
            _isBirthdayValid.value = false
        } else if (!date.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
            _isBirthdayValid.value = false
        } else {
            val parts = date.split("-")
            val year = parts[0].toInt()
            val month = parts[1].toInt()
            val day = parts[2].toInt()

            if (year < 1920 || year > 2006) {
                _isBirthdayValid.value = false
            } else if (month == 2 && day > 28) {
                _isBirthdayValid.value = false
            } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                _isBirthdayValid.value = false
            } else if (month < 1 || month > 12) {
                _isBirthdayValid.value = false
            } else _isBirthdayValid.value = !(day < 1 || day > 31)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
