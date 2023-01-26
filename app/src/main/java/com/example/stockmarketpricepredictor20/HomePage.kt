package com.example.stockmarketpricepredictor20

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.stockmarketpricepredictor20.ui.theme.BackgroundColor
import com.example.stockmarketpricepredictor20.ui.theme.StockMarketPricePredictor20Theme
import com.example.stockmarketpricepredictor20.ui.theme.TopNavigationBarColor

@Composable
fun HomePage() {
//    Column(modifier = Modifier.fillMaxSize().background(BackgroundColor)) {
//        val list=listOf("Home","Stats")
//        var state:Int by remember {
//            mutableStateOf(0)
//        }
//        UpperTab(0, titles = list, onStateChangedHai = {state=it})
//
    Column(modifier = Modifier.fillMaxSize()) {
        Image(modifier = Modifier.fillMaxWidth(), painter = painterResource(id = R.drawable.tt), contentDescription = null)
    }
}
@Composable
fun UpperTab(state: Int, titles:List<String>, onStateChangedHai:(Int)->Unit){
    TabRow(selectedTabIndex = state,
        backgroundColor = TopNavigationBarColor,
        contentColor = Color.White) {
        titles.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = state == index,
                onClick = { onStateChangedHai(index) },
                icon = {
                    if(index==0){
                        Icon(imageVector = Icons.Default.Home,"Data")
                    }
                    else{
                        Icon(imageVector = Icons.Default.DataExploration,"Map")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewedHai() {
    StockMarketPricePredictor20Theme {
        HomePage()
    }
}