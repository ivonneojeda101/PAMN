package com.example.skan.domain.useCases

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.skan.data.dataManager.SharedPreferencesManager
import com.example.skan.data.interfaces.ProductRepository
import com.example.skan.data.repositories.ProductRepositoryImpl
import com.example.skan.domain.entities.Product
import com.google.gson.Gson

class CreateProduct {
    private val productRepository: ProductRepository = ProductRepositoryImpl()
    private val processBarCode: ProcessBarCode = ProcessBarCode()

    suspend operator fun invoke(product: Product, image: Bitmap, context: Context): Boolean {
        var finalBarCode = processBarCode.scanBarcodes(image)
        if (finalBarCode != null && finalBarCode.isNotEmpty()) {
            Log.println(Log.ASSERT,"Create Product", finalBarCode)
            product.barcode = finalBarCode
            val response = productRepository.createProduct(product)
            Log.println(Log.ASSERT,"Product Response", response.toString())
            if (response > 0) {
                product.id = response
                val sharedPreferencesManager = SharedPreferencesManager(context)
                val gson = Gson()
                val json = gson.toJson(product)
                sharedPreferencesManager.saveData("Product", json)
                return true
            }
            return false
        }
        else {
            return false
        }
    }
}