package com.example.stockinfo.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FetchStockApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://storage.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val RestaurantService by lazy {
        retrofit.create(FetchStockService::class.java)
    }
}