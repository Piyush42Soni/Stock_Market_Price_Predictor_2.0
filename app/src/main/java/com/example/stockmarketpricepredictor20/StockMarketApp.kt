package com.example.stockmarketpricepredictor20

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

enum class StockScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    Compare(title = R.string.Compare),
    Login(title = R.string.Login),
    Stats(title = R.string.Login)
}
@Composable
fun StockTopAppBar(
    currentScreen: StockScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            }
        }
    )
}
@Composable
fun StockBottomAppBar(
    modifier: Modifier = Modifier,
    HomeNavigate:() -> Unit,
    CompareNavigate:() -> Unit,
    StatsNavigate:() -> Unit,
) {
    BottomAppBar(modifier = modifier,){
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {

            IconButton(onClick = { HomeNavigate() }) {
                Column(horizontalAlignment =Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Icon(Icons.Filled.Home, contentDescription = "Localized description")
                    Text(text = "Home")
                }
            }
        }
        Spacer(Modifier.weight(0.5f, true))
        IconButton(onClick = { CompareNavigate() }) {
            Column(horizontalAlignment =Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(Icons.Filled.Compare, contentDescription = "Localized description")
                Text(text = "Compare")
            }
        }
        Spacer(Modifier.weight(0.5f, true))
        IconButton(onClick = { StatsNavigate() }) {
            Column(horizontalAlignment =Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(Icons.Filled.QueryStats, contentDescription = "Localized description")
                Text(text = "Stats")
            }
        }
    }
}
@Composable
fun StockMarketApp(
    navController: NavHostController = rememberNavController()
) {
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    val topBarState = rememberSaveable { (mutableStateOf(false)) }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = StockScreen.valueOf(
        backStackEntry?.destination?.route ?: StockScreen.Login.name
    )
    Scaffold(
        topBar = {
            StockTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = topBarState.value,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar ={
            if(bottomBarState.value) {
                StockBottomAppBar(
                    HomeNavigate = {
                        navController.navigate(StockScreen.Home.name)
                    },
                    StatsNavigate = {
                        navController.navigate(StockScreen.Stats.name)
                    },
                    CompareNavigate = {
                        navController.navigate(StockScreen.Compare.name)
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = StockScreen.Login.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9F)
                    .padding(innerPadding)
            ) {
                composable(route = StockScreen.Home.name) {
                    HomePage()
                    bottomBarState.value=true
                    topBarState.value=true
                }
                composable(route = StockScreen.Compare.name) {
                    val comparePageViewModel: ComparePageViewModel = viewModel()
                    ComparePage(comparePageViewModel)
                    bottomBarState.value=true
                    topBarState.value=true
                }
                composable(route = StockScreen.Login.name) {
                    LoginPage(onSendButtonClicked = { navController.navigate(StockScreen.Home.name) })
                    bottomBarState.value=false
                    topBarState.value=false
                }
                composable(route = StockScreen.Stats.name) {
                    val statsViewModel: StatsViewModel = viewModel()
                    StatsPage(statsViewModel)
                    bottomBarState.value=true
                    topBarState.value=true
                }
            }
        }
    }
}
private fun shareOrder(context: Context, subject: String, Stats: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, Stats)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_stock_prediction)
        )
    )
}

@Preview
@Composable
fun AppPreviewed(){
    StockMarketApp()
}