package com.example.skan.presentation.viewModel

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _permissionsGranted = MutableLiveData<Boolean>()

    fun checkPermissions(context: Context): Boolean {
        val hasPermissions = CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                context, it
            ) == PackageManager.PERMISSION_GRANTED
        }
        _permissionsGranted.value = hasPermissions
        return hasPermissions
    }

    fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity, CAMERAX_PERMISSIONS, 0
        )
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            android.Manifest.permission.CAMERA
        )
    }
}
