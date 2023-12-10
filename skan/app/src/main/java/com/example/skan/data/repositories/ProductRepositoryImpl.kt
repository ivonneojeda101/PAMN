package com.example.skan.data.repositories

import android.util.Log
import com.example.skan.data.interfaces.ProductRepository
import com.example.skan.data.network.AnalyzerAPISService
import com.example.skan.domain.entities.Ingredient
import com.example.skan.domain.entities.Product
import java.util.concurrent.Flow

class ProductRepositoryImpl: ProductRepository {

    private val api = AnalyzerAPISService()
    override fun createProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun getProduct(barcode: String): Product {
        return api.getProduct(barcode)
    }

    override suspend fun analyzeIngredients(text: String): Product {
        return api.getIngredients(text)
    }
}