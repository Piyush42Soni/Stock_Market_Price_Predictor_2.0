package com.example.stockmarketpricepredictor20.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShareHoldingPatternData(
    @SerialName(value = "quoteSummary")
    val quoteSummary: QuoteSummaryShareHolding= QuoteSummaryShareHolding()
)

@Serializable
data class QuoteSummaryShareHolding(
    @SerialName(value="result")
    val result: List<FinancialDataHolding> = listOf()
)

@Serializable
data class FinancialDataHolding(
    @SerialName("majorHoldersBreakdown")
    val majorHoldersBreakdown:MajorHoldersBreakdown= MajorHoldersBreakdown()
)

@Serializable
data class MajorHoldersBreakdown(
    @SerialName("insidersPercentHeld")
    val insidersPercentHeld:RawFmt= RawFmt(),
    @SerialName("institutionsPercentHeld")
    val institutionsPercentHeld:RawFmt=RawFmt()
)

//{
//    "quoteSummary": {
//        "result": [
//            {
//                "majorHoldersBreakdown": {
//                    "maxAge": 1,
//                    "insidersPercentHeld": {
//                        "raw": 0.74559,
//                        "fmt": "74.56%"
//                    },
//                    "institutionsPercentHeld": {
//                        "raw": 0.11185,
//                        "fmt": "11.19%"
//                    },
//                    "institutionsFloatPercentHeld": {
//                        "raw": 0.43965,
//                        "fmt": "43.96%"
//                    },
//                    "institutionsCount": {
//                        "raw": 121,
//                        "fmt": "121",
//                        "longFmt": "121"
//                    }
//                }
//            }
//        ],
//        "error": null
//    }
//}
