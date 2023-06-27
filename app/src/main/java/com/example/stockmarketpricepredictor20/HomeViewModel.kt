package com.example.stockmarketpricepredictor20


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketpricepredictor20.data.CurrentData
import com.example.stockmarketpricepredictor20.network.StockApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface StockUiState {
    data class Success(val photos: String) : StockUiState
    object Error : StockUiState
    object Loading : StockUiState
}

class HomeViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var stockUiState: StockUiState by mutableStateOf(StockUiState.Loading)
        private set
    private var stockSymbol=MutableLiveData<String>()
    var statement: MutableList<CurrentData> by mutableStateOf(mutableListOf())

    /**
     * Call getStockPhotos() on init so we can display status immediately.
     */
//    init {
//        getStockPhotos()
//    }

    /**
     * Gets Stock photos information from the Stock API Retrofit service and updates the
     * [StockPhoto] [List] [MutableList].
     */
    fun getStockPhotos() {
        viewModelScope.launch {
            stockUiState = StockUiState.Loading
            stockUiState = try {
                val listResult = stockSymbol.value.let {
                    if (it != null) {
                        StockApi.retrofitService.getData(it)
                    } else {
                        StockApi.retrofitService.getData("ADANIENT.NS")
                    }
                }
                statement.add(listResult)
                //Log.d("YOHO",statement.last().quoteSummary.result[0].financialData.financialCurrency.toString())
                StockUiState.Success(
                    //statement.value.toString()
                ""
                )
            } catch (e: IOException) {
                StockUiState.Error
            } catch (e: HttpException) {
                StockUiState.Error
            }
        }
    }
    fun getSymbol(s:String){
        stockSymbol.value = s
        getStockPhotos()
    }
}
