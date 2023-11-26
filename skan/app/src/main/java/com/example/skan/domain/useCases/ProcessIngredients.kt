package com.example.skan.domain.useCases

import android.graphics.Bitmap
import android.util.Log
import com.example.skan.data.dataManager.ProductProvider
import com.example.skan.data.interfaces.ProductRepository
import com.example.skan.data.repositories.ProductRepositoryImpl
import com.example.skan.domain.entities.Ingredient
import com.example.skan.domain.entities.Product
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await

class ProcessIngredients {

    private val productRepository: ProductRepository = ProductRepositoryImpl()

    suspend operator fun invoke(image: Bitmap): Boolean {
        var finalText = recognizeText(image)
        if (finalText != null){
            val ingredients = getIngredients(finalText)
            if (ingredients.isNotEmpty()){
                val product = analyzeProduct(ingredients)
                if (!product.isEmpty()) { ProductProvider.product = product}
                return !product.isEmpty()
            }
            return false
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

    private suspend fun getIngredients(text: String): List<Ingredient>{
        return productRepository.getIngredients(text)
    }

    private suspend fun analyzeProduct(ingredients: List<Ingredient>): Product {
        return productRepository.analyzeProduct(ingredients)
    }
}