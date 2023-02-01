package com.example.stockmarketpricepredictor20

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmarketpricepredictor20.ui.theme.StockMarketPricePredictor20Theme

@Composable
fun StatsPage() {
    val list= listOf<String>("Daily","Weekly","Monthy","Yearly","Max")
    var state by remember {
        mutableStateOf(0)
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)) {
        segmentedButton(list = list, state =state , onStateChange = {state=it})

    }

}

@Composable
fun segmentedButton(list:List<String>,state:Int,onStateChange:(Int)->Unit) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(list.size){
            if (it == 0) {
                if (state == it) {
                    Button(
                        onClick = { onStateChange(it) },
                        shape = RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
                    ) {
                        Text(text = list[it], style = MaterialTheme.typography.button, fontSize = 11.sp)
                    }
                } else {
                    OutlinedButton(
                        onClick = { onStateChange(it) },
                        shape = RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
                    ) {
                        Text(text = list[it], style = MaterialTheme.typography.button, fontSize = 11.sp)
                    }
                }
            } else if (it == list.size - 1) {
                if (state == it) {
                    Button(
                        onClick = { onStateChange(it) },
                        shape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
                    ) {
                        Text(text = list[it], style = MaterialTheme.typography.button, fontSize = 11.sp)
                    }
                } else {
                    OutlinedButton(
                        onClick = { onStateChange(it) },
                        shape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
                    ) {
                        Text(text = list[it], style = MaterialTheme.typography.button, fontSize = 11.sp)
                    }
                }
            } else {
                if (state == it) {
                    Button(
                        onClick = { onStateChange(it) },
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Text(text = list[it], style = MaterialTheme.typography.button, fontSize = 11.sp)
                    }
                } else {
                    OutlinedButton(
                        onClick = { onStateChange(it) },
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Text(text = list[it], style = MaterialTheme.typography.button, fontSize = (10.5).sp)
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewedHaiTharhega() {
    StockMarketPricePredictor20Theme {
        StatsPage()
    }
}