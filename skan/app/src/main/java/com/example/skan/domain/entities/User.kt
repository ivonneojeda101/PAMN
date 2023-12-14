package com.example.skan.domain.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("email") var email: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("password") var password: String = "",
    @SerializedName("status") var status: String = "",
) {

    fun isEmpty(): Boolean {
        return this == emptyUser
    }

    companion object {
        val emptyUser = User()
    }
}