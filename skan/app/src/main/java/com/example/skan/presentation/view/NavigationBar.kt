package com.example.skan.presentation.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import com.example.skan.R
import com.example.skan.presentation.viewModel.NavigationBarViewModel

data class BottomNavigationItem(
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
    val title: String,
)

    @Preview
    @Composable
    fun NavigationBar() {
        var viewModel: NavigationBarViewModel = NavigationBarViewModel()
        Box(
            Modifier
                .fillMaxSize()
        ) {
            ViewContainer(Modifier, viewModel)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ViewContainer(modifier: Modifier, viewModel: NavigationBarViewModel) {
        val currentScreen: Int by viewModel.currentScreen.observeAsState(initial = 0)
        Scaffold(
            content = {
                when (currentScreen) {
                    0 -> HomeScreen()
                    1 -> ResourceScreen()
                    2 -> CameraScreen()
                    3 -> SearchScreen()
                    4 -> ProfileScreen()
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
    fun FAB(modifier: Modifier) {
        val context = LocalContext.current
        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = {
                Toast.makeText(context, "Testing", Toast.LENGTH_SHORT).show()
            }) {
            Text(text = "Camara")
        }
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
                selectedIcon = painterResource(id = R.drawable.camera),
                unselectedIcon = painterResource(id = R.drawable.camera2),
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
                            modifier = Modifier.size(48.dp)
                        )
                    }
                )
            }
        }
    }
