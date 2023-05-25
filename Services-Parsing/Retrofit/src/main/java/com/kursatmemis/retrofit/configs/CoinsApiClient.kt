package com.kursatmemis.retrofit.configs

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoinsApiClient {
    private val BASE_URL = "https://api.coinranking.com/"
    private var retrofit: Retrofit? = null

    fun getClient() : Retrofit{
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit as Retrofit
    }

}