package com.example.skan.domain.entities

import com.google.gson.annotations.SerializedName
import java.util.*

data class Review(
    val id: Int = 0,
    val title: String? = null,
    val description: String? = null,
    val rate: Int? = null,
    val idAuthor: Int? = null,
    val idProduct: Int? = null,
    val authorName: String? = null,
    val productName: String? = null,
    val creationDate: String? = null
)