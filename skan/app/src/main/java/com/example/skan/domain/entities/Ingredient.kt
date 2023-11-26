package com.example.skan.domain.entities

data class Ingredient(
    val id: Int? = null,
    val name: String = "",
    val cosmeticFunction: String? = null,
    val cir: String? = null,
    val ewg: Int? = null,
    val goodDrySkin: Boolean? = null,
    val goodOilSkin: Boolean? = null,
    val goodSensitiveSkin: Boolean? = null,
    val notableEffects: List<NotableEffect> = emptyList()
) {
    fun isEmpty(): Boolean {
        return this == emptyIngredient
    }

    companion object {
        val emptyIngredient = Ingredient()
    }
}