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
import androidx.lifecycle.*
import com.example.skan.domain.useCases.ProcessBarCode
import com.example.skan.domain.useCases.ProcessIngredients
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CameraViewModel(private val controller: LifecycleCameraController): ViewModel() {
    private val _foundBarCode = MutableLiveData<Boolean>()
    val foundBarCode: LiveData<Boolean> = _foundBarCode
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _productIsReady = MutableLiveData<Boolean>()
    val productIsReady: LiveData<Boolean> = _productIsReady
    var processBarCode = ProcessBarCode()
    var processIngredients = ProcessIngredients()

    fun takePhoto(context: Context, option: Int) {
        val coroutineScope = viewModelScope
        _isLoading.postValue(true)

        controller.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    val matrix = Matrix().apply { postRotate(image.imageInfo.rotationDegrees.toFloat()) }
                    val rotatedBitmap = Bitmap.createBitmap(
                        image.toBitmap(), 0, 0, image.width, image.height, matrix, true)

                    when (option) {
                        1 -> coroutineScope.launch {updateState(processBarCode(rotatedBitmap, context)!!) }
                        2 -> coroutineScope.launch {updateState(processIngredients(rotatedBitmap, context)!!)}
                    }
                    //image.close()
                }

                override fun onError(exception: ImageCaptureException) {
                    when (option) {
                        1 -> _foundBarCode.postValue(false)
                        2 -> _foundBarCode.postValue(true)
                    }
                    coroutineScope.coroutineContext.cancel()
                    super.onError(exception)
                    Log.e("Camera", "Couldn't take photo: ", exception)
                }
            }
        )
    }

    private suspend fun updateState(result: Boolean){
        _foundBarCode.postValue(result)
        if (result) {_productIsReady.postValue(true)}
        _isLoading.postValue(false)
    }
}