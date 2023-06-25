package com.example.stockmarketpricepredictor20.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CurrentData(
    @SerialName(value="quoteSummary")
    val quoteSummary:QuoteSummary
)
@Serializable
data class QuoteSummary(
    @SerialName(value="result")
    val result:List<FinancialData>
)
@Serializable
data class FinancialData(
    @SerialName(value="financialData")
    val financialData:StockResult
)
@Serializable
data class StockResult(
    @SerialName(value="currentPrice")
    val currentPrice:RawFmt,
    @SerialName(value="totalCash")
    val totalCash:RawFmt,
    @SerialName(value="ebitda")
    val ebitda:RawFmt,
    @SerialName(value="totalRevenue")
    val totalRevenue:RawFmt,
    @SerialName(value="financialCurrency")
    val financialCurrency:String
)
@Serializable
data class RawFmt(
    @SerialName(value="raw")
    val raw:Float
)

//"financialData":{
//    "maxAge":86400,
//    "currentPrice":{"raw":2233.55,"fmt":"2,233.55"},
//    "targetHighPrice":{},
//    "targetLowPrice":{},
//    "targetMeanPrice":{},
//    "targetMedianPrice":{},
//    "recommendationMean":{},
//    "recommendationKey":"none",
//    "numberOfAnalystOpinions":{},
//    "totalCash":{"raw":55386898432,"fmt":"55.39B","longFmt":"55,386,898,432"},
//    "totalCashPerShare":{"raw":48.604,"fmt":"48.6"},
//    "ebitda":{"raw":91650203648,"fmt":"91.65B","longFmt":"91,650,203,648"},
//    "totalDebt":{"raw":532004601856,"fmt":"532B","longFmt":"532,004,601,856"},
//    "quickRatio":{"raw":0.404,"fmt":"0.40"},
//    "currentRatio":{"raw":0.829,"fmt":"0.83"},
//    "totalRevenue":{"raw":1369777635328,"fmt":"1.37T","longFmt":"1,369,777,635,328"},
//    "debtToEquity":{"raw":140.407,"fmt":"140.41"},
//    "revenuePerShare":{"raw":1206.408,"fmt":"1,206.41"},
//    "returnOnAssets":{"raw":0.03458,"fmt":"3.46%"},
//    "returnOnEquity":{"raw":0.07472,"fmt":"7.47%"},
//    "grossProfits":{},"freeCashflow":{"raw":12793724928,"fmt":"12.79B","longFmt":"12,793,724,928"},
//    "operatingCashflow":{"raw":176264593408,"fmt":"176.26B","longFmt":"176,264,593,408"},
//    "earningsGrowth":{"raw":1.289,"fmt":"128.90%"},"revenueGrowth":{"raw":0.261,"fmt":"26.10%"},
//    "grossMargins":{"raw":0.24869,"fmt":"24.87%"},"ebitdaMargins":{"raw":0.06691,"fmt":"6.69%"},
//    "operatingMargins":{"raw":0.04912,"fmt":"4.91%"},"profitMargins":{"raw":0.01805,"fmt":"1.81%"},
//    "financialCurrency":"INR"}}