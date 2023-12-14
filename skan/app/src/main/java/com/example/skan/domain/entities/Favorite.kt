package com.example.skan.domain.entities

import com.google.gson.annotations.SerializedName

data class Favorite(
    @SerializedName("idProduct")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String

)