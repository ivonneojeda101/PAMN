package com.example.skan.domain.useCases

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.skan.data.dataManager.SharedPreferencesManager
import com.example.skan.data.interfaces.ProductRepository
import com.example.skan.data.repositories.ProductRepositoryImpl
import com.example.skan.domain.entities.Product
import com.google.gson.Gson
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.tasks.await

class ProcessBarCode {

    private val productRepository: ProductRepository = ProductRepositoryImpl()

    suspend operator fun invoke(image: Bitmap, context: Context): Boolean {
        var finalBarCode = scanBarcodes(image)
        if (finalBarCode != null) {
            val product = getProduct(finalBarCode)
            if (!product.isEmpty()) {
                val sharedPreferencesManager = SharedPreferencesManager(context)
                val gson = Gson()
                val json = gson.toJson(product)
                sharedPreferencesManager.saveData("Product", json)
                return true
            }
            return false
        }
        else {
            return false
        }
    }

    private suspend fun scanBarcodes(image: Bitmap): String? {
        var barCodeString: String? = null
        val inputImage = InputImage.fromBitmap(image, 0)
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_EAN_8,
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_UPC_A,
                Barcode.FORMAT_UPC_E)
            .build()
        val scanner = BarcodeScanning.getClient()
        val result = scanner.process(inputImage)
            .addOnFailureListener {e ->
                Log.e("LibraryML", "Could not find barcode: ", e)
            }.await()
        if (result.size>0 ){
            barCodeString = result.get(0).rawValue.toString()
        }
        return barCodeString
    }

    private suspend fun getProduct(barcode: String): Product{
        return productRepository.getProduct(barcode)
    }
}
