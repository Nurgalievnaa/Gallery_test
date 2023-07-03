package com.example.gallery_test.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

object ApiServiceBuilder {
    private const val BASE_URL = "https://gallery.prod1.webant.ru/"

    fun createApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
