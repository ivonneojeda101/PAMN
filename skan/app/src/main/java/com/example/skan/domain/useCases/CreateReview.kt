package com.example.skan.domain.useCases

import com.example.skan.data.interfaces.ReviewRepository
import com.example.skan.data.repositories.ReviewRepositoryImpl
import com.example.skan.domain.entities.Review

class CreateReview {

    private val reviewRepository: ReviewRepository = ReviewRepositoryImpl()

    suspend operator fun invoke(review: Review): Any {
        return reviewRepository.createReview(review);
    }
}