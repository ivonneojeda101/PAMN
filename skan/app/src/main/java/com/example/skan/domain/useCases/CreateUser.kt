package com.example.skan.domain.useCases

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.skan.data.interfaces.UserRepository
import com.example.skan.data.repositories.UserRepositoryImpl
import com.example.skan.domain.entities.Product
import com.example.skan.domain.entities.User

class CreateUser {

    private val userRepository: UserRepository = UserRepositoryImpl()

    suspend operator fun invoke(user: User): Any {
        val response = userRepository.createUser(user)
        Log.println(Log.ASSERT,"Product Response", response.toString())
        return response
    }
}