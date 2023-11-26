package com.example.skan.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.skan.R
import com.example.skan.domain.entities.Ingredient
import com.example.skan.domain.entities.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

class AnalyzerAPISService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getIngredients(): List<Ingredient> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(AnalizerAPIClient::class.java).getIngredients()
            response.body() ?: emptyList()
        }
    }

    suspend fun analyzeProduct(): Product{
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(AnalizerAPIClient::class.java).analyzeProduct()
            (response.body() ?: null)!!
        }
    }

}