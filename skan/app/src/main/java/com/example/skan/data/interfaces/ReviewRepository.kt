package com.example.skan.data.interfaces

import com.example.skan.domain.entities.Review

interface ReviewRepository {

    suspend fun createReview(review: Review): Any

    suspend fun getReviews(idProduct: Int): List<Review>

    suspend fun getUserReviews(idUser: Int): List<Review>

    suspend fun deleteReview(idReview: Int): Any

}