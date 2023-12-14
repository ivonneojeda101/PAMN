package com.example.skan.data.interfaces

import com.example.skan.domain.entities.Favorite
import com.example.skan.domain.entities.Ingredient
import com.example.skan.domain.entities.Product

interface ProductRepository {

    suspend fun createProduct(product: Product): Int

    suspend fun getProduct(barcode: String): Product

    suspend fun analyzeIngredients(text: String): Product

    suspend fun getProductById(idProduct: Int): Product

    suspend fun searchKeyword(keyword: String): List<Favorite>
}