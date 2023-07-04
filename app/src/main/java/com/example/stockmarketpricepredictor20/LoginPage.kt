package com.example.stockmarketpricepredictor20


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmarketpricepredictor20.auth.SignInState
import com.example.stockmarketpricepredictor20.ui.theme.BackgroundColor
import com.example.stockmarketpricepredictor20.ui.theme.buttonTextColor

@Composable
fun LoginPage(state: SignInState,
              onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundColor)
        .padding(top = 45.dp),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector= ImageVector.vectorResource(id = R.drawable.datausage), contentDescription = "",Modifier.size(160.dp),tint=Color.White)
        Spacer(modifier = Modifier.size(50.dp))
        var username:String by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = username,
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth(),
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            colors=TextFieldDefaults.textFieldColors(textColor = Color.White, unfocusedIndicatorColor = Color.White, unfocusedLabelColor = Color.White),
            trailingIcon={ IconButton(onClick = {username=""}){Icon(imageVector= Icons.Default.Cancel,contentDescription = null) }}
        )
        Spacer(modifier = Modifier.size(20.dp))
        var password:String by remember {
            mutableStateOf("")
        }
        var showPassword:Boolean by remember {
            mutableStateOf(false)
        }
        Column(modifier=Modifier.padding(start = 15.dp,end=15.dp),horizontalAlignment = Alignment.Start) {
            OutlinedTextField(
                value = password,
                modifier =Modifier
                    .fillMaxWidth(),
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    unfocusedLabelColor = Color.White,
                ),
                shape = MaterialTheme.shapes.medium,
                trailingIcon={ IconButton(onClick = {password=""}){Icon(imageVector= Icons.Default.Cancel,contentDescription = null) }}
            )
            Row(modifier = Modifier.padding(10.dp)) {
                Checkbox(
                    checked = showPassword,
                    onCheckedChange = { showPassword = !showPassword },
                    colors = CheckboxDefaults.colors(uncheckedColor = Color.White, checkedColor = MaterialTheme.colors.primary)
                )
                Text(modifier = Modifier.padding(top=15.dp),text="Show Password", color = Color.White)
            }
        }
        Spacer(modifier = Modifier.size(45.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal=55.dp),
            onClick = {
//                if(username=="YOHO" && password=="YOHO") {
                    onSignInClick()
//                }
            }
            , shape = MaterialTheme.shapes.small
        ) {
            Text(text="Login",fontSize=25.sp,style=MaterialTheme.typography.button, color = buttonTextColor)
        }
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal=55.dp,vertical=10.dp),
            onClick = { /*TODO*/ },
            shape = MaterialTheme.shapes.small,
        ) {
            Text(text="Register",fontSize=25.sp,style=MaterialTheme.typography.button, color = buttonTextColor)
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun Previewed() {
//    StockMarketPricePredictor20Theme {
//        LoginPage(onSendButtonClicked = {})
//    }
//}