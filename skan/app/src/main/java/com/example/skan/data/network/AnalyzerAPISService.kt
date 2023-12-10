package com.example.skan.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.skan.domain.entities.Product
import com.example.skan.domain.entities.Product.Companion.emptyProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        print(AppConfig.API_BASE_URL)
        val timeout = 60L // Timeout in seconds

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(timeout, TimeUnit.SECONDS) // Read timeout
            .build()

        return Retrofit.Builder()
            .baseUrl(AppConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}

class AnalyzerAPISService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getIngredients(text: String): Product {
        try{
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(AnalizerAPIClient::class.java).getIngredients(text)
                response.body() ?: emptyProduct
            }
        }
        catch (e: Exception) {
            print(e)
            return emptyProduct
        }
    }

    suspend fun getProduct(barcode: String): Product{
        try{
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(AnalizerAPIClient::class.java).getProduct(barcode)
                (response.body() ?: emptyProduct)!!
            }
        }
        catch (e: Exception) {
            print(e)
            return emptyProduct
        }
    }

}