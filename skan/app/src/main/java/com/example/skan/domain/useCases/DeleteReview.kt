package com.example.skan.domain.useCases

import com.example.skan.data.interfaces.ReviewRepository
import com.example.skan.data.repositories.ReviewRepositoryImpl
import com.example.skan.domain.entities.Review

class DeleteReview {

    private val reviewRepository: ReviewRepository = ReviewRepositoryImpl()

    suspend operator fun invoke(idReview: Int): Any {
        return reviewRepository.deleteReview(idReview);
    }
}