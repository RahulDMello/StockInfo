package com.example.stockinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stockinfo.repository.StockRepository
import com.example.stockinfo.ui.ListStocks

class MainActivity : ComponentActivity() {
    private val viewModel: StockViewModel by viewModels {
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // usually would let DI handle this
                return StockViewModel(StockRepository()) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showUI()
        viewModel.fetchStockInfo()
    }

    private fun showUI() {
        setContent {
            ListStocks(viewModel)
        }
    }
}
