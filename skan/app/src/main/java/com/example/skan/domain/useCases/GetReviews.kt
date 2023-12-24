package com.example.skan.domain.useCases

import com.example.skan.data.interfaces.ReviewRepository
import com.example.skan.data.repositories.ReviewRepositoryImpl
import com.example.skan.domain.entities.Review

class GetReviews {

    private val reviewRepository: ReviewRepository = ReviewRepositoryImpl()

    suspend fun getReviews(idProduct: Int): List<Review> {
        return reviewRepository.getReviews(idProduct);
    }

    suspend fun getUserReviews(idUser: Int): List<Review> {
        return reviewRepository.getUserReviews(idUser)
    }
}