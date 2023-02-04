package com.example.stockmarketpricepredictor20


import android.graphics.Paint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmarketpricepredictor20.ui.theme.*
import java.lang.Float.max
import java.util.*
import java.util.concurrent.ThreadLocalRandom

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
    val list= listOf<String>("1D","1W","1M","1Y","Max")
    val CompanyName= listOf<String>("C1", "C2", "C3", "C4")
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
            else->listOf("2018","2019","2020","2021")
        }

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
        Card(
            Modifier
                .padding(5.dp),
            backgroundColor = CardColor,
            shape = MaterialTheme.shapes.large
        ) {
            statsViewModel.name.value?.let { statsViewModel.name1.value?.let { it1 -> Graphhai(list = it,list2= it1) } }
        }
        Card(
            Modifier
                .height(700.dp)
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
                        AdjustText(list[it])
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
                        AdjustText(list[it])
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
                        AdjustText(list[it])
                    }
                }
            }
        }
    }
}

@Composable
fun AdjustText(text:String){
    val textStyleBody1 = MaterialTheme.typography.button
    var textStyle by remember { mutableStateOf(textStyleBody1) }
    var readyToDraw by remember { mutableStateOf(false) }
    Text(
        text = text,
        style = textStyle,
        maxLines = 1,
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
                                color = Purple200,
                                size = Size(
                                    width = (max(1f, ((canvasWidth / list.size) - 60f))),
                                    height = ((canvasHeight / 250f) * list[i])
                                )
                            )
                            space += (max(1f, ((canvasWidth / list.size))))
                        }
                        drawLine(
                            start = Offset(x = 0f, y = canvasHeight),
                            end = Offset(x = canvasWidth, y = canvasHeight),
                            color = Color.Black,
                            strokeWidth = 5f
                        )
                        var x=1f
                        for(i in list2.indices) {
                            drawIntoCanvas { canvas ->
                                canvas.nativeCanvas.drawText(
                                    list2[i],
                                    x * canvasWidth / 8.7f,
                                    size.height - (size.height / 13),
                                    Paint().apply {
                                        textSize = 40f
                                        color = android.graphics.Color.WHITE
                                        textAlign = Paint.Align.CENTER
                                    }
                                )
                            }
                            x+=((5f*1.7f)/list2.size)
                        }
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
        val statsViewModel:StatsViewModel=viewModel()
        StatsPage(statsViewModel)
    }
}