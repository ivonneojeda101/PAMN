package com.example.skan.domain.useCases

import android.content.Context
import android.util.Log
import com.example.skan.data.dataManager.SharedPreferencesManager
import com.example.skan.domain.entities.Favorite
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ManageFavorites(context: Context) {
    private val context = context
    private val sharedPreferencesManager = SharedPreferencesManager(context)
    private val gson = Gson()

    fun addFavorite(favorite: Favorite){
        var listFavorites = getFavorites()
        listFavorites.add(favorite)
        val json = gson.toJson(listFavorites)
        sharedPreferencesManager.saveData("Favorites", json)
    }

    fun getFavorites(): MutableList<Favorite> {
        val favoritesData = sharedPreferencesManager.loadData("Favorites")
        val type = object : TypeToken<List<Favorite>>() {}.type
        val listFavorites = gson.fromJson<List<Favorite>>(favoritesData, type)
        if (listFavorites != null){
            Log.println(Log.ASSERT, "Favoriteos: ", favoritesData)
            return listFavorites.toMutableList()
        }
        else {
            return mutableListOf()
        }
    }

    fun isFavorite(idProduct: Int): Boolean {
        var listFavorites = getFavorites()
        var result = listFavorites.find { it.id == idProduct}
        return result != null
    }

    fun removeFavorite(idProduct: Int) {
        var listFavorites = getFavorites()
        listFavorites.removeAll { it.id == idProduct}
        val json = gson.toJson(listFavorites)
        sharedPreferencesManager.saveData("Favorites", json)
    }
}