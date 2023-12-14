package com.example.skan.presentation.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skan.domain.entities.Product
import com.example.skan.domain.useCases.CreateProduct
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

sealed class MainUIState {
    object Loading : MainUIState()
    data class Progress(val isWaiting: Boolean) : MainUIState()
    data class Saved(val isSaved: Boolean) : MainUIState()
    data class Error(val msg: String) : MainUIState()
}
class SaveProductViewModel(private val controller: LifecycleCameraController): ViewModel() {
    private val _productName = MutableLiveData<String>()
    val productName: LiveData<String> = _productName
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description
    private val _uiState = MutableStateFlow<MainUIState>(MainUIState.Loading)
    val uiState: StateFlow<MainUIState> = _uiState.asStateFlow()
    private var createProduct = CreateProduct()

    fun updateName(name: String) {
        _productName.postValue(name)
    }

    fun updateDescription(description: String) {
        _description.postValue(description)
    }
    fun saveProduct(context: Context, product: Product, name: String, description: String) {
        val coroutineScope = viewModelScope
        product.name = name
        product.description = description
        _uiState.value = MainUIState.Progress(true)

        controller.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    val matrix =
                        Matrix().apply { postRotate(image.imageInfo.rotationDegrees.toFloat()) }
                    val rotatedBitmap = Bitmap.createBitmap(
                        image.toBitmap(), 0, 0, image.width, image.height, matrix, true
                    )
                    coroutineScope.launch {
                        updateState(createProduct(product, rotatedBitmap, context), image)
                    }
                }
                override fun onError(exception: ImageCaptureException) {
                    coroutineScope.launch {
                        _uiState.emit(MainUIState.Saved(false))
                        _uiState.emit(MainUIState.Progress(false))
                    }
                    coroutineScope.coroutineContext.cancel()
                    super.onError(exception)
                    Log.e("SaveProduct", "Error creating the product: ", exception)
                }
            }
        )
    }

    private suspend fun updateState(result: Boolean,  image: ImageProxy,
    ) {
        if(!result){
            delay(1000)
            _uiState.value = MainUIState.Error( "Error al registrar el producto, intente nuevamente")
            delay(1000)
            _uiState.value = MainUIState.Saved(false)
        }
        else {
            delay(1000)
            _uiState.value = MainUIState.Saved(true)
        }
        delay(1000)
        _uiState.value = MainUIState.Progress(false)
        delay(1000)
        image.close()
    }
}

