package com.example.skan.presentation.viewModel

import android.content.Context
import androidx.lifecycle.*
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.example.skan.data.dataManager.SharedPreferencesManager
import com.example.skan.domain.entities.Favorite
import com.example.skan.domain.entities.Product
import com.example.skan.domain.useCases.ManageFavorites

class ProductDetailsViewModel: ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product
    private val _saveProduct = MutableLiveData<Boolean>()
    val saveProduct: LiveData<Boolean> = _saveProduct
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getData(context: Context){
        val sharedPreferencesManager = SharedPreferencesManager(context)
        val productData = sharedPreferencesManager.loadData("Product")
        if (productData.isNotEmpty()){
            val gson = Gson()
            val objectProduct = gson.fromJson(productData, Product::class.java)
            _product.value = objectProduct
            objectProduct.id?.let { validateFavorite(context, it) }
        }
    }

    fun updateSave(save: Boolean){
        _saveProduct.value = save
    }

    fun validateFavorite(context: Context, id: Int){
        val managerFavorites = ManageFavorites(context)
        _isFavorite.value = managerFavorites.isFavorite(id)
    }

    fun addFavorite(context: Context){
        val managerFavorites = ManageFavorites(context)
        val productI = product.value
        managerFavorites.addFavorite(Favorite(id = productI?.id!!, name = productI.name!!, description = productI.description!!))
        _isFavorite.value = true
    }
}