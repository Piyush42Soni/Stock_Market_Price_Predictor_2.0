package com.example.stockmarketpricepredictor20

import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmarketpricepredictor20.ui.theme.*
import kotlinx.coroutines.launch
import java.lang.Float.max
import java.util.*
import kotlin.math.min

@Composable
fun ComparePage(statsViewModel: StatsViewModel,statsViewModel2: StatsViewModel) {
    var mSelectedText:String by rememberSaveable{ mutableStateOf("ADANIENT.NS") }
    var mSelectedText2:String by rememberSaveable{ mutableStateOf("ADANIENT.NS") }
    when (statsViewModel.stockUiState) {
        is StockUiState1.Loading -> LoadingScreen()
        is StockUiState1.Success -> {
            when (statsViewModel2.stockUiState) {
                is StockUiState1.Success -> {
                    val list = listOf<String>("1D", "1W", "1M", "1Y")
                    var state by remember {
                        mutableStateOf(0)
                    }

                    var list3
                            : MutableList<String> by rememberSaveable {
                        mutableStateOf(mutableListOf())
                    }
                    when (state) {
                        0 -> {
                            lateinit var d: Date
                            list3.clear()
                            for (i in (statsViewModel.stockUiState as StockUiState1.Success).photos[state].chart.result[0].timestamp) {
                                d = Date((i * 1000))
                                list3.add("${d.hours}:${d.minutes}")
                            }
                        }

                        1 -> {
                            list3.clear()
                            for (i in (statsViewModel.stockUiState as StockUiState1.Success).photos[state].chart.result[0].timestamp) {
                                val d = Date(i.toLong() * 1000)
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
                            list3.clear()
                            for (i in (statsViewModel.stockUiState as StockUiState1.Success).photos[state].chart.result[0].timestamp) {
                                val d = Date(i.toLong() * 1000)
                                list3.add((d.date).toString())
                            }
                        }

                        else -> {
                            list3.clear()
                            for (i in (statsViewModel.stockUiState as StockUiState1.Success).photos[state].chart.result[0].timestamp) {
                                val d = Date(i.toLong() * 1000)
                                list3.add(
                                    when (d.month) {
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
                        listOf<Float>(
                            statsViewModel.indexTrendData.quoteSummary.result[0].indexTrend.peRatio.raw,
                            statsViewModel.indexTrendData.quoteSummary.result[0].indexTrend.pegRatio.raw
                        )

                    val CompanyDetails2 =
                        listOf<Float>(
                            statsViewModel2.indexTrendData.quoteSummary.result[0].indexTrend.peRatio.raw,
                            statsViewModel2.indexTrendData.quoteSummary.result[0].indexTrend.pegRatio.raw
                        )

                    val list2 =
                        ((statsViewModel.stockUiState as StockUiState1.Success).photos[state].chart.result[0].indicators.quote[0].open.reversed() as MutableList<Float?>)
                    val list4 =
                        ((statsViewModel2.stockUiState as StockUiState1.Success).photos[state].chart.result[0].indicators.quote[0].open.reversed() as MutableList<Float?>)
                    val c=((statsViewModel.stockUiState as StockUiState1.Success).photos[state].chart.result[0].indicators.quote[0].open.reversed() as MutableList<Float?>)
                    for(i in c.indices){
                        if(i==0 && c[i]==null){
                            var y=1
                            while(y<c.size && c[y]==null){
                                y++
                            }
                            if(y<c.size) {
                                c[i] = c[y]
                                list2[i] = c[y]
                            }
                            else{
                                c[i]=0f
                                list2[i]=0f
                            }
                        }
                        else if(c[i]==null){
                            var y=i-1
                            while(y>=0 && c[y]==null){
                                y--
                            }
                            if(y==-1){
                                y=0
                                while(y<c.size && c[y]==null){
                                    y++
                                }
                            }
                            if(y<c.size) {
                                c[i] = c[y]
                                list2[i] = c[y]
                            }
                            else{
                                c[i]=0f
                                list2[i]=0f
                            }
                        }
                    }
                    list2 as List<Float>
                    c as MutableList<Float>
                    c.replaceAll { it-list2.min() }
                    c.reverse()
                    list3.reverse()
                    val c1=((statsViewModel2.stockUiState as StockUiState1.Success).photos[state].chart.result[0].indicators.quote[0].open.reversed() as MutableList<Float?>)
                    for(i in c1.indices){
                        if(i==0 && c1[i]==null){
                            var y=1
                            while(y<c1.size && c1[y]==null){
                                y++
                            }
                            if(y<c1.size) {
                                c1[i] = c1[y]
                                list4[i] = c1[y]
                            }
                            else{
                                c1[i]=0f
                                list4[i]=0f
                            }
                        }
                        else if(c1[i]==null){
                            var y=i-1
                            while(y>=0 && c1[y]==null){
                                y--
                            }
                            if(y==-1){
                                y=0
                                while(y<c1.size && c1[y]==null){
                                    y++
                                }
                            }
                            if(y<c1.size) {
                                c1[i] = c1[y]
                                list4[i] = c1[y]
                            }
                            else{
                                c1[i]=0f
                                list4[i]=0f
                            }
                        }
                    }
                    list4 as List<Float>
                    c1 as MutableList<Float>
                    c1.replaceAll { it-list4.min() }
                    c1.reverse()
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BackgroundColor)
                            .verticalScroll(rememberScrollState())
                    ) {
                        segmentedButton(list = list, state = state, onStateChange = { state = it })
                        Spacer(modifier = Modifier.size(15.dp))
                        val switchState = remember {
                            mutableStateOf(false)
                        }
                        Spacer(modifier = Modifier.size(15.dp))
                        MyContent(label1 = "Company1", mSelectedText) {
                            mSelectedText = it
                            statsViewModel.viewModelScope.launch {
                                statsViewModel.getSymbol(it)
                            }
                        }
                        MyContent(label1 = "Company2", mSelectedText2) {
                            mSelectedText2 = it
                            statsViewModel2.viewModelScope.launch {
                                statsViewModel2.getSymbol(it)
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                tint = Color.White,
                                imageVector = Icons.Filled.DataExploration,
                                contentDescription = null
                            )
                            Switch(
                                checked = switchState.value,
                                onCheckedChange = { switchState.value = it },
                                modifier = Modifier.size(50.dp),
                                colors = SwitchDefaults.colors(uncheckedThumbColor = Color.White)
                            )
                        }
                        Card(
                            Modifier
                                .padding(5.dp),
                            backgroundColor = CardColor,
                            shape = MaterialTheme.shapes.large
                        ) {
                            if (switchState.value) {
                                CompareGraph1hai(c, list3, c1)
                            } else {
                                CompareGraphhai(c, list3, c1)
                            }
                        }
                        Spacer(modifier = Modifier.size(15.dp))
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
                                    .padding(30.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val colorList = listOf<Color>(Purple200, Teal200)
                                val list6 = mutableListOf(100f, 300f)
                                Column(Modifier) {
                                    Progressed(radius = 260, strokeWidth = 30.dp, list6, colorList)
                                }
                                Spacer(modifier = Modifier.size(50.dp))
                                Card(
                                    Modifier
                                        .padding(5.dp),
                                    backgroundColor = ElementColor,
                                    shape = MaterialTheme.shapes.medium
                                ) {
                                    LazyColumn {
                                        item() {
                                            when ((mSelectedText)) {
                                                "C1" -> {
                                                    element(
                                                        name = mSelectedText,
                                                        Pic = R.drawable.m,
                                                        Purple200
                                                    )
                                                }

                                                "C2" -> {
                                                    element(
                                                        name = mSelectedText,
                                                        Pic = R.drawable.a,
                                                        Purple200
                                                    )
                                                }

                                                "C3" -> {
                                                    element(
                                                        name = mSelectedText,
                                                        Pic = R.drawable.b,
                                                        Purple200
                                                    )
                                                }

                                                else -> {
                                                    element(
                                                        name = mSelectedText,
                                                        Pic = R.drawable.c,
                                                        Purple200
                                                    )
                                                }
                                            }
                                        }
                                        item() {
                                            when ((mSelectedText2)) {
                                                "C1" -> {
                                                    element(
                                                        name = mSelectedText2,
                                                        Pic = R.drawable.m,
                                                        Teal200
                                                    )
                                                }

                                                "C2" -> {
                                                    element(
                                                        name = mSelectedText2,
                                                        Pic = R.drawable.a,
                                                        Teal200
                                                    )
                                                }

                                                "C3" -> {
                                                    element(
                                                        name = mSelectedText2,
                                                        Pic = R.drawable.b,
                                                        Teal200
                                                    )
                                                }

                                                else -> {
                                                    element(
                                                        name = mSelectedText2,
                                                        Pic = R.drawable.c,
                                                        Teal200
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                is StockUiState1.Loading -> LoadingScreen()
                else -> ErrorScreen()
            }
        }
        else -> LoadingScreen()
    }
}
@Composable
fun MyContent(
    label1: String,
    mSelectedText: String,
    onmSelectedTextChange: (String) -> Unit
) {
    var mExpanded by remember { mutableStateOf(false) }
    val mCities = listOf("ADANIENT.NS","ADANIPORTS.NS","APOLLOHOSP.NS","ASIANPAINT.NS","AXISBANK.NS","BAJAJ-AUTO.NS","BAJAJFINSV.NS","BAJFINANCE.NS","BHARTIARTL.NS","BPCL.NS","BRITANNIA.NS","CIPLA.NS","COALINDIA.NS","DIVISLAB.NS","DRREDDY.NS","EICHERMOT.NS","GAIL.NS","GRASIM.NS","HCLTECH.NS","HDFC.NS","HDFCBANK.NS","HDFCLIFE.NS","HEROMOTOCO.NS","HINDALCO.NS","HINDUNILVR.NS","ICICIBANK.NS","INDUSINDBK.NS","INFY.NS","ITC.NS","JSWSTEEL.NS","KOTAKBANK.NS","LT.NS","M&M.NS","MARUTI.NS","NESTLEIND.NS","NTPC.NS","ONGC.NS","POWERGRID.NS","RELIANCE.NS","SBILIFE.NS","SBIN.NS","SUNPHARMA.NS","TATACONSUM.NS","TATASTEEL.NS","TCS.NS","TECHM.NS","TITN.NS","ULTRACEMCO.NS","UPL.NS","WIPRO.NS")
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = White),
            value = mSelectedText,
            onValueChange =
            {
                onmSelectedTextChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                },
            maxLines = 1,
            label = { Text(text = label1) },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            mCities.forEach { label ->
                DropdownMenuItem(onClick = {
                    onmSelectedTextChange(label)
                    mExpanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}
@Composable
fun CompareGraphhai(list: List<Float>, list2: List<String>, list3: List<Float>) {
    val Min=min(list.min(),list3.min())
    val Max=max(list.max(),list3.max())
    val list1=when {
        list.isEmpty() -> listOf("0", "50", "100", "150", "250")
        else -> {
            listOf(
                "${Min.toInt()}",
                "${(Min + ((Max - Min) / 2)).toInt()}",
                "${(Max).toInt()}"
            )
        }
    }
    var canvasHeight = 0f
    var canvasWidth = 0f
    var columnWidth = 0f
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
                        for (i in 0 until min(list.size,min(list2.size,list3.size))) {
                            drawRoundRect(
                                cornerRadius = CornerRadius(
                                    canvasWidth / 25f,
                                    canvasWidth / 25f
                                ),
                                topLeft = Offset(
                                    x = space,
                                    y = 0f
                                ),
                                color = BackgroundColor,
                                size = Size(
                                    width = (max(
                                        1f,
                                        ((canvasWidth / (list.size * 2)) - 35f)
                                    )),
                                    height = (canvasHeight)
                                )
                            )
                            drawRoundRect(
                                cornerRadius = CornerRadius(
                                    canvasWidth / 25f,
                                    canvasWidth / 25f
                                ),
                                topLeft = Offset(
                                    x = space + max(
                                        1f,
                                        ((canvasWidth / (list.size * 2)) - 30f)
                                    ),
                                    y = 0f
                                ),
                                color = BackgroundColor,
                                size = Size(
                                    width = (max(
                                        1f,
                                        ((canvasWidth / (list.size * 2)) - 35f)
                                    )),
                                    height = (canvasHeight)
                                )
                            )
                            drawRoundRect(
                                cornerRadius = CornerRadius(
                                    canvasWidth / 25f,
                                    canvasWidth / 25f
                                ),
                                topLeft = Offset(
                                    x = space,
                                    y = (canvasHeight - ((canvasHeight / Max) * list[i]))
                                ),
                                color = Teal200,
                                size = Size(
                                    width = (max(
                                        1f,
                                        ((canvasWidth / (list.size * 2)) - 35f)
                                    )),
                                    height = ((canvasHeight / Max) * list[i])
                                )
                            )
                            drawRoundRect(
                                cornerRadius = CornerRadius(
                                    canvasWidth / 25f,
                                    canvasWidth / 25f
                                ),
                                topLeft = Offset(
                                    x = space + max(
                                        1f,
                                        ((canvasWidth / (list.size * 2)) - 30f)
                                    ),
                                    y = (canvasHeight - ((canvasHeight / Max) * list3[i]))
                                ),
                                color = Purple200,
                                size = Size(
                                    width = (max(
                                        1f,
                                        ((canvasWidth / (list.size * 2)) - 35f)
                                    )),
                                    height = ((canvasHeight / Max) * list3[i])
                                )
                            )
                            val rect = Rect(
                                Offset(
                                    x = space,
                                    y = (canvasHeight) + 40f
                                ), size
                            )
                            drawIntoCanvas { canvas ->
                                rotate(
                                    degrees = 90f, Offset(
                                        x = space,
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
fun CompareGraph1hai(list: List<Float>, list2: List<String>, list3: List<Float>) {
    val Min=min(list.min(),list3.min())
    val Max=max(list.max(),list3.max())
    val list1=when {
        list.isEmpty() -> listOf("0", "50", "100", "150", "250")
        else -> {
            listOf(
                "${Min.toInt()}",
                "${(Min + ((Max - Min) / 5)).toInt()}",
                "${(Min + (2 * (Max - Min) / 5)).toInt()}",
                "${(Min + (3 * (Max - Min) / 5)).toInt()}",
                "${(Min + (4 * (Max - Min) / 5)).toInt()}",
                "${(Max).toInt()}",

                )
        }
    }
    var canvasHeight = 0f
    var canvasWidth = 0f
    var columnWidth = 0f
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
                    val animationProgress = remember {
                        Animatable(0f)
                    }
                    LaunchedEffect(key1 = list, block = {
                        animationProgress.animateTo(1f, tween(3000))
                    })
                    Canvas(
                        modifier = Modifier
                            .height(350.dp)
                            .fillMaxWidth()
                    ) {
                        canvasHeight = 300.dp.toPx()
                        canvasWidth = size.width
                        val numberEntries = list.size - 1
                        val weekWidth = canvasWidth / numberEntries
                        var previousBalanceX = 0f
                        var previousBalanceY = canvasHeight
                        var previousBalanceX1 = 0f
                        var previousBalanceY1 = canvasHeight
                        val max = list.maxBy { it }
                        val range = max
                        val heightPxPerAmount = canvasHeight / Max
                        drawLine(
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 0f, y = canvasHeight),
                            color = Color.Black,
                            strokeWidth = 5f
                        )
                        var space = 30f
                        val path = Path()
                        val path2 = Path()
                        path.moveTo(0f, canvasHeight)
                        path2.moveTo(0f, canvasHeight)
                        for (i in 0 until min(list.size,min(list2.size,list3.size))) {
                            val balanceX = i * weekWidth
                            val balanceY = canvasHeight - (list[i]) * heightPxPerAmount
                            val balanceY1 = canvasHeight - (list3[i]) * heightPxPerAmount
                            val controlPoint1 =
                                PointF((balanceX + previousBalanceX) / 2f, previousBalanceY)
                            val controlPoint2 =
                                PointF((balanceX + previousBalanceX) / 2f, balanceY)
                            val controlPoint3 =
                                PointF((balanceX + previousBalanceX) / 2f, previousBalanceY1)
                            val controlPoint4 =
                                PointF((balanceX + previousBalanceX) / 2f, balanceY1)
                            path.cubicTo(
                                controlPoint1.x,
                                controlPoint1.y,
                                controlPoint2.x,
                                controlPoint2.y,
                                balanceX,
                                balanceY
                            )
                            path2.cubicTo(
                                controlPoint3.x,
                                controlPoint3.y,
                                controlPoint4.x,
                                controlPoint4.y,
                                balanceX,
                                balanceY1
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
                                previousBalanceY1 = balanceY1
                                space += (java.lang.Float.max(1f, ((canvasWidth / list.size))))
                            }
                        }
                        drawLine(
                            start = Offset(x = 0f, y = canvasHeight),
                            end = Offset(x = canvasWidth, y = canvasHeight),
                            color = Color.Black,
                            strokeWidth = 5f
                        )
                        clipRect(right = canvasWidth * animationProgress.value) {
                            drawPath(path, Teal200, style = Stroke(2.dp.toPx()))
                            drawPath(path2, Purple200, style = Stroke(2.dp.toPx()))
                        }
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun PreviewedHaiYeTho(){
    StockMarketPricePredictor20Theme {
        var name:String by rememberSaveable {
            mutableStateOf("")
        }
        MyContent(
            "YOHO",
            name,
            {name=it}
        )
    }
}