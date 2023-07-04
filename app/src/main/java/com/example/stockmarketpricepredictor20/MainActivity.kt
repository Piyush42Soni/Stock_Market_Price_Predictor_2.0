package com.example.stockmarketpricepredictor20

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.stockmarketpricepredictor20.auth.GoogleAuthUiClient
import com.example.stockmarketpricepredictor20.auth.SignInViewModel
import com.example.stockmarketpricepredictor20.ui.theme.StockMarketPricePredictor20Theme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController: NavHostController = rememberNavController()
            val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
            val topBarState = rememberSaveable { (mutableStateOf(false)) }
            val currentActive=rememberSaveable { (mutableStateOf(0)) }
            val backStackEntry by navController.currentBackStackEntryAsState()
            val statsViewModel: StatsViewModel = viewModel()
            val homeViewModel: HomeViewModel = viewModel()
            val currentScreen = StockScreen.valueOf(
                backStackEntry?.destination?.route ?: StockScreen.Login.name
            )
            StockMarketPricePredictor20Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            StockTopAppBar(
                                currentScreen = currentScreen,
                                canNavigateBack = topBarState.value,
                                navigateUp = { navController.navigateUp() }
                            )
                        },
                        bottomBar ={
//            if(bottomBarState.value) {
//                StockBottomAppBar(
//                    HomeNavigate = {
//                        navController.navigate(StockScreen.Home.name)
//                    },
//                    StatsNavigate = {
//                        navController.navigate(StockScreen.Stats.name)
//                    },
//                    CompareNavigate = {
//                        navController.navigate(StockScreen.Compare.name)
//                    },
//                    active = currentActive.value
//                )
//            }
                        }
                    ) { innerPadding ->
                        Column(modifier = Modifier.fillMaxSize()) {
                            if(currentActive.value!=0) {
                                TabRow(selectedTabIndex = currentActive.value - 1) {
                                    Tab(
                                        text = { Text("Home") },
                                        selected = currentActive.value == 1,
                                        onClick = {
                                            currentActive.value = 1
                                            navController.navigate(StockScreen.Home.name)
                                        }
                                    )
                                    Tab(
                                        text = { Text("Compare") },
                                        selected = currentActive.value == 2,
                                        onClick = {
                                            currentActive.value = 2
                                            navController.navigate(StockScreen.Compare.name)
                                        }
                                    )
                                }
                            }
                            NavHost(
                                navController = navController,
                                startDestination = StockScreen.Login.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.9F)
                                    .padding(innerPadding)
                            ) {
                                composable(route = StockScreen.Home.name) {
                                    HomePage(homeViewModel, onPageChange = {it,it1,it2->
                                        statsViewModel.currentData=it
                                        statsViewModel.companyName=it1
                                        statsViewModel.gworth=it2
                                        statsViewModel.getSymbol(statsViewModel.companyName)
                                        statsViewModel.getStockShareHoldingPatternData()
                                        statsViewModel.getStockIndexTrendData()
                                        navController.navigate(StockScreen.Stats.name)
                                    })
                                    bottomBarState.value=true
                                    topBarState.value=true
                                    currentActive.value=1
                                }
                                composable(route = StockScreen.Compare.name) {
                                    val comparePageViewModel: StatsViewModel = viewModel()
                                    ComparePage(comparePageViewModel,statsViewModel)
                                    bottomBarState.value=true
                                    topBarState.value=true
                                    currentActive.value=2
                                }
                                composable(route = StockScreen.Login.name) {
                                    val viewModel = viewModel<SignInViewModel>()
                                    val state by viewModel.state.collectAsState()

                                    LaunchedEffect(key1 = Unit) {
                                        if(googleAuthUiClient.getSignedInUser() != null) {
                                            navController.navigate(StockScreen.Home.name)
                                        }
                                    }

                                    val launcher = rememberLauncherForActivityResult(
                                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                                        onResult = { result ->
                                            if(result.resultCode == RESULT_OK) {
                                                lifecycleScope.launch {
                                                    val signInResult = googleAuthUiClient.signInWithIntent(
                                                        intent = result.data ?: return@launch
                                                    )
                                                    viewModel.onSignInResult(signInResult)
                                                }
                                            }
                                        }
                                    )

                                    LaunchedEffect(key1 = state.isSignInSuccessful) {
                                        if(state.isSignInSuccessful) {
                                            Toast.makeText(
                                                applicationContext,
                                                "Sign in successful",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            navController.navigate(StockScreen.Home.name)
                                            viewModel.resetState()
                                        }
                                    }

                                    LoginPage(
                                        state = state,
                                        onSignInClick = {
                                            lifecycleScope.launch {
                                                val signInIntentSender = googleAuthUiClient.signIn()
                                                launcher.launch(
                                                    IntentSenderRequest.Builder(
                                                        signInIntentSender ?: return@launch
                                                    ).build()
                                                )
                                            }
                                        }
                                    )
                                }
                                composable(route = StockScreen.Stats.name) {
                                    StatsPage(statsViewModel)
                                    bottomBarState.value=true
                                    topBarState.value=true
                                    currentActive.value=1
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
fun StockTopAppBar(
    currentScreen: StockScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { AdjustText(text=(stringResource(currentScreen.title)), textStyleBody1 = MaterialTheme.typography.h6, color = Color.White) },
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
    active:Int
) {
    BottomAppBar(modifier = modifier,){
        if(active==1) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {

                IconButton(onClick = { HomeNavigate() }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Filled.Home, contentDescription = "Localized description")
                        Text(text = "Home")
                    }
                }
            }
        }
        else{
            IconButton(onClick = { HomeNavigate() }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Filled.Home, contentDescription = "Localized description")
                    Text(text = "Home")
                }
            }
        }
        Spacer(Modifier.weight(0.5f, true))
        if(active==2) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                IconButton(onClick = { CompareNavigate() }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Filled.Compare, contentDescription = "Localized description")
                        Text(text = "Compare")
                    }
                }
            }
        }
        else{
            IconButton(onClick = { CompareNavigate() }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Filled.Compare, contentDescription = "Localized description")
                    Text(text = "Compare")
                }
            }
        }
        Spacer(Modifier.weight(0.5f, true))
        if(active==3) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                IconButton(onClick = { StatsNavigate() }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Filled.QueryStats, contentDescription = "Localized description")
                        Text(text = "Stats")
                    }
                }
            }
        }
        else{
            IconButton(onClick = { StatsNavigate() }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Filled.QueryStats, contentDescription = "Localized description")
                    Text(text = "Stats")
                }
            }
        }
    }
}