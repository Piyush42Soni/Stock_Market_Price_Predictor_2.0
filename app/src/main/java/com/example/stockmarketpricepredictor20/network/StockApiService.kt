package com.example.stockmarketpricepredictor20.network

import com.example.stockmarketpricepredictor20.data.CurrentData
import com.example.stockmarketpricepredictor20.data.HistoricalData
import com.example.stockmarketpricepredictor20.data.IndexTrendData
import com.example.stockmarketpricepredictor20.data.ShareHoldingPatternData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL1 =
    "https://query1.finance.yahoo.com/v10/finance/quoteSummary/"
private const val BASE_URL2 =
    "https://query1.finance.yahoo.com/v8/finance/chart/"


/**
 * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL1)
    .build()
private val retrofit1 = Retrofit.Builder()
    .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL2)
    .build()

/**
 * Retrofit service object for creating api calls
 */
interface StockApiService {
    @GET("{symbol}?modules=financialData")
    suspend fun getCurrentData(@Path("symbol") s:String): CurrentData
    @GET("{symbol}?modules=indexTrend")
    suspend fun getIndexTrend(@Path("symbol") s:String): IndexTrendData
    @GET("{symbol}?modules=majorHoldersBreakdown")
    suspend fun getShareHoldingPattern(@Path("symbol") s:String): ShareHoldingPatternData
    @GET("{symbol}?metrics=high?&interval=15m&range=1d")
    suspend fun getHistoricalData(@Path("symbol") s:String): HistoricalData
    @GET("{symbol}?metrics=high?&interval=1d&range=1mo")
    suspend fun getHistoricalDataMonthly(@Path("symbol") s:String): HistoricalData
    @GET("{symbol}?metrics=high?&interval=1mo&range=1y")
    suspend fun getHistoricalDataYearly(@Path("symbol") s:String): HistoricalData
    @GET("{symbol}?metrics=high?&interval=1d&range=5d")
    suspend fun getHistoricalDataWeekly(@Path("symbol") s:String): HistoricalData

}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object StockApi {
    val retrofitService: StockApiService by lazy {
        retrofit.create(StockApiService::class.java)
    }
    val retrofitService1: StockApiService by lazy {
        retrofit1.create(StockApiService::class.java)
    }
}

