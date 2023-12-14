package com.example.skan.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skan.data.dataManager.SharedPreferencesManager
import com.example.skan.domain.entities.User
import com.example.skan.domain.entities.User.Companion.emptyUser
import com.example.skan.domain.useCases.CreateUser
import com.example.skan.domain.useCases.LoginUser
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class WelcomeViewModel: ViewModel() {
    private var createUser = CreateUser()
    private var loginUser = LoginUser()
    private val _errorMsg = MutableStateFlow("")
    val errorMsg: StateFlow<String> = _errorMsg.asStateFlow()
    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved.asStateFlow()
    private val _errorMsg2 = MutableStateFlow("")
    val errorMsg2: StateFlow<String> = _errorMsg2.asStateFlow()
    private val _isLogged = MutableStateFlow(false)
    val isLogged: StateFlow<Boolean> = _isLogged.asStateFlow()

    fun markWelcomeAsShown(context: Context) {
        val sharedPreferencesManager = SharedPreferencesManager(context)
        sharedPreferencesManager.saveData("PREF_WELCOME_SCREEN_DISPLAYED", true.toString())
    }

    fun createUser(name: String, email: String, password: String, context: Context) {
        _isSaved.value = false
        if (validaData(name, email, password)) {
            val user = User(name = name, email = email.lowercase(Locale.ROOT), password = password)
            viewModelScope.launch {
                val result = createUser(user)
                if (result is Int && result > 0) {
                    _isSaved.value = true
                    delay(250)
                    _errorMsg.value = "Cuenta creada, verifique su correo"
                } else if (result is String) {
                    delay(250)
                    _errorMsg.value = result
                } else {
                    delay(250)
                    _errorMsg.value = "Problemas con el servidor, intente nuevamente"
                }
            }
        }
    }

    private fun validaData(name: String, email: String, password: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        if (name.length in 3..80) {
            if (email.matches(emailRegex.toRegex())) {
                if (password.length in 8..15) {
                    return true
                } else {
                    _errorMsg.value = "La contraseña debe ser de al menos 8 caracteres"
                    return false
                }
            } else {
                _errorMsg.value = "Email no valido"
                return false
            }
        } else {
            _errorMsg.value = "Nombre debe ser al menos de 3 caracteres"
            return false
        }
    }

    fun loginUser(email: String, password: String, context: Context){
        _isLogged.value = false
        viewModelScope.launch {
            val result = loginUser(email, password)
            if (result is User && result != emptyUser) {
                val sharedPreferencesManager = SharedPreferencesManager(context)
                val gson = Gson()
                val json = gson.toJson(result)
                sharedPreferencesManager.saveData("User", json)
                _isLogged.value = true
            } else if (result is String) {
                delay(250)
                _errorMsg2.value = result
            } else {
                delay(250)
                _errorMsg2.value = "Problemas con el servidor, intente nuevamente"
            }
        }
    }
}