package com.example.skan.data.network

object AppConfig {
    const val API_BASE_URL = "http://192.168.1.135:8000"
    const val GET_INGREDIENTS_ENDPOINT = "/getIngredientsAnalysis"
    const val ANALYZE_PRODUCT_ENDPOINT = "/getBarcode"
    const val CREATE_PRODUCT = "/Product"
    const val CREATE_USER = "/User"
    const val LOGIN_USER = "/Login"
    const val GET_ID_PRODUCT = "/getId"
    const val SEARCH_KEYWORD = "/search"
    const val CREATE_REVIEW = "/Review"
    const val GET_REVIEWS = "/Reviews"
    const val GET_USER_REVIEWS = "/userReviews"
    const val DELETE_REVIEW = "/deleteReview"
}