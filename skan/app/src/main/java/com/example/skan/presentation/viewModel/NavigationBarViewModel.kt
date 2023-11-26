package com.example.skan.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavigationBarViewModel: ViewModel()  {
    private val _currentScreen = MutableLiveData<Int>(0)
    val currentScreen: LiveData<Int> = _currentScreen

    fun switchToScreen(index: Int) {
        _currentScreen.postValue(index)
    }

}