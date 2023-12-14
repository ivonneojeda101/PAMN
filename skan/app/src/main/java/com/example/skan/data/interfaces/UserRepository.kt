package com.example.skan.data.interfaces

import com.example.skan.domain.entities.User

interface UserRepository {
    suspend fun createUser(user: User): Any

    suspend fun loginUser(email: String, password: String): Any
}