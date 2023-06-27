package com.example.stockmarketpricepredictor20.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoricalData(
    @SerialName("chart")
    val chart:Chart
)
@Serializable
data class Chart(
    @SerialName("result")
    val result:List<HistoricalResult>
    )
@Serializable
data class HistoricalResult(
    @SerialName("meta")
    val meta:Meta,
    @SerialName("indicators")
    val indicators:Indicators,
    @SerialName("timestamp")
    val timestamp:List<Int>
)
@Serializable
data class Indicators(
    @SerialName("quote")
    val quote:List<Quote> ,
)
@Serializable
data class Meta(
    @SerialName("range")
    val range:String
)
@Serializable
data class Quote(
    @SerialName("open")
    val open:List<Float> ,
    @SerialName("close")
    val close:List<Float> ,
    @SerialName("high")
    val high:List<Float> ,
    @SerialName("volume")
    val volume:List<Int> ,
    @SerialName("low")
    val low:List<Float>
)
//"chart"{
//    "result":[{
//        "meta":{
//            "currency":"INR",
//            "symbol":"ADANIENT.NS",
//            "exchangeName":"NSI",
//            "instrumentType":"EQUITY",
//            "firstTradeDate":1025495100,
//            "regularMarketTime":1687860000,
//            "gmtoffset":19800,
//            "timezone":"IST",
//            "exchangeTimezoneName":"Asia/Kolkata",
//            "regularMarketPrice":2284.45,
//            "chartPreviousClose":2295.6,
//            "previousClose":2295.6,
//            "scale":3,
//            "priceHint":2,
//            "currentTradingPeriod":{"pre":{"timezone":"IST","start":1687837500,"end":1687837500,"gmtoffset":19800},"regular":{"timezone":"IST","start":1687837500,"end":1687860000,"gmtoffset":19800},"post":{"timezone":"IST","start":1687860000,"end":1687860000,"gmtoffset":19800}},
//            "tradingPeriods":[[{"timezone":"IST","start":1687837500,"end":1687860000,"gmtoffset":19800}]],
//            "dataGranularity":"15m",
//            "range":"1d",
//            "validRanges":["1d","5d","1mo","3mo","6mo","1y","2y","5y","10y","ytd","max"]
//         }
//    ,"timestamp":[1687837500,1687838400,1687839300,1687840200,1687841100,1687842000,1687842900,1687843800,1687844700,1687845600,1687846500,1687847400,1687848300,1687849200,1687850100,1687851000,1687851900,1687852800,1687853700,1687854600,1687855500,1687856400,1687857300,1687858200,1687859100,1687860000],
//    "indicators":{
//        "quote":[{
//            "open":[2324.949951171875,2312.550048828125,2310.949951171875,2313.949951171875,2308.449951171875,2304.050048828125,2298.199951171875,2305.800048828125,2309.0,2306.0,2295.449951171875,2301.800048828125,2301.050048828125,2304.10009765625,2297.0,2297.64990234375,2299.050048828125,2305.949951171875,2302.5,2306.050048828125,2302.0,2301.0,2297.449951171875,2293.0,2290.0,2284.449951171875],
//            "low":[2312.0,2302.0,2304.699951171875,2305.300048828125,2298.300048828125,2290.050048828125,2298.0,2302.0,2303.050048828125,2295.300048828125,2292.89990234375,2295.0,2300.300048828125,2296.0,2295.0,2296.60009765625,2297.699951171875,2300.0,2300.5,2300.050048828125,2295.0,2297.14990234375,2291.0,2289.800048828125,2272.300048828125,2284.449951171875],
//            "close":[2312.0,2310.949951171875,2313.949951171875,2308.050048828125,2304.050048828125,2298.60009765625,2305.800048828125,2309.0,2306.0,2295.800048828125,2300.75,2301.050048828125,2304.10009765625,2298.64990234375,2296.550048828125,2299.050048828125,2305.949951171875,2302.949951171875,2306.0,2302.550048828125,2299.550048828125,2297.25,2293.0,2290.0,2283.89990234375,2284.449951171875],
//            "volume":[573159,296209,163179,118944,150242,181632,89911,82489,54011,84195,120941,64575,72411,66423,62838,33365,56350,66471,80285,49672,87165,51614,118765,147201,362229,0],
//            "high":[2342.89990234375,2321.14990234375,2317.85009765625,2313.949951171875,2308.85009765625,2305.85009765625,2306.0,2310.0,2310.0,2308.35009765625,2304.0,2303.60009765625,2307.0,2304.64990234375,2299.699951171875,2300.89990234375,2306.5,2308.0,2307.949951171875,2306.050048828125,2303.949951171875,2301.89990234375,2298.0,2299.0,2290.050048828125,2284.449951171875]
//        }]
//    }
// }]
//}

//https://query1.finance.yahoo.com/v8/finance/chart/ADANIENT.NS?metrics=high?&interval=15m&range=1d