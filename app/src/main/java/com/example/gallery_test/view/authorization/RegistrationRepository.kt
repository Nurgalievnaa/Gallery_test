package com.example.gallery_test.view.authorization

import android.content.SharedPreferences
import com.example.gallery_test.api.ApiService
import com.example.gallery_test.model.UserData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RegistrationRepository@Inject constructor(
    private val apiService: ApiService
) {

    fun registerUser(
        username: String,
        email: String,
        birthday: String,
        password: String,
        confirmPassword: String
    ): Single<Boolean> {
        if (username.isBlank() || email.isBlank() || birthday.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            return Single.error(IllegalArgumentException("All fields must be filled"))
        }

        if (password != confirmPassword || password.length < 6) {
            return Single.error(IllegalArgumentException("Passwords must match and be at least 6 characters long"))
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || username.length < 6) {
            return Single.error(IllegalArgumentException("Invalid email address or username"))
        }

        val user = UserData(username, email, birthday, password, confirmPassword)

        return apiService.registerUser(user)
            .subscribeOn(Schedulers.io())
            .map { response -> true }
            .onErrorReturnItem(false)
    }

}
