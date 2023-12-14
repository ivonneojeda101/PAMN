package com.example.skan.data.dataManager

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    fun saveData(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun loadData(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    fun deleteData(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}
