package com.kursatmemis.retrofit.services

import com.kursatmemis.retrofit.models.CoinsModel
import retrofit2.Call
import retrofit2.http.GET

interface CoinsService {
    @GET("v2/coins")
    fun getCoins() : Call<CoinsModel>
}