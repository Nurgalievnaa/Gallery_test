package com.example.gallery_test.api

import com.example.gallery_test.model.PhotoResponse
import com.example.gallery_test.model.TokenResponse
import com.example.gallery_test.model.UserData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("/oauth/v2/token")
    fun getToken(
        @Query("client_id") clientId: String = /*todo move to const*/"70_1r5x3tudicv4o4g84cs0sc8ocs0koso40w0o4g84k0s4cc844o",
        @Query("grant_type") grantType: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("client_secret") clientSecret: String = /*todo move to const*/"1kda6tulcbr480g4k4og88kggksko8occkkc444cs8cssgkcco"
    ): Single<TokenResponse>

    @GET("/oauth/v2/token")
    fun refreshToken(
        @Query("client_id") clientId: String,
        @Query("grant_type") grantType: String,
        @Query("refresh_token") refreshToken: String,
        @Query("client_secret") clientSecret: String
    ): Single<TokenResponse>

    @GET("/api/photos")
    fun getPhotos(
        @Query("new") isNew: Boolean?= null,
        @Query("popular") isPopular: Boolean? = null
    ): Single<PhotoResponse>


    @POST("/api/users")
    fun registerUser(@Body user: UserData): Single<TokenResponse>
}
