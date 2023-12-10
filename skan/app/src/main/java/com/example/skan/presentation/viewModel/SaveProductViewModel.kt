package com.example.skan.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SaveProductViewModel {
    private val _productName = MutableLiveData<String>()
    val productName: LiveData<String> = _productName
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description
    private val _isFieldsFilled = MutableLiveData<Boolean>()
    val isFieldsFilled: LiveData<Boolean> = _isFieldsFilled

    fun updateName(name: String){
        _productName.value = name
    }

    fun updateDescription(description: String){
        _description.value = description
    }

}