package com.example.stockinfo.service

import retrofit2.Call
import retrofit2.http.GET

interface FetchStockService {

    companion object {
        private const val PORTFOLIO_PATH = "cash-homework/cash-stocks-api/portfolio.json"
        private const val PORTFOLIO_ERROR_PATH = "cash-homework/cash-stocks-api/portfolio_malformed.json"
        private const val PORTFOLIO_EMPTY_PATH = "cash-homework/cash-stocks-api/portfolio_empty.json"
    }

    @GET(PORTFOLIO_PATH)
    fun getStocks(): Call<StockList>
}