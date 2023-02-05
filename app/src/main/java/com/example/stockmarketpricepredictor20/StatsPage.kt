package com.example.stockmarketpricepredictor20


import android.graphics.Paint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmarketpricepredictor20.ui.theme.*
import java.lang.Float.max
import java.util.*

class StatsViewModel:ViewModel(){
    private val _name=MutableLiveData<List<Float>>()
    val name=_name
    private val _name1=MutableLiveData<List<String>>()
    val name1=_name1
    fun onNameChange(newList: List<Float>){
        _name.value=newList
    }
    fun onName1Change(newList: List<String>){
        _name1.value=newList
    }
}
@Composable
fun StatsPage(statsViewModel: StatsViewModel) {
    val ShareHoldinglist = remember {
        mutableStateListOf<Float>(10f, 20f, 120f, 60f)
    }
    val list= listOf<String>("1D","1W","1M","1Y","Max")
    val CompanyName= listOf<String>("Promoters", "FII", "DII", "Public")
    val CompanyName1= listOf<String>("C1", "C2", "C3", "C4")
    val colorList=listOf(ProgressColor1, ProgressColor2, ProgressColor3, ProgressColor4)
    var state by remember {
        mutableStateOf(0)
    }
    Column(modifier = Modifier
        .background(BackgroundColor)
        .fillMaxSize()
        .padding(15.dp)
        .verticalScroll(rememberScrollState())) {
        segmentedButton(list = list, state =state , onStateChange = {state=it})
        var list3:List<String> =when(state){
            0-> listOf("9:00","11:00","13:00","15:00","17:00")
            1->listOf("Mon","Tue","Wed","Thu","Fri")
            2->listOf("1","2","3","4","5","6","7","8","9","10",
                "11","12","13","14","15","16","17","18","19","20"
            ,"21","22","23","24","25","26","27","28","29","30")
            3->listOf("Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec")
            else->listOf("2018","2019","2020","2021","2022")
        }
        val CompanyDetails=listOf<Float>(1f,2f,3f,4f,5f,6f,7f,8f,9f,10f,11f,12f,13f,14f,15f,16f)
        var list2:MutableList<Float> = mutableListOf<Float>()
        when(state){
            0->{
                for(i in 1..5){
                    val rd = Random() // creating Random object
                    list2.add((rd.nextFloat()*250f))
                }
            }
            1->{
                for(i in 1..5){
                    val rd = Random() // creating Random object
                    list2.add((rd.nextFloat()*250f))
                }
            }
            2->{
                for(i in 1..30){
                    val rd = Random() // creating Random object
                    list2.add((rd.nextFloat()*250f))
                }
            }
            3->{
                for(i in 1..12){
                    val rd = Random() // creating Random object
                    list2.add((rd.nextFloat()*250f))
                }
            }
            else->{
                for(i in 1..5){
                    val rd = Random() // creating Random object
                    list2.add((rd.nextFloat()*250f))
                }
            }
        }
        statsViewModel.onNameChange(list2)
        statsViewModel.onName1Change(list3)
        Spacer(modifier = Modifier.size(15.dp))
        val switchState = remember {
            mutableStateOf(false)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
            Icon(tint = White, imageVector = Icons.Filled.DataExploration, contentDescription = null)
            Switch(
                checked = switchState.value,
                onCheckedChange = { switchState.value = it },
                modifier = Modifier.size(50.dp),
                colors = SwitchDefaults.colors(uncheckedThumbColor = White)

            )
        }
        Card(
            Modifier
                .padding(5.dp),
            backgroundColor = CardColor,
            shape = MaterialTheme.shapes.large
        ) {
            if(switchState.value) {
                statsViewModel.name.value?.let {
                    statsViewModel.name1.value?.let { it1 ->
                        Graph1hai(
                            list = it,
                            list2 = it1
                        )
                    }
                }
            }
            else{
                statsViewModel.name.value?.let {
                    statsViewModel.name1.value?.let { it1 ->
                        Graphhai(
                            list = it,
                            list2 = it1
                        )
                    }
                }
            }
        }

        Details(CompanyDetails)
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
                    AdjustText("Share Holding Pattern", textStyleBody1 = MaterialTheme.typography.h1, color = White)
                    Spacer(modifier = Modifier.size(20.dp))
                    Progressed(radius = 260, strokeWidth = 30.dp, ShareHoldinglist, colorList)
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
                                    Sharelement(name = CompanyName[(it%4)], Pic = R.drawable.m,colorList[(it%4)])
                                }
                                1 -> {
                                    Sharelement(name = CompanyName[(it%4)], Pic = R.drawable.b,colorList[(it%4)])
                                }
                                2 -> {
                                    Sharelement(name = CompanyName[(it%4)], Pic = R.drawable.c,colorList[(it%4)])
                                }
                                else -> {
                                    Sharelement(name = CompanyName[(it%4)], Pic = R.drawable.a,colorList[(it%4)])
                                }
                            }
                        }
                    }
                }
            }
        }
        Card(
            Modifier
                .height(700.dp)
                .padding(25.dp),
            backgroundColor = ElementColor,
            shape = MaterialTheme.shapes.medium
        ) {
            LazyColumn {
                items(count = 10) {
                    when ((it%4)) {
                        0 -> {
                            element(name = CompanyName1[(it%4)], Pic = R.drawable.m,colorList[(it%4)])
                        }
                        1 -> {
                            element(name = CompanyName1[(it%4)], Pic = R.drawable.b,colorList[(it%4)])
                        }
                        2 -> {
                            element(name = CompanyName1[(it%4)], Pic = R.drawable.c,colorList[(it%4)])
                        }
                        else -> {
                            element(name = CompanyName1[(it%4)], Pic = R.drawable.a,colorList[(it%4)])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun segmentedButton(list:List<String>,state:Int,onStateChange:(Int)->Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        for(it in list.indices){
            if (it == 0) {
                if (state == it) {
                    Button(
                        onClick = { onStateChange(it) },
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp)
                    ) {
                        AdjustText(list[it])
                    }
                } else {
                    TextButton(
                        onClick = { onStateChange(it) },
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp),
                        colors= ButtonDefaults.textButtonColors(backgroundColor = BackgroundColor,contentColor= MaterialTheme.colors.primary)
                    ) {
                        AdjustText(list[it],MaterialTheme.colors.primary)
                    }
                }
            } else if (it == list.size - 1) {
                if (state == it) {
                    Button(
                        onClick = { onStateChange(it) },
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp)
                    ) {
                        AdjustText(list[it])
                    }
                } else {
                    TextButton(
                        onClick = { onStateChange(it) },
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp),
                        colors= ButtonDefaults.textButtonColors(backgroundColor = BackgroundColor)
                    ) {
                        AdjustText(list[it],MaterialTheme.colors.primary)
                    }
                }
            } else {
                if (state == it) {
                    Button(
                        onClick = { onStateChange(it) },
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp)
                    ) {
                        AdjustText(list[it])
                    }
                } else {
                    TextButton(
                        onClick = { onStateChange(it) },
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp),
                        colors= ButtonDefaults.textButtonColors(backgroundColor = BackgroundColor)
                    ) {
                        AdjustText(list[it],MaterialTheme.colors.primary)
                    }
                }
            }
        }
    }
}

@Composable
fun AdjustText(text:String, color:Color= Black, textStyleBody1: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.button){
    var textStyle by remember { mutableStateOf(textStyleBody1) }
    var readyToDraw by remember { mutableStateOf(false) }
    Text(
        text = text,
        style = textStyle,
        maxLines = 1,
        color=color,
        softWrap = false,
        modifier = Modifier.drawWithContent {
            if (readyToDraw) drawContent()
        },
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.didOverflowWidth) {
                textStyle = textStyle.copy(fontSize = textStyle.fontSize * 0.9)
            } else {
                readyToDraw = true
            }
        }
    )
}

@Composable
fun Graphhai(list: List<Float>,list2:List<String>){
    val list1=listOf("$250","$200","$150","$100","$50","$0")
    var canvasHeight=0f
    var canvasWidth=0f
    var columnWidth=0f
    Column() {
        Row(Modifier.padding(15.dp)) {
            Column(
                modifier = Modifier.height(300.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in list1.indices) {
                    Text(text = list1[i], color = White, fontSize = 13.sp)
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Column() {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Canvas(
                        modifier = Modifier
                            .height(350.dp)
                            .fillMaxWidth()
                    ) {
                        canvasHeight = 300.dp.toPx()
                        canvasWidth = size.width
                        drawLine(
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 0f, y = canvasHeight),
                            color = Color.Black,
                            strokeWidth = 5f
                        )
                        var space = 30f
                        for (i in list.indices) {
                            drawRoundRect(
                                cornerRadius= CornerRadius(canvasWidth/25f,canvasWidth/25f),
                                topLeft = Offset(
                                    x = space,
                                    y = 0f
                                ),
                                color = BackgroundColor,
                                size = Size(
                                    width = (max(1f, ((canvasWidth / list.size) - 60f))),
                                    height = (canvasHeight)
                                )
                            )
                            drawRoundRect(
                                cornerRadius= CornerRadius(canvasWidth/25f,canvasWidth/25f),
                                topLeft = Offset(
                                    x = space,
                                    y = (canvasHeight - ((canvasHeight / 250f) * list[i]))
                                ),
                                color = Teal200,
                                size = Size(
                                    width = (max(1f, ((canvasWidth / list.size) - 60f))),
                                    height = ((canvasHeight / 250f) * list[i])
                                )
                            )
                            val rect = Rect(Offset(
                                x = space,
                                y = (canvasHeight)+40f
                            ), size)
                            drawIntoCanvas { canvas ->
                                rotate(degrees = 90f, Offset(
                                    x = space,
                                    y = (canvasHeight)+40f)) {
                                    canvas.nativeCanvas.drawText(
                                        list2[i],
                                        rect.left, rect.top,
                                        Paint().apply {
                                            color = Color.White.toArgb()
                                            textSize =
                                                if(list2.size>20){20f}
                                                else{50f}
                                        }
                                    )
                                }
                            }
                            space += (max(1f, ((canvasWidth / list.size))))
                            }
                        drawLine(
                            start = Offset(x = 0f, y = canvasHeight),
                            end = Offset(x = canvasWidth, y = canvasHeight),
                            color = Color.Black,
                            strokeWidth = 5f
                        )
                    }

                }
            }
        }
    }
}
@Composable
fun Graph1hai(list: List<Float>,list2:List<String>){
    val list1=listOf("$250","$200","$150","$100","$50","$0")
    var canvasHeight=0f
    var canvasWidth=0f
    var columnWidth=0f
    Column() {
        Row(Modifier.padding(15.dp)) {
            Column(
                modifier = Modifier.height(300.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in list1.indices) {
                    Text(text = list1[i], color = Color.White, fontSize = 13.sp)
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Column() {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Canvas(
                        modifier = Modifier
                            .height(350.dp)
                            .fillMaxWidth()
                    ) {
                        canvasHeight = 300.dp.toPx()
                        canvasWidth = size.width
                        drawLine(
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 0f, y = canvasHeight),
                            color = Color.Black,
                            strokeWidth = 5f
                        )
                        var space = 30f
                        for (i in 0..list.size-2) {
                            drawLine(
                                start =  Offset(
                                    x = space,
                                    y = (canvasHeight - ((canvasHeight / 250f) * list[i]))
                                ),
                                end=Offset(
                                    x = space+ (java.lang.Float.max(1f, ((canvasWidth / list.size)))),
                                    y = (canvasHeight - ((canvasHeight / 250f) * list[i+1]))
                                ),
                                color = Teal200,
                                strokeWidth = 10f
                            )
                            val rect = Rect(
                                Offset(
                                    x = space,
                                    y = (canvasHeight)+40f
                                ), size)
                            drawIntoCanvas { canvas ->
                                rotate(degrees = 90f, Offset(
                                    x = space,
                                    y = (canvasHeight)+40f)
                                ) {
                                    canvas.nativeCanvas.drawText(
                                        list2[i],
                                        rect.left, rect.top,
                                        Paint().apply {
                                            color = Color.White.toArgb()
                                            textSize =
                                                if(list2.size>20){20f}
                                                else{50f}
                                        }
                                    )
                                }
                            }
                            space += (java.lang.Float.max(1f, ((canvasWidth / list.size))))
                        }
                        val rect = Rect(
                            Offset(
                                x = space,
                                y = (canvasHeight)+40f
                            ), size)
                        drawIntoCanvas { canvas ->
                            rotate(degrees = 90f, Offset(
                                x = space,
                                y = (canvasHeight)+40f)
                            ) {
                                canvas.nativeCanvas.drawText(
                                    list2[list2.size-1],
                                    rect.left, rect.top,
                                    Paint().apply {
                                        color = Color.White.toArgb()
                                        textSize =
                                            if(list2.size>20){20f}
                                            else{50f}
                                    }
                                )
                            }
                        }
                        space += (java.lang.Float.max(1f, ((canvasWidth / list.size))))
                        drawLine(
                            start = Offset(x = 0f, y = canvasHeight),
                            end = Offset(x = canvasWidth, y = canvasHeight),
                            color = Color.Black,
                            strokeWidth = 5f
                        )
                    }

                }
            }
        }
    }
}
@Composable
fun Details(list:List<Float>){
    val list1=listOf<String>("Market Cap","Enterprise Value","No. of shares","P/E","P/B","Face Value","Div. Yield","Book Value","Cash","Debt","Promoter Holding","EPS","Sales Growth","ROE","ROCE","Profit Growth")
    Card(
        Modifier
            .size(850.dp)
            .padding(5.dp),
        backgroundColor = CardColor,
        shape = MaterialTheme.shapes.large
    ) {
        Column(){
            for(i in 0..14){
                Row(modifier = Modifier.fillMaxWidth()){
                    Column(Modifier.padding(15.dp).weight(0.5f)) {
                        Text(list1[i], color = Teal200)
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            modifier = Modifier.padding(start = 15.dp),
                            text = list[i].toString(),
                            color = White
                        )
                        Spacer(modifier = Modifier.size(30.dp))
                    }
                    Column(Modifier.padding(15.dp).weight(0.5f)) {
                        Text(list1[i+1], color = Teal200)
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            modifier = Modifier.padding(start = 15.dp),
                            text = list[i+1].toString(),
                            color = White
                        )
                        Spacer(modifier = Modifier.size(30.dp))
                    }
                    i.inc()
                }

            }
        }
    }
}
@Composable
fun Sharelement(name:String, @DrawableRes Pic:Int, color:Color){
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp), backgroundColor = ElementColor, shape = MaterialTheme.shapes.medium)  {
        Row(Modifier, verticalAlignment = Alignment.CenterVertically) {
            Card(Modifier.height(50.dp).padding(horizontal = 10.dp),backgroundColor=(color),shape= RoundedCornerShape(0.dp)){ Spacer(modifier = Modifier.width(20.dp))}
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
@Preview(showBackground = true)
@Composable
fun PreviewedHaiTharhega() {
    StockMarketPricePredictor20Theme {
        val statsViewModel:StatsViewModel=viewModel()
        StatsPage(statsViewModel)
    }
}