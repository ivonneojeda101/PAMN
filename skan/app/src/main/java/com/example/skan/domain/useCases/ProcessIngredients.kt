package com.example.skan.domain.useCases

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.util.Log
import com.example.skan.data.dataManager.SharedPreferencesManager
import com.example.skan.data.interfaces.ProductRepository
import com.example.skan.data.repositories.ProductRepositoryImpl
import com.example.skan.domain.entities.Product
import com.google.gson.Gson
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await


class ProcessIngredients {

    private val productRepository: ProductRepository = ProductRepositoryImpl()
    suspend operator fun invoke(image: Bitmap, context: Context): Boolean {
        var finalText = recognizeText(image)
        if (finalText != null){
            val product = analyzeIngredients(finalText)
            if (!product.isEmpty()) {
                val sharedPreferencesManager = SharedPreferencesManager(context)
                val gson = Gson()
                val json = gson.toJson(product)
                sharedPreferencesManager.saveData("Product", json)
                return true
            }
            else {
                return false
            }
        }
        else{
            return false
        }
    }

    private suspend fun recognizeText(image: Bitmap): String? {
        var textResult: String? = null
        val inputImage = InputImage.fromBitmap(image, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val result = recognizer.process(inputImage)
            .addOnSuccessListener {
                textResult = it.text
                Log.println(Log.ASSERT, "Text", it.text)
            }
            .addOnFailureListener { e ->
                Log.e("LibraryML", "Could not find Text: ", e)
            }.await()
        return textResult
    }

    private suspend fun analyzeIngredients(text: String): Product{
        return productRepository.analyzeIngredients(text)
    }
}