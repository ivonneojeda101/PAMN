package com.example.skan.data.network


import retrofit2.Response
import retrofit2.http.GET
import com.example.skan.R
import com.example.skan.domain.entities.Ingredient
import com.example.skan.domain.entities.Product

interface AnalizerAPIClient {
        @GET(AppConfig.GET_INGREDIENTS_ENDPOINT)
        suspend fun getIngredients(): Response<List<Ingredient>>

        @GET(AppConfig.ANALYZE_PRODUCT_ENDPOINT)
        suspend fun analyzeProduct(): Response<Product>
}