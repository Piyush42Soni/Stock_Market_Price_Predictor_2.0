package com.example.stockmarketpricepredictor20

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
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
    onPageChange: (CurrentData) -> Unit
) {
    val SymbolList= listOf<String>("ADANIENT.NS","ADANIPORTS.NS","APOLLOHOSP.NS","ASIANPAINT.NS","AXISBANK.NS","BAJAJ-AUTO.NS","BAJAJFINSV.NS","BAJFINANCE.NS","BHARTIARTL.NS","BPCL.NS","BRITANNIA.NS","CIPLA.NS","COALINDIA.NS","DIVISLAB.NS","DRREDDY.NS","EICHERMOT.NS","GAIL.NS","GRASIM.NS","HCLTECH.NS","HDFC.NS","HDFCBANK.NS","HDFCLIFE.NS","HEROMOTOCO.NS","HINDALCO.NS","HINDUNILVR.NS","ICICIBANK.NS","INDUSINDBK.NS","INFY.NS","ITC.NS","JSWSTEEL.NS","KOTAKBANK.NS","LT.NS","M&M.NS","MARUTI.NS","NESTLEIND.NS","NTPC.NS","ONGC.NS","POWERGRID.NS","RELIANCE.NS","SBILIFE.NS","SBIN.NS","SUNPHARMA.NS","TATACONSUM.NS","TATASTEEL.NS","TCS.NS","TECHM.NS","TITN.NS","ULTRACEMCO.NS","UPL.NS","WIPRO.NS")
    var b1 by rememberSaveable {
        mutableStateOf(1)
    }
    var priceList by rememberSaveable {
        mutableStateOf(mutableListOf<CurrentData>())
    }
        for (it in SymbolList.indices) {
            LaunchedEffect(SymbolList[it]) {
                homeViewModel.getSymbol(SymbolList[it])
            }
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
                            (priceList[it]).companyName=SymbolList[it]
                            onPageChange(priceList[it])
                        }
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = SymbolList[it])
                        Text(
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
