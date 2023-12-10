package com.example.skan.presentation.view

import android.content.Context
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.skan.R
import com.example.skan.presentation.viewModel.CameraViewModel


@Preview
@Composable
@ExperimentalMaterial3Api
fun CameraScreen() {
    val applicationContext = LocalContext.current
    val controller = remember {
        LifecycleCameraController(applicationContext).apply {
            setEnabledUseCases( CameraController.IMAGE_CAPTURE)}
    }
    val viewModel: CameraViewModel = CameraViewModel(controller)
    val foundBarCode: Boolean by viewModel.foundBarCode.observeAsState(initial = true)
    val productIsReady: Boolean by viewModel.productIsReady.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)

    val scaffoldState = rememberBottomSheetScaffoldState()

    var optionText = if (foundBarCode){ "Escanear código de barras"} else {"Escanear Ingredientes"}

    if (productIsReady){
        ProductDetails()
    }
    else {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp,
            sheetContent = {
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CameraPreview(
                    controller = controller,
                    modifier = Modifier
                        .fillMaxSize()
                )
                if(isLoading){
                    CircularLoadingIndicator()
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(90.dp)
                        .wrapContentSize(Alignment.BottomCenter)
                ) {
                    Text(
                        text = optionText,
                        color = MaterialTheme.colorScheme.background,
                        maxLines = 1)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(100.dp)
                        .wrapContentSize(Alignment.BottomCenter)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .layoutId("Icon")
                            .align(Alignment.Center)
                    ) {
                        photoButton(viewModel, applicationContext, foundBarCode)
                    }
                }
            }
        }
    }
}

@Composable
fun photoButton(viewModel: CameraViewModel, context: Context, foundBarCode: Boolean) {
    //TODO Pendiente definir el comportamiento cuando no hay resultados de ningun tipo
    IconButton(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .size(90.dp)
            .layoutId("icon"),
        onClick = {
            if (foundBarCode){
                viewModel.takePhoto(context = context, 1)
            }
            else {
                viewModel.takePhoto(context = context, 2)
            }
        }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.camera) as Painter,
                contentDescription = if (foundBarCode){ "Escanear código de barras"} else {"Escanear Ingredientes"},
                modifier = Modifier.size(70.dp),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}

@Composable
fun CircularLoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(60.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            strokeWidth = 4.dp,
            color =  MaterialTheme.colorScheme.background// Change the color as needed
        )
    }
}



