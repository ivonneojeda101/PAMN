package com.example.skan.data.network


import retrofit2.Response
import retrofit2.http.GET
import com.example.skan.domain.entities.Ingredient
import com.example.skan.domain.entities.Product
import retrofit2.http.Body
import retrofit2.http.POST

interface AnalizerAPIClient {
        @POST(AppConfig.GET_INGREDIENTS_ENDPOINT)
        suspend fun getIngredients(@Body text: String): Response<Product>

        @POST(AppConfig.ANALYZE_PRODUCT_ENDPOINT)
        suspend fun getProduct(@Body barcode: String): Response<Product>
}