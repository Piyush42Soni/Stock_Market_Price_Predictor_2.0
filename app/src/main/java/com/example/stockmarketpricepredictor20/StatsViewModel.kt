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
import com.example.stockmarketpricepredictor20.data.IndexTrendData
import com.example.stockmarketpricepredictor20.data.ShareHoldingPatternData
import com.example.stockmarketpricepredictor20.network.StockApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
sealed interface StockUiState1 {
    data class Success(val photos: List<HistoricalData>) : StockUiState1
    object Error : StockUiState1
    object Loading : StockUiState1
}
class StatsViewModel : ViewModel() {
    var currentData:String  by mutableStateOf("")
    var companyName:String  by mutableStateOf("ADANIENT.NS")
    var gworth:Float by mutableStateOf(0f)
    var shareHoldingPatternData:ShareHoldingPatternData by mutableStateOf(ShareHoldingPatternData())
    var stockUiState: StockUiState1 by mutableStateOf(StockUiState1.Loading)
    var indexTrendData:IndexTrendData by mutableStateOf(IndexTrendData())
    private var stockSymbol=MutableLiveData<String>()
//    var statement: HistoricalData by mutableStateOf(HistoricalData())

    init {
        if(companyName.isBlank()){
            getSymbol("ADANIENT.NS")
        }
        else {
            getSymbol(companyName)
            getStockShareHoldingPatternData()
            getStockIndexTrendData()
        }
    }
    fun getStockPhotos() {
        viewModelScope.launch {
            stockUiState = StockUiState1.Loading
            stockUiState = try {
                val listResult = companyName.let {
                    listOf(StockApi.retrofitService1.getHistoricalData(s = it)
                        ,StockApi.retrofitService1.getHistoricalDataWeekly(s = it)
                        ,StockApi.retrofitService1.getHistoricalDataMonthly(s = it)
                        ,StockApi.retrofitService1.getHistoricalDataYearly(s = it)
                    )
                }
                Log.d("YOHO", listResult[0].chart.result.size.toString())
                StockUiState1.Success(listResult)
            } catch (e: IOException) {
                StockUiState1.Error
            } catch (e: HttpException) {
                StockUiState1.Error
            }
        }
    }
    fun getStockShareHoldingPatternData() {
        viewModelScope.launch {
            shareHoldingPatternData = try {
                companyName.let {
                    StockApi.retrofitService.getShareHoldingPattern(it)
                }
            } catch (e: IOException) {
                ShareHoldingPatternData()
            } catch (e: HttpException) {
                ShareHoldingPatternData()
            }
        }
    }
    fun getStockIndexTrendData() {
        viewModelScope.launch {
            indexTrendData = try {
                companyName.let {
                    StockApi.retrofitService.getIndexTrend(it)
                }
            } catch (e: IOException) {
                IndexTrendData()
            } catch (e: HttpException) {
                IndexTrendData()
            }
        }
    }
    fun getSymbol(s:String){
        companyName = s
        getStockPhotos()
    }
}
