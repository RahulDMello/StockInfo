package com.example.stockinfo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.stockinfo.R
import com.example.stockinfo.StockViewModel
import com.example.stockinfo.repository.StockDataModel
import com.example.stockinfo.repository.StockDataResponse
import com.example.stockinfo.ui.theme.StockInfoTheme
import java.text.DateFormat

@Composable
fun ListStocks(viewModel: StockViewModel) {
    val dateFormat = DateFormat.getDateInstance()
    val stocksInfo by viewModel.stocksInfo.observeAsState(StockDataResponse.LOADING)

    StockInfoTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp), color = MaterialTheme.colorScheme.background) {
            with(stocksInfo) {
                when (this) {
                    StockDataResponse.ERROR -> PresentError()
                    StockDataResponse.LOADING -> PresentLoading()
                    is StockDataResponse.SUCCESS -> PresentStockList(list, dateFormat)
                }
            }
        }
    }
}

@Composable
private fun PresentStockList(stocksInfo: List<StockDataModel>, dateFormat: DateFormat) {
    LazyColumn {
        stocksInfo
            .forEach {
                item { StockDetails(it, dateFormat) }
            }
    }
}

@Composable
private fun PresentError() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(R.string.error_state_message), modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun PresentLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun StockDetails(stock: StockDataModel, simpleDate: DateFormat) {
    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
        Text(text = stringResource(id = R.string.stock_title, stock.name, stock.ticker))
        Divider(thickness = 1.dp)
        Text(text = stringResource(id = R.string.price, stock.price))
        Text(text = stringResource(id = R.string.quantity, stock.quantity?.toString() ?: "-"))
        Text(text = stringResource(id = R.string.time, simpleDate.format(stock.priceTimeStamp)))
    }
}
