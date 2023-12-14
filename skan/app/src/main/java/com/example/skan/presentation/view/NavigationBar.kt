package com.example.skan.presentation.view

import android.Manifest.permission.INTERNET
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.navigation.compose.rememberNavController
import com.example.skan.R
import com.example.skan.presentation.viewModel.NavigationBarViewModel

data class BottomNavigationItem(
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
    val title: String,
)
    @Composable
    fun NavigationBar() {
        val applicationContext = LocalContext.current
        val result = ContextCompat.checkSelfPermission(applicationContext, INTERNET)
        var viewModel: NavigationBarViewModel = NavigationBarViewModel()
        var PERMISSION_REQUEST_CODE = 200;
        val navController = rememberNavController()
        if (result == PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                applicationContext as Activity,
                arrayOf<String>(INTERNET),
                PERMISSION_REQUEST_CODE
            )
        }
        Box(
            Modifier
                .fillMaxSize()
        ) {
            ViewContainer(modifier = Modifier, viewModel, applicationContext)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ViewContainer(
        modifier: Modifier = Modifier,
        viewModel: NavigationBarViewModel,
        context: Context
    ) {
        val currentScreen: Int by viewModel.currentScreen.observeAsState(initial = 0)
        Scaffold(
            content = {
                when (currentScreen) {
                    0 -> HomeScreen()
                    1 -> ResourceScreen()
                    2 -> CameraScreen()
                    3 -> SearchScreen()
                    4 -> { if (viewModel.getProfile(context)){ProfileScreen()}
                        else{
                            val intent = Intent(context, Registration::class.java)
                            context.startActivity(intent)
                        }}
                    else -> { HomeScreen()}
                }
                      },
            bottomBar = { NavigationBarItems(Modifier, currentScreen)
                            {viewModel.switchToScreen(it) }
                        },
            floatingActionButtonPosition = FabPosition.Center,
        )
    }
    @Composable
    fun NavigationBarItems(modifier: Modifier, currentScreen: Int, switchToScreen: (Int) -> Unit) {

        val items = listOf(
            BottomNavigationItem(
                selectedIcon = painterResource(id = R.drawable.home),
                unselectedIcon = painterResource(id = R.drawable.home),
                title = "Home",
            ),
            BottomNavigationItem(
                selectedIcon = painterResource(id = R.drawable.news),
                unselectedIcon = painterResource(id = R.drawable.news),
                title = "Recursos",
            ),
            BottomNavigationItem(
                selectedIcon = painterResource(id = R.drawable.scanner),
                unselectedIcon = painterResource(id = R.drawable.scanner),
                title = "Cámara",
            ),
            BottomNavigationItem(
                selectedIcon = painterResource(id = R.drawable.search),
                unselectedIcon = painterResource(id = R.drawable.search),
                title = "Búsqueda",
            ),
            BottomNavigationItem(
                selectedIcon = painterResource(id = R.drawable.profile),
                unselectedIcon = painterResource(id = R.drawable.profile),
                title = "Perfil",
            ),
        )

        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = currentScreen == index,
                    onClick = { switchToScreen(index) },
                    alwaysShowLabel = false,
                    icon = {
                        Icon(
                            painter = item.selectedIcon as Painter,
                            contentDescription = item.title,
                            modifier = Modifier.size(48.dp),
                            tint = Color.Unspecified
                        )
                    }
                )
            }
        }
    }
