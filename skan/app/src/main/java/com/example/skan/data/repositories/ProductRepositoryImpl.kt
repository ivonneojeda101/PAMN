package com.example.skan.data.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.skan.data.interfaces.ProductRepository
import com.example.skan.data.network.AnalyzerAPIService
import com.example.skan.domain.entities.Favorite
import com.example.skan.domain.entities.Ingredient
import com.example.skan.domain.entities.Product
import java.util.concurrent.Flow

class ProductRepositoryImpl: ProductRepository {

    private val api = AnalyzerAPIService()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun createProduct(product: Product): Int {
        return api.createProduct(product)
    }

    override suspend fun getProduct(barcode: String): Product {
        return api.getProduct(barcode)
    }

    override suspend fun analyzeIngredients(text: String): Product {
        return api.getIngredients(text)
    }

    override suspend fun getProductById(idProduct: Int): Product {
        return api.getProductById(idProduct)
    }

    override suspend fun searchKeyword(keyword: String): List<Favorite>{
        return api.searchKeyword(keyword)
    }

}