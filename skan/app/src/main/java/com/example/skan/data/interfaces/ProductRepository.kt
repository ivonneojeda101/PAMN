package com.example.skan.data.interfaces

import com.example.skan.domain.entities.Ingredient
import com.example.skan.domain.entities.Product

interface ProductRepository {

    fun createProduct(product: Product)

    suspend fun getProduct(barcode: String): Product

    suspend fun analyzeIngredients(text: String): Product
}