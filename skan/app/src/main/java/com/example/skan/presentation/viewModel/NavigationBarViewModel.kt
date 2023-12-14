package com.example.skan.presentation.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skan.data.dataManager.SharedPreferencesManager

class NavigationBarViewModel: ViewModel()  {
    private val _currentScreen = MutableLiveData<Int>(0)
    val currentScreen: LiveData<Int> = _currentScreen

    fun switchToScreen(index: Int) {
        _currentScreen.postValue(index)
    }

    fun getProfile(context: Context): Boolean {
        val sharedPreferencesManager = SharedPreferencesManager(context)
        val userData = sharedPreferencesManager.loadData("User")
        return userData.isNotEmpty()
    }

}