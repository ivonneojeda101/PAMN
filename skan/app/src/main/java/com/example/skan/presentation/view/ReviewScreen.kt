package com.example.skan.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.skan.R
import com.example.skan.domain.entities.Favorite
import com.example.skan.domain.entities.Review
import com.example.skan.presentation.viewModel.ReviewViewModel

@Composable
fun ReviewScreen(navController: NavHostController) {
    val applicationContext = LocalContext.current
    val viewModel = ReviewViewModel()
    val listReviews: List<Review> by viewModel.listReviews.observeAsState(initial = listOf())
    viewModel.getData(applicationContext)
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
                text = "Mis Reseñas",
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
                    listReviews.forEach { result ->
                        ResultReview(result) { selectedResult ->
                            viewModel.deleteReview(selectedResult.id, applicationContext)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ResultReview(review: Review, onItemClick: (Review) -> Unit) {
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
                .fillMaxWidth()
        ) {
            review.productName?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }
            Divider()
           review.title?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
            review.description?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Justify
                )
            }
            Row {
                repeat(5) { index ->
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = if (index < review.rate!!) {
                            Color(0xFFffd966)
                        } else {
                            Color.Unspecified
                        },
                    )
                }
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { onItemClick(review) }
                        .padding(4.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}