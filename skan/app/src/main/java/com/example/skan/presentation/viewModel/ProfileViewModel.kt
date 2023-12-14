package com.example.skan.presentation.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skan.data.dataManager.SharedPreferencesManager
import com.example.skan.domain.entities.User
import com.google.gson.Gson

class ProfileViewModel: ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    private val _findUser = MutableLiveData<Boolean>()
    val findUser: LiveData<Boolean> = _findUser

    fun getData(context: Context){
        val sharedPreferencesManager = SharedPreferencesManager(context)
        val userData = sharedPreferencesManager.loadData("User")
        if (userData.isNotEmpty()){
            val gson = Gson()
            val objectProduct = gson.fromJson(userData, User::class.java)
            _user.value = objectProduct
        }else{
            _findUser.value = false
        }
    }

    fun logout(context: Context){
        val sharedPreferencesManager = SharedPreferencesManager(context)
        sharedPreferencesManager.deleteData("User")
        sharedPreferencesManager.deleteData("Favorites")
        _user.value = User.emptyUser
        _findUser.value = false
    }
}