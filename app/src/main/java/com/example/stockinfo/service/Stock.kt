package com.example.stockinfo.service

import com.example.stockinfo.repository.Price
import com.example.stockinfo.repository.StockDataModel
import com.google.gson.annotations.SerializedName
import java.util.Currency
import java.util.Date

data class StockList(
    @SerializedName("stocks")
    val stocks: List<Stock>
)

data class Stock(
    @SerializedName("ticker")
    val ticker: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("current_price_cents")
    val cents: Long,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("current_price_timestamp")
    val priceTimeStamp: Long,
)
