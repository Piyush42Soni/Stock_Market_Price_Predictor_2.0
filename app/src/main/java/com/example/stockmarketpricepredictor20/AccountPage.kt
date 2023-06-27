package com.example.stockmarketpricepredictor20

import android.graphics.drawable.shapes.Shape
import android.media.Image
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmarketpricepredictor20.ui.theme.*

@Composable
fun AccountPage() {
//    Column(modifier = Modifier.fillMaxSize().background(BackgroundColor)) {
//        val list=listOf("Home","Stats")
//        var state:Int by remember {
//            mutableStateOf(0)
//        }
//        UpperTab(0, titles = list, onStateChangedHai = {state=it})
//
    val list = remember {
        mutableStateListOf<Float>(10f, 20f, 120f, 60f)
    }
    val colorList=listOf<Color>(ProgressColor1, ProgressColor2, ProgressColor3, ProgressColor4)
    val CompanyName= listOf<String>("C1", "C2", "C3", "C4")
    Column(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundColor)
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(((-60)).dp)) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            painter = painterResource(id = R.drawable.tt),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)) {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(120.dp),
                painter = painterResource(id = R.drawable.rrr),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.size(15.dp))
            Text(
                text = "Greetings,",
                fontSize = 15.sp,
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
            Spacer(Modifier.size(10.dp))
            Text(
                text = "Name",
                fontSize = 23.sp,
                style = MaterialTheme.typography.h1,
                color = Color.White
            )
            Spacer(Modifier.size(15.dp))
            Card(
                Modifier
                    .padding(5.dp),
                backgroundColor = CardColor,
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {

                    Text(
                        modifier = Modifier.padding(15.dp),
                        text = "Profession",
                        fontSize = 25.sp,
                        style = MaterialTheme.typography.h2,
                        color = Color.White
                    )
                    Text(
                        modifier = Modifier.padding(15.dp),
                        text = "Intrests",
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.body1,
                        color = Color.White
                    )
                    Text(
                        modifier = Modifier.padding(15.dp),
                        text = "details",
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.body1,
                        color = Color.White
                    )
                }
            }
            Spacer(Modifier.size(15.dp))
            Card(
                Modifier
                    .padding(5.dp)
                    .height(700.dp),
                backgroundColor = CardColor,
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Column(Modifier) {
                        Progressed(radius = 260, strokeWidth = 30.dp, list, colorList)
                    }
                    Spacer(modifier = Modifier.size(50.dp))
                    Card(
                        Modifier
                            .padding(5.dp),
                        backgroundColor = ElementColor,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        LazyColumn {
                            items(count = 10) {
                                when ((it%4)) {
                                    0 -> {
                                        element(name = CompanyName[(it%4)], Pic = R.drawable.m,colorList[(it%4)])
                                    }
                                    1 -> {
                                        element(name = CompanyName[(it%4)], Pic = R.drawable.b,colorList[(it%4)])
                                    }
                                    2 -> {
                                        element(name = CompanyName[(it%4)], Pic = R.drawable.c,colorList[(it%4)])
                                    }
                                    else -> {
                                        element(name = CompanyName[(it%4)], Pic = R.drawable.a,colorList[(it%4)])
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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

@Composable
fun element(name:String,@DrawableRes Pic:Int,color:Color){
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp), backgroundColor = ElementColor, shape = MaterialTheme.shapes.medium)  {
        Row(Modifier, verticalAlignment = Alignment.CenterVertically) {
            Card(Modifier.height(50.dp).padding(horizontal = 10.dp),backgroundColor=(color),shape= RoundedCornerShape(0.dp)){ Spacer(modifier = Modifier.width(20.dp))}
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp),
                painter = painterResource(id = Pic),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(15.dp))
            Text(
                text = name,
                fontSize = 25.sp,
                style = MaterialTheme.typography.h2,
                color = Color.White
            )
        }
    }
}
@Composable
fun Progressed(radius:Int,strokeWidth: Dp,list:MutableList<Float>,listColor:List<Color>) {
    Column(modifier = Modifier
        .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Canvas(
            modifier = Modifier
                .height(radius.dp)
                .width(radius.dp)
        ) {
            var angle=-90f
            val Sum=list.sum()
            Log.d(Sum.toString(),"YESS")
            for(i in 0 until list.size) {
                drawArc(
                    color = (listColor[(i%(listColor.size))]),
                    angle,
                    (360f*((list[i])/Sum)),
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Butt),
                )
                angle+=(360f*(list[i]/Sum))
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewedHai() {
    StockMarketPricePredictor20Theme {
        val homeViewModel: HomeViewModel = viewModel()
        //HomePage(homeViewModel,{})
        //element(name = "RAJU", Pic = R.drawable.m)
    }
}
@Preview(showBackground = true)
@Composable
fun Previewedai() {
    StockMarketPricePredictor20Theme {
        element(name = "RAJU", Pic = R.drawable.m, Color.Black)
    }
}