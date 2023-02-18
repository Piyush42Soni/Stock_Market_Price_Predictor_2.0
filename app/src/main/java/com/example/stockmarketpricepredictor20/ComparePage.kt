package com.example.stockmarketpricepredictor20

import android.graphics.Paint
import android.graphics.PointF
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmarketpricepredictor20.ui.theme.*
import java.lang.Float.max
import java.util.*

class ComparePageViewModel: ViewModel(){
    private val _name= MutableLiveData<List<Float>>()
    val name=_name
    private val _name1= MutableLiveData<List<String>>()
    val name1=_name1

    private val _name2= MutableLiveData<List<Float>>()
    val name2=_name2
    fun onNameChange(newList: List<Float>){
        _name.value=newList
    }
    fun onName1Change(newList: List<String>){
        _name1.value=newList
    }
    fun onName2Change(newList: List<Float>){
        _name2.value=newList
    }
}
@Composable
fun ComparePage(comparePageViewModel: ComparePageViewModel) {
    val list= listOf<String>("1D","1W","1M","1Y","Max")
    var state by remember {
        mutableStateOf(0)
    }
    var list3:List<String> =when(state){
        0-> listOf("9:00","11:00","13:00","15:00","17:00")
        1->listOf("Mon","Tue","Wed","Thu","Fri")
        2->listOf("1","2","3","4","5","6","7","8","9","10",
            "11","12","13","14","15","16","17","18","19","20"
            ,"21","22","23","24","25","26","27","28","29","30")
        3->listOf("Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec")
        else->listOf("2018","2019","2020","2021","2022")
    }
    var list2:MutableList<Float> = mutableListOf<Float>()
    var list4:MutableList<Float> = mutableListOf<Float>()
    when(state){
        0->{
            for(i in 1..5){
                val rd = Random() // creating Random object
                list2.add((rd.nextFloat()*250f))
                list4.add((rd.nextFloat()*250f))
            }
        }
        1->{
            for(i in 1..5){
                val rd = Random() // creating Random object
                list2.add((rd.nextFloat()*250f))
                list4.add((rd.nextFloat()*250f))
            }
        }
        2->{
            for(i in 1..30){
                val rd = Random() // creating Random object
                list2.add((rd.nextFloat()*250f))
                list4.add((rd.nextFloat()*250f))
            }
        }
        3->{
            for(i in 1..12){
                val rd = Random() // creating Random object
                list2.add((rd.nextFloat()*250f))
                list4.add((rd.nextFloat()*250f))
            }
        }
        else->{
            for(i in 1..5){
                val rd = Random() // creating Random object
                list2.add((rd.nextFloat()*250f))
                list4.add((rd.nextFloat()*250f))
            }
        }
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(BackgroundColor)
        .verticalScroll(rememberScrollState())) {
        segmentedButton(list = list, state = state, onStateChange = { state = it })
        Spacer(modifier = Modifier.size(15.dp))
        val switchState = remember {
            mutableStateOf(false)
        }
        var mSelectedText by remember { mutableStateOf("") }
        Spacer(modifier = Modifier.size(15.dp))
        var mSelectedText2 by remember { mutableStateOf("") }
        MyContent(label1 = "Company1", mSelectedText, { mSelectedText = it })
        MyContent(label1 = "Company2", mSelectedText2, { mSelectedText2 = it })
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
        comparePageViewModel.onNameChange(list2)
        comparePageViewModel.onName1Change(list3)
        comparePageViewModel.onName2Change(list4)
        Card(
            Modifier
                .padding(5.dp),
            backgroundColor = CardColor,
            shape = MaterialTheme.shapes.large
        ) {
            if (switchState.value) {
                comparePageViewModel.name.value?.let {
                    comparePageViewModel.name1.value?.let { it1 ->
                        comparePageViewModel.name2.value?.let { it2 ->
                            CompareGraph1hai(it, it1, it2)
                        }
                    }
                }
            } else {
                comparePageViewModel.name.value?.let {
                    comparePageViewModel.name1.value?.let { it1 ->
                        comparePageViewModel.name2.value?.let { it2 ->
                            CompareGraphhai(it, it1, it2)
                        }
                    }
                }
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
                        .padding(30.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val colorList=listOf<Color>(Purple200,Teal200)
                    val list6= mutableListOf(100f,300f)
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
                                        element(name = mSelectedText, Pic = R.drawable.m, Purple200)
                                    }
                                    "C2" -> {
                                        element(name = mSelectedText, Pic = R.drawable.a, Purple200)
                                    }
                                    "C3" -> {
                                        element(name = mSelectedText, Pic = R.drawable.b, Purple200)
                                    }
                                    else -> {
                                        element(name = mSelectedText, Pic = R.drawable.c, Purple200)
                                    }
                                }
                            }
                            item() {
                                when ((mSelectedText2)) {
                                    "C1" -> {
                                        element(name = mSelectedText2, Pic = R.drawable.m, Teal200)
                                    }
                                    "C2" -> {
                                        element(name = mSelectedText2, Pic = R.drawable.a, Teal200)
                                    }
                                    "C3" -> {
                                        element(name = mSelectedText2, Pic = R.drawable.b, Teal200)
                                    }
                                    else -> {
                                        element(name = mSelectedText2, Pic = R.drawable.c, Teal200)
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
fun MyContent(label1:String,mSelectedText:String,onmSelectedTextChange:(String)->Unit){
    var mExpanded by remember { mutableStateOf(false) }
    val mCities = listOf("C1", "C2", "C3", "C4")

    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            colors= TextFieldDefaults.outlinedTextFieldColors(textColor= White),
            value = mSelectedText,
            onValueChange = {onmSelectedTextChange(it)},
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                },
            maxLines = 1,
            label = {Text(text=label1)},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
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
fun CompareGraphhai(list: List<Float>,list2:List<String>,list3:List<Float>){
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
                                    width = (max(1f, ((canvasWidth / (list.size*2)) - 35f))),
                                    height = (canvasHeight)
                                )
                            )
                            drawRoundRect(
                                cornerRadius= CornerRadius(canvasWidth/25f,canvasWidth/25f),
                                topLeft = Offset(
                                    x = space + max(1f, ((canvasWidth / (list.size*2)) - 30f)),
                                    y = 0f
                                ),
                                color = BackgroundColor,
                                size = Size(
                                    width = (max(1f, ((canvasWidth / (list.size*2)) - 35f))),
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
                                    width = (max(1f, ((canvasWidth / (list.size*2)) - 35f))),
                                    height = ((canvasHeight / 250f) * list[i])
                                )
                            )
                            drawRoundRect(
                                cornerRadius= CornerRadius(canvasWidth/25f,canvasWidth/25f),
                                topLeft = Offset(
                                    x = space + max(1f, ((canvasWidth / (list.size*2)) - 30f)),
                                    y = (canvasHeight - ((canvasHeight / 250f) * list3[i]))
                                ),
                                color = Purple200,
                                size = Size(
                                    width = (max(1f, ((canvasWidth / (list.size*2)) - 35f))),
                                    height = ((canvasHeight / 250f) * list3[i])
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
fun CompareGraph1hai(list: List<Float>,list2:List<String>,list3: List<Float>) {
    val list1 = listOf("$250", "$200", "$150", "$100", "$50", "$0")
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
                        val heightPxPerAmount = canvasHeight / 250f
                        drawLine(
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 0f, y = canvasHeight),
                            color = Color.Black,
                            strokeWidth = 5f
                        )
                        var space = 30f
                        val path = Path()
                        val path2=Path()
                        path.moveTo(0f,canvasHeight)
                        path2.moveTo(0f,canvasHeight)
                        for (i in 0..list.size - 1) {
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
        val comparePageViewModel:ComparePageViewModel= viewModel()
        ComparePage(comparePageViewModel)
    }
}