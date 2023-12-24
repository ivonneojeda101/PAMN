package com.example.skan.data.network

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.skan.domain.entities.Favorite
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.skan.domain.entities.Product
import com.example.skan.domain.entities.Product.Companion.emptyProduct
import com.example.skan.domain.entities.Review
import com.example.skan.domain.entities.User
import com.example.skan.domain.entities.User.Companion.emptyUser
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.json.JSONObject
import okhttp3.RequestBody
import org.json.JSONArray
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

class AnalyzerAPIService {

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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    suspend fun createProduct(product: Product): Int {
        try{
            val jsonObject = JSONObject()
            val ingredientsArray = JSONArray()
            product.name?.let { jsonObject.put("name", it) }
            product.description?.let { jsonObject.put("description", it) }
            product.barcode?.let { jsonObject.put("barcode", it) }


            for (ingredient in product.ingredients) {
                if (ingredient.id!! > 0) {
                    ingredientsArray.put(ingredient.id)
                }
            }
            jsonObject.put("ingredients", ingredientsArray)
            val requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(AnalizerAPIClient::class.java).createProduct(requestBody)
                if (response.isSuccessful) {
                    response.body() ?: 0
                } else {
                    0
                }
            }
        }
        catch (e: Exception) {
            print(e)
            return 0
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    suspend fun createUser(user: User): Any {
        try{
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(AnalizerAPIClient::class.java).createUser(user)
                if (response.isSuccessful) {
                    response.body() ?: 0
                } else {
                    if (response.code() != 500) {
                        val objectS = response.errorBody()?.string().toString()
                        objectS
                    }
                    else {
                        "No fue posible conectarse al servidor"
                    }
                }
            }
        }
        catch (e: Exception) {
            print(e)
            return "No fue posible conectarse al servidor"
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    suspend fun loginUser(email: String, password: String): Any {
        try{
            val jsonObject = JSONObject()
            jsonObject.put("email", email)
            jsonObject.put("password", password)

            val requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(AnalizerAPIClient::class.java).loginUser(requestBody)
                if (response.isSuccessful) {
                    response.body() ?: emptyUser
                } else {
                    if (response.code() != 500) {
                        val objectS = response.errorBody()?.string().toString()
                        objectS
                    }
                    else {
                        "No fue posible conectarse al servidor"
                    }
                }
            }
        }

        catch (e: Exception) {
            Log.println(Log.ASSERT,"Error Retrofit ", e.toString())
            return "No fue posible conectarse al servidor"
        }
    }

    suspend fun getProductById(idProduct: Int): Product{
        try{
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(AnalizerAPIClient::class.java).getProductById(idProduct)
                (response.body() ?: emptyProduct)!!
            }
        }
        catch (e: Exception) {
            print(e)
            return emptyProduct
        }
    }

    suspend fun searchKeyword(keyword: String): List<Favorite>{
        try{
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(AnalizerAPIClient::class.java).searchKeyword(keyword)
                (response.body() ?: listOf())!!
            }
        }
        catch (e: Exception) {
            print(e)
            return listOf()
        }
    }

    suspend fun createReview(review: Review): Boolean {
        try{
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(AnalizerAPIClient::class.java).createReview(review)
                response.isSuccessful
            }
        }
        catch (e: Exception) {
            print(e)
            return false
        }
    }

    suspend fun getReviews(idProduct: Int): List<Review> {
        try{
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(AnalizerAPIClient::class.java).getReviews(idProduct)
                (response.body() ?: listOf())!!
            }
        }
        catch (e: Exception) {
            Log.println(Log.ASSERT, "SKINError", e.toString())
            return emptyList()
        }
    }

    suspend fun getUserReviews(idUser: Int): List<Review> {
        try {
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(AnalizerAPIClient::class.java).getUserReviews(idUser)
                (response.body() ?: listOf())!!
            }
        }
        catch (e: Exception) {
            Log.println(Log.ASSERT, "SKINError", e.toString())
            return listOf()
        }
    }

    suspend fun deleteReview (idReview: Int): Boolean {
        try {
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(AnalizerAPIClient::class.java).deleteReview(idReview)
                response.isSuccessful
            }
        }
        catch (e: Exception) {
            Log.println(Log.ASSERT, "SKINError", e.toString())
            return false
        }
    }
}