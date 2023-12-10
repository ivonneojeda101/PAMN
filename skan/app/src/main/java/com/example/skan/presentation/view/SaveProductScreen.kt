package com.example.skan.presentation.view

import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skan.presentation.viewModel.SaveProductViewModel


@Composable
@Preview
fun SaveScreen() {
    val viewModel: SaveProductViewModel = SaveProductViewModel()
    val applicationContext = LocalContext.current
    val name: String by viewModel.productName.observeAsState(initial = "")
    val description: String by viewModel.description.observeAsState(initial = "")
    val controller = remember {
        LifecycleCameraController(applicationContext).apply {
            setEnabledUseCases( CameraController.IMAGE_CAPTURE)}
    }
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

        Row(){
            Box(
                modifier = Modifier
                    .padding(
                        start = 18.dp,
                        top = 70.dp,
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
                    BarcodeCaptureScreen(name, description, viewModel, controller)
                }
            }
        }
    }
}

@Composable
fun BarcodeCaptureScreen(name: String, description: String, viewModel: SaveProductViewModel, controller: LifecycleCameraController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Guardar Producto",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        Text(
            text = "Diligencie los campos y capturar el código de barras",
            textAlign = TextAlign.Justify,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { text: String ->
                viewModel.updateName(text)
            },
            label = {Text(text = "Nombre del Producto")},
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = {text: String ->
                viewModel.updateDescription(text)
            },
            label = { Text("Descripción") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .padding(
                    start = 56.dp,
                    top = 113.dp,
                    end = 1.dp,
                    bottom = 120.dp
                )
        ) {
            CameraPreview(
                controller = controller,
                modifier = Modifier
                    .size(width = 200.dp, height = 40.dp)
            )
        }

        Button(
            colors = buttonColors(backgroundColor = MaterialTheme.colors.background),
            onClick = { /* Handle taking photo */ },
            enabled = name.isNotEmpty() && description.isNotEmpty(),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text("Guardar")
        }
    }
}

