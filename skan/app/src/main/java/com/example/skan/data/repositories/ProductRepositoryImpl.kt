package com.example.skan.data.repositories

import android.util.Log
import com.example.skan.data.interfaces.ProductRepository
import com.example.skan.data.network.AnalyzerAPISService
import com.example.skan.domain.entities.Ingredient
import com.example.skan.domain.entities.Product

class ProductRepositoryImpl: ProductRepository {

    private val api = AnalyzerAPISService()
    override fun createProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun getProduct(barcode: String): Product {
        //Implementing
        val ingredients = listOf(
            Ingredient(
                ewg = 1,
                cir = "A",
                name = "1,2-Hexanediol",
                cosmeticFunction = "(Solvent)",
            ),
            Ingredient(
                ewg = 1,
                cir = "",
                name = "Glyceryl Acrylate/Acrylic Acid Copolymer",
                cosmeticFunction = "(Humectant,Viscosity Controlling)",
            )
        )

        val product = Product(
            isParabenFree = true,
            isSulfateFree = true,
            isAlcoholFree = true,
            isSiliconeFree = false,
            isEUAllergenFree = true,
            isFungalAcneSafe = false,
            ingredients = ingredients
        )
        return product
    }

    override suspend fun getIngredients(text: String): List<Ingredient> {
        val ingredients = listOf(
            Ingredient(
                ewg = 1,
                cir = "A",
                name = "1,2-Hexanediol",
                cosmeticFunction = "(Solvent)",
                notableEffects = emptyList()
            ),
            Ingredient(
                ewg = 1,
                cir = "",
                name = "Glyceryl Acrylate/Acrylic Acid Copolymer",
                cosmeticFunction = "(Humectant,Viscosity Controlling)",
            ),
            Ingredient(
                ewg = 1,
                cir = "A",
                name = "Niacinamide",
                cosmeticFunction = "(Hair Conditioning,Skin Conditioning,Smoothing)",
            ),
            Ingredient(
                ewg = 1,
                cir = "A",
                name = "Dimethiconol",
                cosmeticFunction = "(Antifoaming Agent,Skin Conditioning,Emollient,Moisturising)",
            )
        )
        return ingredients
        //return api.getIngredients()
    }

    override suspend fun analyzeProduct(ingredients: List<Ingredient>): Product {
        //return api.analyzeProduct()
        val ingredients = listOf(
            Ingredient(
                ewg = 1,
                cir = "A",
                name = "1,2-Hexanediol",
                cosmeticFunction = "(Solvent)",
            ),
            Ingredient(
                ewg = 1,
                cir = "",
                name = "Glyceryl Acrylate/Acrylic Acid Copolymer",
                cosmeticFunction = "(Humectant,Viscosity Controlling)",
            )
        )

        val product = Product(
            isParabenFree = true,
            isSulfateFree = true,
            isAlcoholFree = true,
            isSiliconeFree = false,
            isEUAllergenFree = true,
            isFungalAcneSafe = false,
            ingredients = ingredients
        )
        return product
    }


}