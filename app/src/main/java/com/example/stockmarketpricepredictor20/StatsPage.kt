package com.example.stockmarketpricepredictor20


import android.graphics.Paint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmarketpricepredictor20.ui.theme.*
import java.lang.Float.max

@Composable
fun StatsPage() {
    val list= listOf<String>("1D","1W","1M","1Y","Max")
    var state by remember {
        mutableStateOf(0)
    }
    Column(modifier = Modifier
        .background(BackgroundColor)
        .fillMaxSize()
        .padding(15.dp).verticalScroll(rememberScrollState())) {
        segmentedButton(list = list, state =state , onStateChange = {state=it})
        val list2= listOf<Float>(200f,120f,130f,210f,150f)
        Spacer(modifier = Modifier.size(15.dp))
        Card(
            Modifier
                .padding(5.dp)
                .height(400.dp),
            backgroundColor = CardColor,
            shape = MaterialTheme.shapes.large
        ) {
            Graphhai(list = list2)
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
fun Graphhai(list: List<Float>){
    val list1=listOf("$250","$200","$150","$100","$50","$0")
    val list2=listOf("Mon","Tue","Wed","Thu","Fri")
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
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "Mon",
                                canvasWidth/8.7f,
                                size.height-(size.height/13),
                                Paint().apply {
                                    textSize = 40f
                                    color = android.graphics.Color.WHITE
                                    textAlign = Paint.Align.CENTER
                                }
                            )
                        }
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "Tue",
                                2.7f*canvasWidth/8.7f,
                                size.height-(size.height/13),
                                Paint().apply {
                                    textSize = 40f
                                    color = android.graphics.Color.WHITE
                                    textAlign = Paint.Align.CENTER
                                }
                            )
                        }
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "Wed",
                                4.4f*canvasWidth/8.7f,
                                size.height-(size.height/13),
                                Paint().apply {
                                    textSize = 40f
                                    color = android.graphics.Color.WHITE
                                    textAlign = Paint.Align.CENTER
                                }
                            )
                        }
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "Thu",
                                6.1f*canvasWidth/8.7f,
                                size.height-(size.height/13),
                                Paint().apply {
                                    textSize = 40f
                                    color = android.graphics.Color.WHITE
                                    textAlign = Paint.Align.CENTER
                                }
                            )
                        }
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "Fri",
                                7.8f*canvasWidth/8.7f,
                                size.height-(size.height/13),
                                Paint().apply {
                                    textSize = 40f
                                    color = android.graphics.Color.WHITE
                                    textAlign = Paint.Align.CENTER
                                }
                            )
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
        StatsPage()
    }
}