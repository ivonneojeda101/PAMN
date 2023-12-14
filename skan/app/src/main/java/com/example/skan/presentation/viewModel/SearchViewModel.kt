package com.example.skan.presentation.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skan.domain.entities.Favorite
import com.example.skan.domain.useCases.GetProduct
import com.example.skan.domain.useCases.SearchKeyword
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText
    private val _listProducts = MutableLiveData<List<Favorite>>()
    val listProducts: LiveData<List<Favorite>> = _listProducts
    private var searchKeyword = SearchKeyword()
    private val _showProduct = MutableStateFlow<Boolean>(false)
    val showProduct: StateFlow<Boolean> = _showProduct.asStateFlow()
    private val getProduct = GetProduct()
    fun updateSearchText(text: String){
        _searchText.value = text
    }

    fun searchProduct(keyword: String){
        viewModelScope.launch {
            val result = searchKeyword(keyword)
            _listProducts.postValue(result)
        }
    }

    fun showProduct(idProduct: Int, context: Context){
        viewModelScope.launch {
            val result = getProduct(idProduct, context)
            if (result) {
                delay(250)
                _showProduct.value = true
            }
        }
    }
}