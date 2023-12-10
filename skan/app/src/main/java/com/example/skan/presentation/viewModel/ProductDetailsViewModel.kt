package com.example.skan.presentation.viewModel

import android.content.Context
import androidx.lifecycle.*
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.example.skan.data.dataManager.SharedPreferencesManager
import com.example.skan.domain.entities.Product

class ProductDetailsViewModel: ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product
    private val _saveProduct = MutableLiveData<Boolean>()
    val saveProduct: LiveData<Boolean> = _saveProduct

    fun getData(context: Context){
        print("Hola")
        val sharedPreferencesManager = SharedPreferencesManager(context)
        val productData = sharedPreferencesManager.loadData("Product")
        print(productData)
        if (productData.isNotEmpty()){
            val gson = Gson()
            val objectProduct = gson.fromJson(productData, Product::class.java)
            _product.value = objectProduct
        }
    }

    fun updateSave(save: Boolean){
        print("Hola")
        _saveProduct.value = save
    }
}