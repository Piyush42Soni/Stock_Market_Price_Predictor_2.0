package com.example.stockmarketpricepredictor20

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmarketpricepredictor20.ui.theme.BackgroundColor
import com.example.stockmarketpricepredictor20.ui.theme.CardColor
import com.example.stockmarketpricepredictor20.ui.theme.StockMarketPricePredictor20Theme
import java.util.*

class ComparePageViewModel: ViewModel(){
    private val _name= MutableLiveData<List<Float>>()
    val name=_name
    private val _name1= MutableLiveData<List<String>>()
    val name1=_name1
    fun onNameChange(newList: List<Float>){
        _name.value=newList
    }
    fun onName1Change(newList: List<String>){
        _name1.value=newList
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
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(BackgroundColor)) {
        segmentedButton(list = list, state = state, onStateChange = {state=it})
        Spacer(modifier = Modifier.size(15.dp))
        val switchState = remember {
            mutableStateOf(false)
        }
        var mSelectedText by remember { mutableStateOf("") }
        Spacer(modifier = Modifier.size(15.dp))
        var mSelectedText2 by remember { mutableStateOf("") }
        MyContent(label1 = "Company1",mSelectedText,{mSelectedText=it})
        MyContent(label1 = "Company2",mSelectedText2,{mSelectedText2=it})
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
            Icon(tint = Color.White, imageVector = Icons.Filled.DataExploration, contentDescription = null)
            Switch(
                checked = switchState.value,
                onCheckedChange = { switchState.value = it },
                modifier = Modifier.size(50.dp),
                colors = SwitchDefaults.colors(uncheckedThumbColor = Color.White)
            )
        }
        comparePageViewModel.onNameChange(list2)
        comparePageViewModel.onName1Change(list3)
        Card(
            Modifier
                .padding(5.dp),
            backgroundColor = CardColor,
            shape = MaterialTheme.shapes.large
        ) {
            if (switchState.value) {
                comparePageViewModel.name.value?.let {
                    comparePageViewModel.name1.value?.let { it1 ->
                        Graph1hai(
                            list = it,
                            list2 = it1
                        )
                    }
                }
            } else {
                comparePageViewModel.name.value?.let {
                    comparePageViewModel.name1.value?.let { it1 ->
                        Graphhai(
                            list = it,
                            list2 = it1
                        )
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

@Preview
@Composable
fun PreviewedHaiYeTho(){
    StockMarketPricePredictor20Theme {
        val comparePageViewModel:ComparePageViewModel= viewModel()
        ComparePage(comparePageViewModel)
    }
}