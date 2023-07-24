package com.example.stockinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockinfo.repository.StockDataResponse
import com.example.stockinfo.repository.StockRepository
import kotlinx.coroutines.launch

class StockViewModel(private val repository: StockRepository) : ViewModel() {

    private val _stocksInfo = MutableLiveData<StockDataResponse>(StockDataResponse.LOADING)
    val stocksInfo: LiveData<StockDataResponse> = _stocksInfo

    fun fetchStockInfo() {
        viewModelScope.launch {
            try {
                val stocks = repository.getStocks()
                Log.d("Rahul",stocks.javaClass.canonicalName ?: "null")
                _stocksInfo.value = stocks
            } catch (e: Exception) {
                _stocksInfo.value = StockDataResponse.ERROR
            }
        }
    }
}