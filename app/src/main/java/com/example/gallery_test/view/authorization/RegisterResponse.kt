package com.example.gallery_test.view.authorization

import com.example.gallery_test.model.TokenResponse
import com.example.gallery_test.model.UserData

sealed  class RegisterResponse {
    class Success(val tokenResponse: TokenResponse): RegisterResponse()
    class Error(val error: Throwable): RegisterResponse()
}