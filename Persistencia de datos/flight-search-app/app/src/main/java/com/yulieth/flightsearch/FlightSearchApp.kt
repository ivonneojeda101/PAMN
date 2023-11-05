package com.yulieth.flightsearch


import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yulieth.flightsearch.ui.screens.flight_screen.FlightScreen
import com.yulieth.flightsearch.ui.screens.flight_screen.FlightScreenDestination
import com.yulieth.flightsearch.ui.screens.search.SearchDestination
import com.yulieth.flightsearch.ui.screens.search.SearchScreen
import androidx.compose.ui.res.stringResource

@Composable
fun FlightSearchApp() {
    val navController = rememberNavController()
    val mainTitle = stringResource(R.string.app_name)
    var topAppBarTitle by remember { mutableStateOf(mainTitle) }
    var callBackN by remember { mutableStateOf(false)}
    val onBackHandler = {
        topAppBarTitle = mainTitle
        callBackN = false
        navController.navigateUp()
        navController.navigateUp()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = topAppBarTitle,
                joker = callBackN.toString(),
                canNavigateBack = navController.previousBackStackEntry != null,
                onBackClick = { onBackHandler() }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = SearchDestination.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = SearchDestination.route) {
                SearchScreen(
                    modifier = Modifier,
                    onSelectCode = {
                        navController.navigate("${FlightScreenDestination.route}/${it}")
                    }
                )
            }
            composable(
                route = FlightScreenDestination.routeWithArgs,
                arguments = listOf(navArgument(FlightScreenDestination.codeArg) {
                    type = NavType.StringType
                }
                )
            ) {
                callBackN = true
                FlightScreen(onBack = { onBackHandler() })
                callBackN = false
            }
        }
    }
}

@Composable
fun TopAppBar(
    title: String,
    joker: String,
    canNavigateBack: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (canNavigateBack) {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(
                            R.string.back
                        )
                    )
                }
            },
            modifier = modifier
        )
    } else {
        TopAppBar(
            title = { Text(title) },
            modifier = modifier
        )
    }
}