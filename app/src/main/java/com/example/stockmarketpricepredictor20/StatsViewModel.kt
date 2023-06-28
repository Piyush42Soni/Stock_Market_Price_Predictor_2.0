package com.example.stockmarketpricepredictor20


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketpricepredictor20.data.CurrentData
import com.example.stockmarketpricepredictor20.data.HistoricalData
import com.example.stockmarketpricepredictor20.network.StockApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
sealed interface StockUiState1 {
    data class Success(val photos: HistoricalData) : StockUiState1
    object Error : StockUiState1
    object Loading : StockUiState1
}
class StatsViewModel : ViewModel() {
    var currentData:String  by mutableStateOf("")
    var companyName:String  by mutableStateOf("ADANIENT.NS")
    var gworth:Float by mutableStateOf(0f)
    var stockUiState: StockUiState1 by mutableStateOf(StockUiState1.Loading)
        private set
    private var stockSymbol=MutableLiveData<String>()
//    var statement: HistoricalData by mutableStateOf(HistoricalData())

    /**
     * Call getStockPhotos() on init so we can display status immediately.
     */
    init {
        if(companyName.isBlank()){
            getSymbol("ADANIENT.NS")
        }
        else {
            getSymbol(companyName)
        }
    }

    /**
     * Gets Stock photos information from the Stock API Retrofit service and updates the
     * [StockPhoto] [List] [MutableList].
     */
    fun getStockPhotos() {
        viewModelScope.launch {
            stockUiState = StockUiState1.Loading
            stockUiState = try {
                val listResult = companyName.let {
                    StockApi.retrofitService1.getHistoricalData(s=it)
                }
                Log.d("YOHO", listResult.chart.result.size.toString())
                StockUiState1.Success(listResult)
            } catch (e: IOException) {
                StockUiState1.Error
            } catch (e: HttpException) {
                StockUiState1.Error
            }
        }
    }
    fun getSymbol(s:String){
        companyName = s
        getStockPhotos()
    }
}
