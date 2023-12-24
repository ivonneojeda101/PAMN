package com.example.skan.presentation.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skan.data.dataManager.SharedPreferencesManager
import com.example.skan.domain.entities.Review
import com.example.skan.domain.entities.User
import com.example.skan.domain.useCases.DeleteReview
import com.example.skan.domain.useCases.GetReviews
import com.example.skan.domain.useCases.ManageFavorites
import com.google.gson.Gson
import kotlinx.coroutines.launch

class ReviewViewModel: ViewModel() {
    private val _listReviews = MutableLiveData<List<Review>>()
    val listReviews: LiveData<List<Review>> = _listReviews
    private val _idUser = MutableLiveData<Int>()
    val idUser: LiveData<Int> = _idUser
    private var reviewProvider = GetReviews()
    private var deleteReview = DeleteReview()

    fun getData(context: Context){
        getUserId(context)
        if (_idUser.value!! > 0) {
            viewModelScope.launch {
                getReviews(_idUser.value!!)
            }
        }
    }

    private suspend fun getReviews(idUser: Int ) {
        val result = reviewProvider.getUserReviews(idUser)
        if (result != null && result.isNotEmpty()){
            _listReviews.postValue(result)
        }
    }
    private fun getUserId(context: Context){
        val sharedPreferencesManager = SharedPreferencesManager(context)
        val userData = sharedPreferencesManager.loadData("User")
        if (userData.isNotEmpty()){
            val gson = Gson()
            val objectUser = gson.fromJson(userData, User::class.java)
            _idUser.value = objectUser.id
        }else{
            _idUser.value = 0
        }
    }

    fun deleteReview(idReview: Int, context: Context){
        viewModelScope.launch {
            var result = deleteReview(idReview) as Boolean
           if (result){
                getReviews(_idUser.value!!)
            }
        }
    }
}