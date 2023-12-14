package com.example.skan.domain.entities

import com.google.gson.annotations.SerializedName

data class Product (
    @SerializedName("idProduct")
    var id: Int? = null,
    @SerializedName("productName")
    var name: String? = null,
    @SerializedName("barcode")
    var barcode: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("parabenFree")
    val parabenFree: Boolean? = null,
    @SerializedName("sulfateFree")
    val sulfateFree: Boolean? = null,
    @SerializedName("alcoholFree")
    val alcoholFree: Boolean? = null,
    @SerializedName("siliconeFree")
    val siliconeFree: Boolean? = null,
    @SerializedName("euAllergenFree")
    val euAllergenFree: Boolean? = null,
    @SerializedName("fungalAcne")
    val fungalAcne: Boolean? = null,
    @SerializedName("acneFighting")
    val acneFighting: List<String>? = emptyList(),
    @SerializedName("brightening")
    val brightening: List<String>? = emptyList(),
    @SerializedName("uvProtection")
    val uvProtection: List<String>? = emptyList(),
    @SerializedName("woundHealing")
    val woundHealing: List<String>? = emptyList(),
    @SerializedName("antiAging")
    val antiAging: List<String>? = emptyList(),
    @SerializedName("goodDrySkin")
    val goodDrySkin: Int? = null,
    @SerializedName("badDrySkin")
    val badDrySkin: Int? = null,
    @SerializedName("goodOilSkin")
    val goodOilSkin: Int? = null,
    @SerializedName("badOilSkin")
    val badOilSkin: Int? = null,
    @SerializedName("goodSensitiveSkin")
    val goodSensitiveSkin: Int? = null,
    @SerializedName("badSensitiveSkin")
    val badSensitiveSkin: Int? = null,
    @SerializedName("ingredients")
    val ingredients: List<Ingredient> = emptyList()
){
    fun isEmpty(): Boolean {
        return this == emptyProduct
    }

    companion object {
        val emptyProduct = Product()
    }
}
