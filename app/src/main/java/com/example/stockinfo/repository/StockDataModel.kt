package com.example.stockinfo.repository

import com.example.stockinfo.service.Stock
import java.util.Currency
import java.util.Date

sealed class StockDataResponse {
    data class SUCCESS(
        val list: List<StockDataModel>,
    ): StockDataResponse()

    object ERROR: StockDataResponse()

    object LOADING: StockDataResponse()

    companion object {
        fun of(list: List<StockDataModel>) = SUCCESS(list)
    }
}

data class StockDataModel(
    val ticker: String,
    val name: String,
    val quantity: Int?,
    val price: Price,
    val priceTimeStamp: Date,
)

data class Price(
    val currency: Currency,
    val cents: Long,
) {
    override fun toString(): String {
        return "${currency.symbol}${cents.div(100F)}"
    }
}

fun Stock.toStockDataModel(): StockDataModel {
    return StockDataModel(
        ticker = ticker,
        name = name,
        quantity = quantity,
        price = Price(
            currency = Currency.getInstance(currency),
            cents = cents
        ),
        priceTimeStamp = Date(priceTimeStamp)
    )
}