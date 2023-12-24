package com.example.skan.presentation.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skan.domain.entities.Favorite
import com.example.skan.domain.useCases.ManageFavorites

class FavoriteViewModel: ViewModel() {
    private val _listFavorites = MutableLiveData<List<Favorite>>()
    val listFavorites: LiveData<List<Favorite>> = _listFavorites


    fun getFavorites(context: Context){
        val managerFavorite: ManageFavorites = ManageFavorites(context)
        val result = managerFavorite.getFavorites()
        if (result != null){
            _listFavorites.value = result
        }
    }

    fun deleteFavorite(idProduct: Int, context: Context){
        val managerFavorite: ManageFavorites = ManageFavorites(context)
        managerFavorite.removeFavorite(idProduct)
        getFavorites(context)
    }
}