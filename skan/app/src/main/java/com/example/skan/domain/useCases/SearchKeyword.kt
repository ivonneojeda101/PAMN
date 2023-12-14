package com.example.skan.domain.useCases

import com.example.skan.data.interfaces.ProductRepository
import com.example.skan.data.repositories.ProductRepositoryImpl
import com.example.skan.domain.entities.Favorite

class SearchKeyword {
    private val productRepository: ProductRepository = ProductRepositoryImpl()

    suspend operator fun invoke(keyword: String): List<Favorite> {
        return productRepository.searchKeyword(keyword)
    }
}