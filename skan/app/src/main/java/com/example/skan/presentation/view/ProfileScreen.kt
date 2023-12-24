package com.example.skan.presentation.view

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skan.R
import com.example.skan.domain.entities.User
import com.example.skan.presentation.viewModel.ProfileViewModel


@Composable
fun ProfileScreen() {
    val viewModel: ProfileViewModel = ProfileViewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "profile") {
        composable("profile") { MainContainer(viewModel, navController) }
        composable("favorites") { FavoriteScreen(navController)}
        composable("reviews") { ReviewScreen(navController)}
    }
}

@Composable
fun MainContainer(viewModel: ProfileViewModel, navController: NavHostController) {
    val applicationContext = LocalContext.current
    val findUser: Boolean by viewModel.findUser.observeAsState(initial = true)
    val user: User by viewModel.user.observeAsState(initial = User())

    viewModel.getData(applicationContext)
    if (!findUser) {
        val intent = Intent(applicationContext, Registration::class.java)
        applicationContext.startActivity(intent)
    }
    else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF6EC7D7), Color(0xFFAEE2FA), Color(0xFFEAF7F9)),
                        startY = 0f,
                        endY = 2000f
                    )
                )
        ) {
            Row(){
                mainHeader()
            }

            Box(
                modifier = Modifier
                    .padding(top = 120.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White.copy(alpha = 0.3f))
            ) {
                Text(
                    text = "Información Personal",
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center),
                    style = TextStyle(fontSize = 25.sp, fontFamily = FontFamily(Font(R.font.literata_bold),))
                )
            }

            Row(){
                Components(user, viewModel, applicationContext, navController)
            }
        }
    }
}

@Composable
fun Components(
    user: User,
    viewModel: ProfileViewModel,
    context: Context,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(220.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White.copy(alpha = 0.5f), shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .padding(horizontal = 55.dp, vertical = 18.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.5f))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profilepicture),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(0.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // Profile details
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(text = user.name, color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = user.email, color = Color.Black, modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    text = user.status,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                    onClick = { /* Edit account action */ })
                {
                    Text(text = "Editar cuenta")
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
        ) {
            LazyColumn(
                modifier = Modifier.padding(8.dp)
            ) {
                val items = listOf(
                    R.drawable.favorite,
                    R.drawable.review,
                    R.drawable.settings,
                    R.drawable.logout
                )
                val menuItems = listOf("Mis Favoritos", "Mis Reseñas", "Configuración", "Finalizar sesión")
                itemsIndexed(menuItems) { index, item ->
                    val isGrayBackground = index % 2 == 1
                    val backgroundColor = if (isGrayBackground) Color.White else Color.LightGray

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(backgroundColor)
                            .clickable {
                                when (index) {
                                    0 -> navController.navigate("favorites")
                                    1 -> navController.navigate("reviews")
                                    3 -> viewModel.logout(context)
                                    else -> {}
                                }
                            }
                    ) {
                        Icon(
                            painter = painterResource(id = items[index]) as Painter,
                            contentDescription = "Favorite Icon",
                            modifier = Modifier
                                .size(50.dp)
                                .padding(start = 16.dp),
                            tint = Color.Unspecified,
                        )
                        Text(text = "   $item" , modifier = Modifier.weight(1f))
                        Text(text = ">", modifier = Modifier.padding(end = 16.dp))
                    }

                    if (index < menuItems.size - 1) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.LightGray)
                        )
                    }
                }
            }
        }
    }
}