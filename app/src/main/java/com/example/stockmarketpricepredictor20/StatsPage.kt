package com.example.stockmarketpricepredictor20


import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmarketpricepredictor20.ui.theme.*
import java.lang.Float.max
import java.lang.Float.min
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun StatsPage(statsViewModel: StatsViewModel) {
    when (statsViewModel.stockUiState) {
    is StockUiState1.Success-> {
        val shareHoldinglist = remember {
            mutableStateListOf<Float>(statsViewModel.shareHoldingPatternData.quoteSummary.result[0].majorHoldersBreakdown.insidersPercentHeld.raw,statsViewModel.shareHoldingPatternData.quoteSummary.result[0].majorHoldersBreakdown.institutionsPercentHeld.raw,1-statsViewModel.shareHoldingPatternData.quoteSummary.result[0].majorHoldersBreakdown.insidersPercentHeld.raw-statsViewModel.shareHoldingPatternData.quoteSummary.result[0].majorHoldersBreakdown.institutionsPercentHeld.raw)
        }

        val list = listOf<String>("1D", "1W", "1M", "1Y")
        val companyName = listOf<String>("Insiders","Institutions","Others")
        val companyName1 = listOf<String>("C1", "C2", "C3", "C4")
        val colorList = listOf(ProgressColor1, ProgressColor2, ProgressColor3, ProgressColor4)
        var state by remember {
            mutableStateOf(0)
        }

        Column(
            modifier = Modifier
                .background(BackgroundColor)
                .fillMaxSize()
                .padding(15.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AdjustText(
                statsViewModel.companyName,
                textStyleBody1 = Typography.h4,
                color = HeadingColor
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
            Divider(
                color = Teal200,
                modifier = Modifier //fill the max height
                    .fillMaxWidth()
                    .height(2.dp)
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            AdjustText(
                statsViewModel.currentData,
                textStyleBody1 = Typography.h3,
                color = Color.White
            )
            if (statsViewModel.gworth!! >= 0f) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Triangle(radius = 50)
                    AdjustText(
                        text = "+${statsViewModel.gworth}",
                        color = Color.Green,
                        textStyleBody1 = Typography.h6
                    )
                }
            } else {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Triangle(radius = 50)
                    AdjustText(
                        text = "-$statsViewModel.currentData.quoteSummary?.result?.get(0)?.financialData?.earningsGrowth?.raw.toString()",
                        color = Color.Red,
                        textStyleBody1 = Typography.h6
                    )
                }
            }
            segmentedButton(list = list, state = state, onStateChange = { state=it })

            var list3: MutableList<String> by rememberSaveable {
                mutableStateOf(mutableListOf())
            }

            when (state) {
                0 -> {
                    lateinit var d:Date
                    list3= mutableListOf()
                    for (i in (statsViewModel.stockUiState as StockUiState1.Success).photos[state].chart.result[0].timestamp) {
                        d=Date((i*1000))
                        list3.add("${d.hours}:${d.minutes}")
                    }
                }

                1 -> {
                    list3.clear()
                    for (i in (statsViewModel.stockUiState as StockUiState1.Success).photos[state].chart.result[0].timestamp) {
                        val d = Date(i.toLong()*1000)
                        list3.add(
                            when (d.day) {
                                0 -> "Mon"
                                1 -> "Tue"
                                2 -> "Wed"
                                3 -> "Thu"
                                else -> "Fri"
                            }
                        )
                    }
                }

                2 -> {
                    list3= mutableListOf()
                    for (i in (statsViewModel.stockUiState as StockUiState1.Success).photos[state].chart.result[0].timestamp) {
                        val d = Date(i.toLong()*1000)
                        list3.add((d.date).toString())
                    }
                }

                else -> {
                    list3= mutableListOf()
                    for (i in (statsViewModel.stockUiState as StockUiState1.Success).photos[state].chart.result[0].timestamp) {
                        val d = Date(i.toLong()*1000)
                        list3.add(
                            when (d.day) {
                                0 -> "Jan"
                                1 -> "Feb"
                                2 -> "Mar"
                                3 -> "Apr"
                                4 -> "May"
                                5 -> "Jun"
                                6 -> "Jul"
                                7 -> "Aug"
                                8 -> "Sep"
                                9 -> "Oct"
                                10 -> "Nov"
                                else -> "Dec"
                            }
                        )
                    }
                }
            }
            val CompanyDetails =
                listOf<Float>(statsViewModel.indexTrendData.quoteSummary.result[0].indexTrend.peRatio.raw,statsViewModel.indexTrendData.quoteSummary.result[0].indexTrend.pegRatio.raw)
            Spacer(modifier = Modifier.size(15.dp))
            val switchState = remember {
                mutableStateOf(false)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    tint = White,
                    imageVector = Icons.Filled.DataExploration,
                    contentDescription = null
                )
                Switch(
                    checked = switchState.value,
                    onCheckedChange = { switchState.value = it },
                    modifier = Modifier.size(50.dp),
                    colors = SwitchDefaults.colors(uncheckedThumbColor = White)

                )
            }
            val list2= ((statsViewModel.stockUiState as StockUiState1.Success).photos[state].chart.result[0].indicators.quote[0].open.reversed() as MutableList<Float?>)
            list2.replaceAll{it?:statsViewModel.currentData.toFloat()}
            list2 as List<Float>

            Card(
                Modifier
                    .padding(5.dp),
                backgroundColor = CardColor,
                shape = MaterialTheme.shapes.large
            ) {
                val c=((statsViewModel.stockUiState as StockUiState1.Success).photos[state].chart.result[0].indicators.quote[0].open.reversed() as MutableList<Float?>)
                c.replaceAll{it?:statsViewModel.currentData.toFloat()}
                c as MutableList<Float>
                c.replaceAll { it-list2.min() }
                c.reverse()
                list3.reverse()
                if (switchState.value) {
                            Graph1hai(
                                list = c,
                                list2 = list3,
                                Min=list2.min().toInt(),
                                Max=list2.max().toInt()
                            )
                } else {
                    Graphhai(
                        list = c,
                        list2 = list3,
                        Min=list2.min().toInt(),
                        Max=list2.max().toInt()
                    )
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(30.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(Modifier) {
                        AdjustText(
                            "Share Holding Pattern",
                            textStyleBody1 = MaterialTheme.typography.h1,
                            color = White
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                        Progressed(radius = 260, strokeWidth = 30.dp, shareHoldinglist, colorList)
                    }
                    Spacer(modifier = Modifier.size(50.dp))
                    Card(
                        Modifier
                            .padding(5.dp),
                        backgroundColor = ElementColor,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        LazyColumn {
                            items(count = companyName.size) {
                                when ((it % 4)) {
                                    0 -> {
                                        Sharelement(
                                            name = companyName[(it % 4)],
                                            Pic = R.drawable.m,
                                            colorList[(it % 4)]
                                        )
                                    }

                                    1 -> {
                                        Sharelement(
                                            name = companyName[(it % 4)],
                                            Pic = R.drawable.b,
                                            colorList[(it % 4)]
                                        )
                                    }

                                    2 -> {
                                        Sharelement(
                                            name = companyName[(it % 4)],
                                            Pic = R.drawable.c,
                                            colorList[(it % 4)]
                                        )
                                    }

                                    else -> {
                                        Sharelement(
                                            name = companyName[(it % 4)],
                                            Pic = R.drawable.a,
                                            colorList[(it % 4)]
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
//            Card(
//                Modifier
//                    .height(700.dp)
//                    .padding(25.dp),
//                backgroundColor = ElementColor,
//                shape = MaterialTheme.shapes.medium
//            ) {
//                LazyColumn {
//                    items(count = 10) {
//                        when ((it % 4)) {
//                            0 -> {
//                                element(
//                                    name = companyName1[(it % 4)],
//                                    Pic = R.drawable.m,
//                                    colorList[(it % 4)]
//                                )
//                            }
//
//                            1 -> {
//                                element(
//                                    name = companyName1[(it % 4)],
//                                    Pic = R.drawable.b,
//                                    colorList[(it % 4)]
//                                )
//                            }
//
//                            2 -> {
//                                element(
//                                    name = companyName1[(it % 4)],
//                                    Pic = R.drawable.c,
//                                    colorList[(it % 4)]
//                                )
//                            }
//
//                            else -> {
//                                element(
//                                    name = companyName1[(it % 4)],
//                                    Pic = R.drawable.a,
//                                    colorList[(it % 4)]
//                                )
//                                }
//                            }
//                        }
//                    }
//                }
            }
        }
        is StockUiState1.Loading-> LoadingScreen()
        else -> ErrorScreen()
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
                        onClick = {},
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
@SuppressLint("RememberReturnType")
@Composable
fun Graphhai(list: MutableList<Float>,list2:List<String>,Min:Int,Max:Int){
    val list1=when {
        list.isEmpty() -> listOf("0", "50", "100", "150", "250")
        else -> listOf(
            "${(Min).toInt()}",
            "${(Min+((Max-Min) / 5)).toInt()}",
            "${(Min+(2*(Max-Min) / 5)).toInt()}",
            "${(Min+(3*(Max-Min) / 5)).toInt()}",
            "${(Min+(4*(Max-Min) / 5)).toInt()}",
            "${(Max).toInt()}",

        )
    }
    
    var canvasHeight=0f
    var canvasWidth=0f
    var columnWidth=0f
    Column() {
        var state:Float by remember {
            mutableStateOf(200.0f)
        }
        Row(Modifier.padding(15.dp)) {
//            Column(
//                modifier = Modifier.height(state.dp),
//                verticalArrangement = Arrangement.SpaceBetween
//            ) {
//                for (i in list1.indices) {
//                    Text(text = list1[i], color = White, fontSize = 13.sp)
//                }
//            }
//            Spacer(modifier = Modifier.size(10.dp))
            Column() {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Canvas(
                        modifier = Modifier
                            .height(200.dp)
                            .weight(0.95f)
                    ) {
                        canvasHeight = size.width/2
                        canvasWidth = size.width
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
                                    y = (canvasHeight - ((canvasHeight / (list.max())) * list[i]))
                                ),
                                color = Teal200,
                                size = Size(
                                    width = (max(1f, ((canvasWidth / list.size) - 60f))),
                                    height = ((canvasHeight / (list.max())) * list[i])
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
                        drawLine(
                            start = Offset(x = canvasWidth, y = 0f),
                            end = Offset(x = canvasWidth, y = canvasHeight),
                            color = Color.Black,
                            strokeWidth = 5f
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Canvas(
                        modifier = Modifier
                            .height(canvasHeight.dp)
                            .width(50.dp)
                    ) {
                        canvasWidth = size.width
                        var space = 0f
                        for (i in list1.indices) {
                            val rect1 = Rect(Offset(
                                x = 0f,
                                y = canvasHeight-space
                            ), size)
                            drawIntoCanvas { canvas ->
                                rotate(
                                    degrees = 0f, Offset(
                                        x = 0f,
                                        y = (canvasHeight) - space
                                    )
                                ) {
                                    canvas.nativeCanvas.drawText(
                                        list1[i],
                                        rect1.left, rect1.top,
                                        Paint().apply {
                                            color = Color.White.toArgb()
                                            textSize = 50f
                                        }
                                    )
                                }
                            }
                            space += (max(1f, ((canvasHeight / list1.size))))
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun Graph1hai(list: MutableList<Float>,list2:List<String>,Min:Int,Max:Int) {
    var canvasHeight = 0f
    var canvasWidth = 0f
    var columnWidth = 0f
    val list1=when {
        list.isEmpty() -> listOf("0", "50", "100", "150", "250")
        else -> listOf(
            "${(Min).toInt()}",
            "${(Min+((Max-Min) / 5)).toInt()}",
            "${(Min+(2*(Max-Min) / 5)).toInt()}",
            "${(Min+(3*(Max-Min) / 5)).toInt()}",
            "${(Min+(4*(Max-Min) / 5)).toInt()}",
            "${(Max).toInt()}",

            )
    }
    Column() {
        Row(Modifier.padding(15.dp)) {
//            Column(
//                modifier = Modifier.height(300.dp),
//                verticalArrangement = Arrangement.SpaceBetween
//            ) {
//                for (i in list1.indices) {
//                    Text(text = list1[i], color = Color.White, fontSize = 13.sp)
//                }
//            }
            Spacer(modifier = Modifier.size(10.dp))
            Column() {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {


//                    LaunchedEffect(key1 = list, block = {
//                        animationProgress.animateTo(1f, tween(3000))
//                    })
                    Canvas(
                        modifier = Modifier
                            .height(350.dp)
                            .weight(0.9f)
                    ) {
                        canvasHeight = size.width/2
                        canvasWidth = size.width
                        val numberEntries = list.size - 1
                        val weekWidth = canvasWidth / numberEntries
                        var previousBalanceX = 0f
                        var previousBalanceY = canvasHeight
                        val max = list.maxBy { it }
                        val range = max
                        val heightPxPerAmount = canvasHeight /list.max()
                        drawLine(
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 0f, y = canvasHeight),
                            color = Color.Black,
                            strokeWidth = 5f
                        )
                        var space = 30f
                        val path = Path()
                        val FilledPath=Path()
                        FilledPath.moveTo(0f,canvasHeight)
                        path.moveTo(0f,canvasHeight)
                        for (i in 0..list.size - 1) {
                                val balanceX = i * weekWidth
                                val balanceY = canvasHeight - (list[i]) * heightPxPerAmount
                                val controlPoint1 =
                                    PointF((balanceX + previousBalanceX) / 2f, previousBalanceY)
                                val controlPoint2 =
                                    PointF((balanceX + previousBalanceX) / 2f, balanceY)
                                path.cubicTo(
                                    controlPoint1.x,
                                    controlPoint1.y,
                                    controlPoint2.x,
                                    controlPoint2.y,
                                    balanceX,
                                    balanceY
                                )
                                FilledPath.cubicTo(
                                    controlPoint1.x,
                                    controlPoint1.y,
                                    controlPoint2.x,
                                    controlPoint2.y,
                                    balanceX,
                                    balanceY
                                )
                                val rect = Rect(
                                    Offset(
                                        x = balanceX,
                                        y = (canvasHeight) + 40f
                                    ), size
                                )
                                drawIntoCanvas { canvas ->
                                    rotate(
                                        degrees = 90f, Offset(
                                            x = balanceX - 20f,
                                            y = (canvasHeight) + 40f
                                        )
                                    ) {
                                        canvas.nativeCanvas.drawText(
                                            list2[i],
                                            rect.left, rect.top,
                                            Paint().apply {
                                                color = Color.White.toArgb()
                                                textSize =
                                                    if (list2.size > 20) {
                                                        20f
                                                    } else {
                                                        50f
                                                    }
                                            }
                                        )
                                    }
                                previousBalanceX = balanceX
                                previousBalanceY = balanceY
                                space += (java.lang.Float.max(1f, ((canvasWidth / list.size))))
                            }
                        }
                        drawLine(
                            start = Offset(x = 0f, y = canvasHeight),
                            end = Offset(x = canvasWidth, y = canvasHeight),
                            color = Color.Black,
                            strokeWidth = 5f
                        )
                        FilledPath.relativeLineTo(0f, canvasHeight)
                        FilledPath.lineTo(canvasWidth, canvasHeight)
                        FilledPath.close()
                        clipRect(right = canvasWidth) {
                            drawPath(path, Color.Green, style = Stroke(2.dp.toPx()))

                            drawPath(
                                FilledPath,
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color.Green.copy(alpha = 0.4f),
                                        Color.Transparent
                                    )
                                ),
                                style = Fill
                            )
                        }

                    }
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Canvas(
                modifier = Modifier
                    .height(canvasHeight.dp)
                    .width(50.dp)
            ) {
                canvasWidth = size.width
                var space = 0f
                for (i in list1.indices) {
                    val rect1 = Rect(Offset(
                        x = 0f,
                        y = canvasHeight-space
                    ), size)
                    drawIntoCanvas { canvas ->
                        rotate(
                            degrees = 0f, Offset(
                                x = 0f,
                                y = (canvasHeight) - space
                            )
                        ) {
                            canvas.nativeCanvas.drawText(
                                list1[i],
                                rect1.left, rect1.top,
                                Paint().apply {
                                    color = Color.White.toArgb()
                                    textSize = 50f
                                }
                            )
                        }
                    }
                    space += (max(1f, ((canvasHeight / list1.size))))
                }
            }
        }
    }
}
@Composable
fun Details(list:List<Float>){
    val list1=listOf<String>("P/E Ratio","PEG Ratio")
    Card(
        Modifier
            .height(250.dp)
            .fillMaxWidth()
            .padding(5.dp),
        backgroundColor = CardColor,
        shape = MaterialTheme.shapes.large
    ) {
            Column(modifier = Modifier.fillMaxWidth()){
                Column(
                    Modifier
                        .padding(15.dp)
                        .weight(0.5f)) {
                    Text(list1[0], color = Teal200)
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        modifier = Modifier.padding(start = 15.dp),
                        text = list[0].toString(),
                        color = White
                    )
                    Spacer(modifier = Modifier.size(30.dp))
                }
                Column(
                    Modifier
                        .padding(15.dp)
                        .weight(0.5f)) {
                    Text(list1[1], color = Teal200)
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        modifier = Modifier.padding(start = 15.dp),
                        text = list[1].toString(),
                        color = White
                    )
                    Spacer(modifier = Modifier.size(30.dp))
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
            Card(
                Modifier
                    .height(50.dp)
                    .padding(horizontal = 10.dp),backgroundColor=(color),shape= RoundedCornerShape(0.dp)){ Spacer(modifier = Modifier.width(20.dp))}
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
fun Triangle(radius:Int) {
    Column() {
        Canvas(
            modifier = Modifier
                .height(radius.dp)
                .width(radius.dp)
        ) {
            val size = (radius / 1.3).dp.toPx()
            val trianglePath = Path().apply {
                // Moves to top center position
                moveTo((size + 10.dp.toPx()) / 1.8f, (size - 10.dp.toPx()) / 2f)
                // Add line to bottom right corner
                lineTo(
                    (size + 10.dp.toPx()) / 1.8f + 10.dp.toPx(),
                    size - 10.dp.toPx()
                )
                // Add line to bottom left corner
                lineTo(
                    (size + 10.dp.toPx()) / 1.8f - 10.dp.toPx(),
                    size - 10.dp.toPx()
                )
            }
            drawIntoCanvas { canvas ->
                canvas.drawOutline(
                    outline = Outline.Generic(trianglePath),
                    paint = androidx.compose.ui.graphics.Paint().apply {
                        color = Color.Green
                    }
                )
            }
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





