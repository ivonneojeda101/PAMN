package com.example.skan.domain.useCases

import android.content.Context
import android.graphics.Bitmap
import com.example.skan.data.dataManager.SharedPreferencesManager
import com.example.skan.data.interfaces.ProductRepository
import com.example.skan.data.repositories.ProductRepositoryImpl
import com.google.gson.Gson

class GetProduct {

    private val productRepository: ProductRepository = ProductRepositoryImpl()

    suspend operator fun invoke(idProduct: Int, context: Context): Boolean {
            val product = productRepository.getProductById(idProduct)
            if (!product.isEmpty()) {
                val sharedPreferencesManager = SharedPreferencesManager(context)
                val gson = Gson()
                val json = gson.toJson(product)
                sharedPreferencesManager.saveData("Product", json)
                return true
            }
            return false
    }
}