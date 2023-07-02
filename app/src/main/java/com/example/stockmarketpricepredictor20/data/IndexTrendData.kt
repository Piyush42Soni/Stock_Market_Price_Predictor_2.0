package com.example.stockmarketpricepredictor20.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IndexTrendData(
    @SerialName(value="quoteSummary")
    val quoteSummary:QuoteSummaryIndexTrend= QuoteSummaryIndexTrend()
)

@Serializable
data class QuoteSummaryIndexTrend(
    @SerialName(value = "result")
    val result: List<TrendIndexResult> =listOf()
)

@Serializable
data class TrendIndexResult(
    @SerialName(value = "indexTrend")
    val indexTrend:IndexTrend= IndexTrend()
    )

@Serializable
data class IndexTrend(
    @SerialName(value = "peRatio")
    val peRatio:RawFmt= RawFmt(),
    @SerialName(value = "pegRatio")
    val pegRatio:RawFmt=RawFmt()
)
//{
//    "quoteSummary": {
//        "result": [
//            {
//                "indexTrend": {
//                    "maxAge": 1,
//                    "symbol": "SP5",
//                    "peRatio": {
//                        "raw": 16.0474,
//                        "fmt": "16.05"
//                    },
//                    "pegRatio": {
//                        "raw": 0.708537,
//                        "fmt": "0.71"
//                    },
//                    "estimates": [
//                        {
//                            "period": "0q",
//                            "growth": {
//                                "raw": -0.001,
//                                "fmt": "-0.00"
//                            }
//                        },
//                        {
//                            "period": "+1q",
//                            "growth": {
//                                "raw": 0.073,
//                                "fmt": "0.07"
//                            }
//                        },
//                        {
//                            "period": "0y",
//                            "growth": {
//                                "raw": 0.019,
//                                "fmt": "0.02"
//                            }
//                        },
//                        {
//                            "period": "+1y",
//                            "growth": {
//                                "raw": 0.102,
//                                "fmt": "0.10"
//                            }
//                        },
//                        {
//                            "period": "+5y",
//                            "growth": {
//                                "raw": 0.0777972,
//                                "fmt": "0.08"
//                            }
//                        },
//                        {
//                            "period": "-5y",
//                            "growth": {}
//                        }
//                    ]
//                }
//            }
//        ],
//        "error": null
//    }
//}