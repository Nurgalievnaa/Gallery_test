package com.example.gallery_test.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.gallery_test.api.ApiService
import com.example.gallery_test.model.UserData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RegistrationViewModel(
    //todo make preferences repository
    private val sharedPreferences: SharedPreferences,
    private val apiService: ApiService
) : ViewModel() {

    fun register(
        username: String,
        email: String,
        birthday: String,
        password: String,
        confirmPassword: String
    ): Single<Boolean> {
        //todo read difference with isBlank & isEmpty
        if (username.isEmpty() || email.isEmpty() || birthday.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return Single.error(IllegalArgumentException("All fields must be filled"))
        }

        if (password != confirmPassword || password.length < 6) {
            return Single.error(IllegalArgumentException("Passwords must match and be at least 6 characters long"))
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || username.length < 6) {
            return Single.error(IllegalArgumentException("Invalid email address or username"))
        }

        //todo clean code
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("email", email)
        editor.putString("birthday", birthday)
        editor.putString("password", password)
        editor.putString("confirmPassword", confirmPassword)
        editor.apply()

        val user = UserData(username, email,birthday, password, confirmPassword)

        return apiService.registerUser(user)
            .subscribeOn(Schedulers.io())
            .map { response -> true }
            .onErrorReturnItem(false)
    }
}
