package com.example.stockmarketpricepredictor20

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmarketpricepredictor20.data.CurrentData
import kotlinx.coroutines.cancel
import java.lang.Integer.min

@Composable
fun HomePage(
    homeViewModel: HomeViewModel, modifier: Modifier = Modifier,
    onPageChange: (String,String,Float) -> Unit
) {
    val SymbolList= listOf<String>("ADANIENT.NS","ADANIPORTS.NS","APOLLOHOSP.NS","ASIANPAINT.NS","AXISBANK.NS","BAJAJ-AUTO.NS","BAJAJFINSV.NS","BAJFINANCE.NS","BHARTIARTL.NS","BPCL.NS","BRITANNIA.NS","CIPLA.NS","COALINDIA.NS","DIVISLAB.NS","DRREDDY.NS","EICHERMOT.NS","GAIL.NS","GRASIM.NS","HCLTECH.NS","HDFC.NS","HDFCBANK.NS","HDFCLIFE.NS","HEROMOTOCO.NS","HINDALCO.NS","HINDUNILVR.NS","ICICIBANK.NS","INDUSINDBK.NS","INFY.NS","ITC.NS","JSWSTEEL.NS","KOTAKBANK.NS","LT.NS","M&M.NS","MARUTI.NS","NESTLEIND.NS","NTPC.NS","ONGC.NS","POWERGRID.NS","RELIANCE.NS","SBILIFE.NS","SBIN.NS","SUNPHARMA.NS","TATACONSUM.NS","TATASTEEL.NS","TCS.NS","TECHM.NS","TITN.NS","ULTRACEMCO.NS","UPL.NS","WIPRO.NS")
    var b1 by rememberSaveable {
        mutableStateOf(1)
    }
    var priceList by rememberSaveable {
        mutableStateOf(mutableListOf<CurrentData>())
    }
        for (it in SymbolList.indices) {
//            LaunchedEffect(SymbolList[it]) {
//                homeViewModel.getSymbol(SymbolList[it])
//            }
            when (homeViewModel.stockUiState) {
                is StockUiState.Loading -> b1 = 1
                is StockUiState.Error -> {
                    b1 = 2
                    break
                }
                else -> {
                    //priceList.add((homeViewModel.stockUiState as StockUiState.Success).photos)
                    if (it == SymbolList.size - 1) {
                        b1 = 3
                    }
                }
            }
        }
    if(homeViewModel.statement.size>=SymbolList.size){
        b1=3
    }
    priceList=homeViewModel.statement
    LazyColumn {
        when(b1) {
            1 -> item { LoadingScreen() }
            2-> item { ErrorScreen() }
            3-> items(count=min(SymbolList.size,priceList.size)){

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .clickable {
                            val companyName = SymbolList[it]
                            onPageChange(
                                priceList[it].quoteSummary.result[0].financialData.currentPrice.raw.toString(),
                                companyName,
                                priceList[it].quoteSummary.result[0].financialData.earningsGrowth.raw
                            )
                        }
                ) {
                    @DrawableRes var Pic by rememberSaveable {
                        mutableStateOf(R.drawable.adanient)
                    }
                    Pic=when(SymbolList[it]){
                        ("ADANIENT.NS")->R.drawable.adanient
                        "ADANIPORTS.NS"->R.drawable.adaniports
                                ("APOLLOHOSP.NS")->R.drawable.apollohosp
                                ("ASIANPAINT.NS")->R.drawable.asianpaint
                                ("AXISBANK.NS")->R.drawable.axisbank
                                ("BAJAJ-AUTO.NS")->R.drawable.bajajauto
                                ("BAJAJFINSV.NS")->R.drawable.bajajfinsiv
                                ("BAJFINANCE.NS")->R.drawable.bajajfinance
                                ("BHARTIARTL.NS")->R.drawable.bhartiairtel
                                "BPCL.NS"->R.drawable.bpcl
                        "BRITANNIA.NS"->R.drawable.britannia
                        "CIPLA.NS"->R.drawable.cipla
                            "COALINDIA.NS"->R.drawable.coalindia
                        "DIVISLAB.NS"->R.drawable.divis
                        "DRREDDY.NS"->R.drawable.drreddy
                            "EICHERMOT.NS"->R.drawable.eichermot
                        "GAIL.NS"->R.drawable.gail
                        "GRASIM.NS"->R.drawable.grasim
                            "HCLTECH.NS"->R.drawable.hcltech
                        "HDFC.NS"->R.drawable.hdfc
                        "HDFCBANK.NS"->R.drawable.hdfcbank
                            "HDFCLIFE.NS"->R.drawable.hdfclife
                        "HEROMOTOCO.NS"->R.drawable.heromoto
                        "HINDALCO.NS"->R.drawable.hindalco
                            "HINDUNILVR.NS"->R.drawable.unilever
                        "ICICIBANK.NS"->R.drawable.icicibank
                        "INDUSINDBK.NS"->R.drawable.indusindbank
                            "INFY.NS"->R.drawable.infosys
                        "ITC.NS"->R.drawable.itc
                        "JSWSTEEL.NS"->R.drawable.jwssteel
                            "KOTAKBANK.NS"->R.drawable.kotakbank
                        "LT.NS"->R.drawable.lt
                        "M&M.NS"->R.drawable.mandm
                            "MARUTI.NS"->R.drawable.maruti
                        "NESTLEIND.NS"->R.drawable.nestle
                        "NTPC.NS"->R.drawable.ntpc
                            "ONGC.NS"->R.drawable.ongc
                        "POWERGRID.NS"->R.drawable.powergrid
                        "RELIANCE.NS"->R.drawable.reliance
                            "SBILIFE.NS"->R.drawable.sbilife
                        "SBIN.NS"->R.drawable.sbin
                        "SUNPHARMA.NS"->R.drawable.sunpharma
                            "TATACONSUM.NS"->R.drawable.tataconsumer
                        "TATASTEEL.NS"->R.drawable.tatasteel
                        "TCS.NS"->R.drawable.tcs
                            "TECHM.NS"->R.drawable.techm
                        "TITN.NS"->R.drawable.titan
                        "ULTRACEMCO.NS"->R.drawable.ultratech
                            "UPL.NS"->R.drawable.upl
                        "WIPRO.NS"->R.drawable.wipro

                        else -> {R.drawable.adanient}
                    }
                    Row(Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(50.dp),
                            painter = painterResource(id = Pic),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.size(10.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AdjustText(text = SymbolList[it], color = Color.White)
                            AdjustText(
                                text = priceList[it].quoteSummary?.result?.get(0)?.financialData?.currentPrice?.raw.toString(),
                                color =
                                if (priceList[it].quoteSummary?.result?.get(0)?.financialData?.earningsGrowth?.raw!! >= 0f) {
                                    Color.Green
                                } else {
                                    Color.Red
                                }

                            )

                        }
                        Divider(Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}
/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = "Loading"
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = "loading failed.NS", modifier = Modifier.padding(16.dp))
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun ResultScreen(photos: String, modifier: Modifier = Modifier,onStatementChange:(String)->Unit) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(Modifier.fillMaxSize()) {
            Button(onClick = { onStatementChange(photos) }) {
                Text("Click")
            }
            Text(text = photos)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen()
}

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    var statement: String by rememberSaveable {
        mutableStateOf("")
    }
    ResultScreen(statement,modifier=Modifier.fillMaxWidth(), onStatementChange = {statement="YOHO"})

}
