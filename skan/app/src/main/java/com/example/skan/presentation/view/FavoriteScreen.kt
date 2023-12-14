package com.example.skan.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.skan.R
import com.example.skan.domain.entities.Favorite
import com.example.skan.presentation.viewModel.FavoriteViewModel

@Composable
fun FavoriteScreen(navController: NavHostController) {
    val applicationContext = LocalContext.current
    val viewModel: FavoriteViewModel = FavoriteViewModel()
    val listFavorites: List<Favorite> by viewModel.listFavorites.observeAsState(initial = listOf())
    viewModel.getFavorites(applicationContext)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6EC7D7),
                        Color(0xFFAEE2FA),
                        Color(0xFFEAF7F9)
                    ), // Specify your gradient colors here
                    startY = 0f,
                    endY = 2000f // Adjust the end position as needed
                )
            )

    ) {
        Row(){
            mainHeader()
        }
        Box(
            modifier = Modifier
                .padding(top = 90.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White.copy(alpha = 0.3f))
        ) {
            Text(
                text = "Mis Favoritos",
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(fontSize = 25.sp, fontFamily = FontFamily(Font(R.font.literata_bold),))
            )
        }

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .padding(top = 75.dp, start = 10.dp)
        ){
            Text(
                text = "<",
                modifier = Modifier
                    .clickable {
                        navController.navigate("profile")
                    },
                style = TextStyle(fontSize = 50.sp, fontFamily = FontFamily(Font(R.font.literata_bold),))
            )
        }

        Row(){
            Box(
                modifier = Modifier
                    .padding(
                        start = 18.dp,
                        top = 160.dp,
                        end = 18.dp,
                        bottom = 100.dp
                    )
                    .background(Color.White.copy(alpha = 0.6f), shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    listFavorites.forEach { result ->
                        ResultFavorite(result) { selectedResult ->
                            // Handle selection of result and navigate to another activity
                            // Use context.startActivity(intent) to start another activity
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ResultFavorite(favorite: Favorite, onItemClick: (Favorite) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.facemask),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 8.dp)
                .weight(1f)
        ) {
            favorite.name?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
            favorite.description?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable { /* Handle icon click here */ }
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}