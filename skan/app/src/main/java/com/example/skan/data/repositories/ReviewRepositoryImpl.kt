package com.example.skan.data.repositories

import com.example.skan.data.interfaces.ReviewRepository
import com.example.skan.data.network.AnalyzerAPIService
import com.example.skan.domain.entities.Review


class ReviewRepositoryImpl: ReviewRepository {

    private val api = AnalyzerAPIService()

    override suspend fun createReview(review: Review): Boolean {
        return api.createReview(review);
    }

    override suspend fun getReviews(idProduct: Int): List<Review> {
        return api.getReviews(idProduct);
    }

    override suspend fun getUserReviews(idUser: Int): List<Review> {
        return api.getUserReviews(idUser);
    }

    override suspend fun deleteReview(idReview: Int): Boolean {
        return api.deleteReview(idReview);
    }


}