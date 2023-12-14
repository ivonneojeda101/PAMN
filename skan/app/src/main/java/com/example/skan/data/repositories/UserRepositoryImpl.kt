package com.example.skan.data.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.skan.data.interfaces.UserRepository
import com.example.skan.data.network.AnalyzerAPIService
import com.example.skan.domain.entities.User

class UserRepositoryImpl: UserRepository {
    private val api = AnalyzerAPIService()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun createUser(user: User): Any {
        return api.createUser(user)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun loginUser(email: String, password: String): Any {
        return api.loginUser(email, password)
    }
}