package com.example.skan.domain.useCases

import android.util.Log
import com.example.skan.data.interfaces.UserRepository
import com.example.skan.data.repositories.UserRepositoryImpl
import com.example.skan.domain.entities.User

class LoginUser {
    private val userRepository: UserRepository = UserRepositoryImpl()

    suspend operator fun invoke(email: String, password: String): Any {
        val response = userRepository.loginUser(email, password)
        Log.println(Log.ASSERT,"Product Response", response.toString())
        return response
    }
}