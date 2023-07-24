package com.example.stockinfo.repository

import androidx.annotation.OpenForTesting
import com.example.stockinfo.service.FetchStockApiClient
import retrofit2.await

@OpenForTesting
open class StockRepository {
    @OpenForTesting
    open suspend fun getStocks(): StockDataResponse {
        val stockList = FetchStockApiClient.RestaurantService.getStocks().await()
        return StockDataResponse.of(stockList
            .stocks
            .map {
                it.toStockDataModel()
            })
    }
}