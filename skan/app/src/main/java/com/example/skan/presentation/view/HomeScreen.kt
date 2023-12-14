package com.example.skan.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skan.R
import androidx.compose.material.MaterialTheme as AppTheme
@Preview
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF6EC7D7), Color(0xFFAEE2FA), Color(0xFFEAF7F9)),
                startY = 0f,
                endY = 2000f
            ))
    ) {
        Row(){
            mainHeader()
        }
        ImageGallery()
    }
}

@Composable
fun ImageGallery() {

    val images = listOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5,
        R.drawable.img6,
        R.drawable.img7,
        R.drawable.img8,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.0f)) // Semi-transparent white background
            .padding(
                start = 18.dp,
                top = 60.dp,
                end = 18.dp,
                bottom = 100.dp
            )
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState())
        ) {
            val chunkedImages = images.chunked(2)
            chunkedImages.forEach { rowImages ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rowImages.forEach { image ->
                        Image(
                            painter = painterResource(id = image) as Painter,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(170.dp)
                                .background(Color.White.copy(alpha = 0.4f), shape = RoundedCornerShape(16.dp))
                        )
                    }
                }
            }
        }
    }
}

