package com.example.skan.data.network


import com.example.skan.domain.entities.Favorite
import retrofit2.Response
import retrofit2.http.GET
import com.example.skan.domain.entities.Ingredient
import com.example.skan.domain.entities.Product
import com.example.skan.domain.entities.User
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AnalizerAPIClient {
        @POST(AppConfig.GET_INGREDIENTS_ENDPOINT)
        suspend fun getIngredients(@Body text: String): Response<Product>

        @POST(AppConfig.ANALYZE_PRODUCT_ENDPOINT)
        suspend fun getProduct(@Body barcode: String): Response<Product>

        @POST(AppConfig.CREATE_PRODUCT)
        @Headers("Content-Type: application/json")
        suspend fun createProduct(@Body requestBody: RequestBody): Response<Int>

        @POST(AppConfig.CREATE_USER)
        @Headers("Content-Type: application/json")
        suspend fun createUser(@Body user: User): Response<Int>

        @POST(AppConfig.LOGIN_USER)
        @Headers("Content-Type: application/json")
        suspend fun loginUser(@Body requestBody: RequestBody): Response<User>

        @POST(AppConfig.SEARCH_KEYWORD)
        suspend fun searchKeyword(@Body text: String): Response<List<Favorite>>

        @POST(AppConfig.GET_ID_PRODUCT)
        suspend fun getProductById(@Body idProduct: Int): Response<Product>
}