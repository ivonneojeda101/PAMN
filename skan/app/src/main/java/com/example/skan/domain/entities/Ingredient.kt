package com.example.skan.domain.entities

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("idIngredient")
    val id: Int? = null,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("CIR")
    val cir: String? = "",
    @SerializedName("EWG")
    val ewg: Int? = 0,
    @SerializedName("drySkin")
    val drySkin: Boolean? = null,
    @SerializedName("oilSkin")
    val oilSkin: Boolean? = null,
    @SerializedName("sensitiveSkin")
    val sensitiveSkin: Boolean? = null,
    @SerializedName("notableEffects")
    val notableEffects: List<NotableEffect> = emptyList()
) {
    fun isEmpty(): Boolean {
        return this == emptyIngredient
    }

    companion object {
        val emptyIngredient = Ingredient()
    }
}