package com.example.skan.data.interfaces

import com.example.skan.domain.entities.Ingredient

interface IngredientRepository {

    fun createIngredient(ingredient: Ingredient)

}