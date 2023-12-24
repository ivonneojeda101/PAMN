package com.example.skan.presentation.view

import android.content.Context
import android.util.Log
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skan.domain.entities.Product
import com.example.skan.presentation.viewModel.MainUIState
import com.example.skan.presentation.viewModel.SaveProductViewModel
import kotlinx.coroutines.*

@Composable
fun SaveScreen(product: Product) {
    val applicationContext = LocalContext.current
    val controller = remember {
        LifecycleCameraController(applicationContext).apply {
            setEnabledUseCases( CameraController.IMAGE_CAPTURE)}
    }
    val viewModel: SaveProductViewModel = SaveProductViewModel(controller)
    val name: String by viewModel.productName.observeAsState(initial = "")
    val description: String by viewModel.description.observeAsState(initial = "")
    var error: String by remember { mutableStateOf("") }
    var isWaiting by remember { mutableStateOf(false) }
    var isSaved: Boolean by remember { mutableStateOf(false) }

    CoroutineScope(Dispatchers.IO).launch {
        viewModel.uiState.collect() { uiState ->
            when (uiState) {
                is MainUIState.Loading -> {}
                is MainUIState.Error -> {
                    error = uiState.msg
                }
                is MainUIState.Saved -> {
                    isSaved = uiState.isSaved
                }
                is MainUIState.Progress -> {
                    isWaiting = uiState.isWaiting
                }
            }
        }
    }
    if (isSaved){
        ProductDetails()
    }
    else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6EC7D7),
                            Color(0xFFAEE2FA),
                            Color(0xFFEAF7F9)
                        ),
                        startY = 0f,
                        endY = 2000f
                    )
                )

        ) {
            Row(){
                mainHeader()
            }

            Row(){
                Box(
                    modifier = Modifier
                        .padding(
                            start = 18.dp,
                            top = 65.dp,
                            end = 18.dp,
                            bottom = 100.dp
                        )
                        .background(
                            Color.White.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(5.dp)
                    ) {
                        BarcodeCaptureScreen(name, description, viewModel, controller, product, applicationContext)
                        if (isWaiting){LoadingProgressBar()}
                        if (error.length > 0){
                            Text(
                                text = error,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 8.dp),
                                color = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BarcodeCaptureScreen(name: String, description: String, viewModel: SaveProductViewModel, controller: LifecycleCameraController, product: Product, context: Context) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp)
    ) {
        Text(
            text = "Guardar Producto",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Diligencie los campos y capture el código de barras",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 15.dp)
        )
        OutlinedTextField(
            value = name,
            onValueChange = {
                viewModel.updateName(it)
            },
            label = {Text(text = "Nombre del Producto")},
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = {
                viewModel.updateDescription(it)
            },
            label = { Text("Descripción") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .padding(
                    top = 100.dp,
                    bottom = 120.dp
                )
        ) {
            CameraPreview(
                controller = controller,
                modifier = Modifier
                    .size(width = 200.dp, height = 60.dp)
            )
        }

        Button(
            colors = buttonColors(backgroundColor = MaterialTheme.colors.background),
            onClick = {
                viewModel.saveProduct(context = context, product = product, name = name, description = description)
                      },
            enabled = name.isNotEmpty() && description.isNotEmpty(),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        ) {
            Text("Capturar código y guardar")
        }
    }
}

@Composable
fun LoadingProgressBar() {
    val infiniteTransition = rememberInfiniteTransition()
    val animationValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .height(8.dp)
            .background(color = Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animationValue)
                .height(8.dp)
                .background(color = Color.Gray)
        )
    }
}

