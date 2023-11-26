package com.example.skan.domain.entities

data class Product (
    val id: Int? = null,
    val name: String? = null,
    val barcode: String? = null,
    val isParabenFree: Boolean? = null,
    val isSulfateFree: Boolean? = null,
    val isAlcoholFree: Boolean? = null,
    val isSiliconeFree: Boolean? = null,
    val isEUAllergenFree: Boolean? = null,
    val isFungalAcneSafe: Boolean? = null,
    val ingredients: List<Ingredient> = emptyList()
){
    fun isEmpty(): Boolean {
        return this == emptyProduct
    }

    companion object {
        val emptyProduct = Product()
    }
}
