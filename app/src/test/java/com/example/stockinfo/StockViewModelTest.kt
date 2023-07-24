package com.example.stockinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.stockinfo.repository.Price
import com.example.stockinfo.repository.StockDataModel
import com.example.stockinfo.repository.StockDataResponse
import com.example.stockinfo.repository.StockRepository
import com.google.common.truth.Truth
import getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Currency
import java.util.Date

class StockViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: StockRepository = mock()
    private val stockViewModel = StockViewModel(repository)

    @Test
    fun testLoading() = runTest {
        whenever(repository.getStocks()).doReturn(StockDataResponse.LOADING)
        stockViewModel.fetchStockInfo()
        Truth.assertThat(stockViewModel.stocksInfo.getOrAwaitValue()).isEqualTo(StockDataResponse.LOADING)
    }

    @Test
    fun testError() = runTest {
        whenever(repository.getStocks()).doReturn(StockDataResponse.ERROR)
        stockViewModel.fetchStockInfo()
        Truth.assertThat(stockViewModel.stocksInfo.getOrAwaitValue()).isEqualTo(StockDataResponse.ERROR)
    }

    @Test
    fun testEmptyList() = runTest {
        whenever(repository.getStocks()).doReturn(StockDataResponse.SUCCESS(listOf()))
        stockViewModel.fetchStockInfo()
        val value = stockViewModel.stocksInfo.getOrAwaitValue()
        Truth.assertThat(value).isEqualTo(StockDataResponse.SUCCESS(listOf()))
    }

    @Test
    fun testResponse() = runTest {
        val dataModel = StockDataModel(
            ticker = "ticker",
            name = "name",
            quantity = 1,
            price = Price(Currency.getInstance("USD"), 1000),
            priceTimeStamp = Date()
        )
        whenever(repository.getStocks()).doReturn(StockDataResponse.SUCCESS(listOf(
            dataModel
        )))
        stockViewModel.fetchStockInfo()
        val value = stockViewModel.stocksInfo.getOrAwaitValue()
        Truth.assertThat(value).isEqualTo(StockDataResponse.SUCCESS(listOf(dataModel)))
    }
}