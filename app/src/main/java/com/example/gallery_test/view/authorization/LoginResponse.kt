package com.example.gallery_test.view.authorization

import com.example.gallery_test.model.TokenResponse

sealed class LoginResponse {
    class Success(val tokenResponse: TokenResponse): LoginResponse()
    class Error(val error: Throwable): LoginResponse()
}