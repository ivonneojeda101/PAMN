package com.example.skan.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.skan.domain.entities.Product

class SearchViewModel {
    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText
    private val _listProducts = MutableLiveData<List<Product>>()
    val listProducts: LiveData<List<Product>> = _listProducts

    fun updateSearchText(text: String){
        _searchText.value = text
    }

    fun searchProduct(text: String){
        val product1 = Product(name = "N Body Lotion", description = "Reafirma visiblemente la piel y mejora su elasticidad en tan sólo 10 días con Q10 PLUS VITAMIN C Loción Corporal Reafirmante")
        val product2 = Product(name = "N Serum", description = "Piel Radiante Serum Antimanchas está formulado con Aceite de Pétalo de Rosa y Lumicinol, para reducir los principales signos del envejecimiento de la piel y corregir visiblemente las manchas" )
        var product3 = Product(name = "N Crema de Noche", description = "Crema de Noche con coenzima Q10 100% IDÉNTICA a la de la piel reduce arrugas y líneas de expresión en 4 semanas")
        _listProducts.value = listOf(product1, product2, product3)

    }

}