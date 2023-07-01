package com.example.stockmarketpricepredictor20


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarketpricepredictor20.data.CurrentData
import com.example.stockmarketpricepredictor20.network.StockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
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
    private var SymbolList= listOf<String>("ADANIENT.NS","ADANIPORTS.NS","APOLLOHOSP.NS","ASIANPAINT.NS","AXISBANK.NS","BAJAJ-AUTO.NS","BAJAJFINSV.NS","BAJFINANCE.NS","BHARTIARTL.NS","BPCL.NS","BRITANNIA.NS","CIPLA.NS","COALINDIA.NS","DIVISLAB.NS","DRREDDY.NS","EICHERMOT.NS","GAIL.NS","GRASIM.NS","HCLTECH.NS","HDFC.NS","HDFCBANK.NS","HDFCLIFE.NS","HEROMOTOCO.NS","HINDALCO.NS","HINDUNILVR.NS","ICICIBANK.NS","INDUSINDBK.NS","INFY.NS","ITC.NS","JSWSTEEL.NS","KOTAKBANK.NS","LT.NS","M&M.NS","MARUTI.NS","NESTLEIND.NS","NTPC.NS","ONGC.NS","POWERGRID.NS","RELIANCE.NS","SBILIFE.NS","SBIN.NS","SUNPHARMA.NS","TATACONSUM.NS","TATASTEEL.NS","TCS.NS","TECHM.NS","TITN.NS","ULTRACEMCO.NS","UPL.NS","WIPRO.NS")
    /**
     * Call getStockPhotos() on init so we can display status immediately.
     */
    init {
        SymbolList= listOf<String>("ADANIENT.NS","ADANIPORTS.NS","APOLLOHOSP.NS","ASIANPAINT.NS","AXISBANK.NS","BAJAJ-AUTO.NS","BAJAJFINSV.NS","BAJFINANCE.NS","BHARTIARTL.NS","BPCL.NS","BRITANNIA.NS","CIPLA.NS","COALINDIA.NS","DIVISLAB.NS","DRREDDY.NS","EICHERMOT.NS","GAIL.NS","GRASIM.NS","HCLTECH.NS","HDFC.NS","HDFCBANK.NS","HDFCLIFE.NS","HEROMOTOCO.NS","HINDALCO.NS","HINDUNILVR.NS","ICICIBANK.NS","INDUSINDBK.NS","INFY.NS","ITC.NS","JSWSTEEL.NS","KOTAKBANK.NS","LT.NS","M&M.NS","MARUTI.NS","NESTLEIND.NS","NTPC.NS","ONGC.NS","POWERGRID.NS","RELIANCE.NS","SBILIFE.NS","SBIN.NS","SUNPHARMA.NS","TATACONSUM.NS","TATASTEEL.NS","TCS.NS","TECHM.NS","TITN.NS","ULTRACEMCO.NS","UPL.NS","WIPRO.NS")
        getStockPhotos()
    }

    /**
     * Gets Stock photos information from the Stock API Retrofit service and updates the
     * [StockPhoto] [List] [MutableList].
     */
    fun getStockPhotos() {
        viewModelScope.launch {
            SymbolList= listOf<String>("ADANIENT.NS","ADANIPORTS.NS","APOLLOHOSP.NS","ASIANPAINT.NS","AXISBANK.NS","BAJAJ-AUTO.NS","BAJAJFINSV.NS","BAJFINANCE.NS","BHARTIARTL.NS","BPCL.NS","BRITANNIA.NS","CIPLA.NS","COALINDIA.NS","DIVISLAB.NS","DRREDDY.NS","EICHERMOT.NS","GAIL.NS","GRASIM.NS","HCLTECH.NS","HDFC.NS","HDFCBANK.NS","HDFCLIFE.NS","HEROMOTOCO.NS","HINDALCO.NS","HINDUNILVR.NS","ICICIBANK.NS","INDUSINDBK.NS","INFY.NS","ITC.NS","JSWSTEEL.NS","KOTAKBANK.NS","LT.NS","M&M.NS","MARUTI.NS","NESTLEIND.NS","NTPC.NS","ONGC.NS","POWERGRID.NS","RELIANCE.NS","SBILIFE.NS","SBIN.NS","SUNPHARMA.NS","TATACONSUM.NS","TATASTEEL.NS","TCS.NS","TECHM.NS","TITN.NS","ULTRACEMCO.NS","UPL.NS","WIPRO.NS")
            stockUiState = StockUiState.Loading
            StockUiState.Success(
                //statement.value.toString()
                ""
            )
            for(it in SymbolList) {
                stockUiState = try {
                    val listResult = it.let {
                        StockApi.retrofitService.getCurrentData(it)
                    }
                    statement.add(listResult)
                    if(it!="WIPRO.NS") {
                        StockUiState.Loading
                    }
                    else{
                        StockUiState.Success("")
                    }
                } catch (e: IOException) {
                    StockUiState.Error
                    break
                } catch (e: HttpException) {
                    StockUiState.Error
                }
            }
            delay(1000*60*15)
        }
    }
    fun getSymbol(s:String){
        stockSymbol.value = s
        getStockPhotos()
    }
}
