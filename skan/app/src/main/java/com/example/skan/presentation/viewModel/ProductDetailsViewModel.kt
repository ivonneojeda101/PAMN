package com.example.skan.presentation.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.example.skan.data.dataManager.SharedPreferencesManager
import com.example.skan.domain.entities.Favorite
import com.example.skan.domain.entities.Product
import com.example.skan.domain.entities.Review
import com.example.skan.domain.entities.User
import com.example.skan.domain.useCases.CreateReview
import com.example.skan.domain.useCases.GetReviews
import com.example.skan.domain.useCases.ManageFavorites
import kotlinx.coroutines.launch

class ProductDetailsViewModel: ViewModel() {
    private var createReview = CreateReview()
    private var reviewProvider = GetReviews()
    private val _idUser = MutableLiveData<Int>()
    val idUser: LiveData<Int> = _idUser
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product
    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: MutableLiveData<List<Review>> = _reviews
    private val _saveProduct = MutableLiveData<Boolean>()
    val saveProduct: LiveData<Boolean> = _saveProduct
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _reviewTitle = MutableLiveData<String>()
    val reviewTitle: LiveData<String> = _reviewTitle
    private val _reviewText = MutableLiveData<String>()
    val reviewText: LiveData<String> = _reviewText
    private val _selectedStars = MutableLiveData<Int>()
    val selectedStars: LiveData<Int> = _selectedStars
    private val _showReviewScreen = MutableLiveData<Boolean>()
    val showReviewScreen: LiveData<Boolean> = _showReviewScreen

    fun getData(context: Context){
        val sharedPreferencesManager = SharedPreferencesManager(context)
        val productData = sharedPreferencesManager.loadData("Product")
        if (productData.isNotEmpty()){
            val gson = Gson()
            val objectProduct = gson.fromJson(productData, Product::class.java)
            _product.value = objectProduct
            objectProduct.id?.let { validateFavorite(context, it) }
            getUserId(context)
            getReviews()
        }
    }

    fun updateSave(save: Boolean){
        _saveProduct.value = save
    }

    fun updateReviewTitle(title: String) {
        _reviewTitle.postValue(title)
    }

    fun updateReviewText(text: String) {
        _reviewText.postValue(text)
    }

    fun updateStars(stars: Int) {
        _selectedStars.postValue(stars)
    }

    fun updateShowReviewScreen(save: Boolean){
        _showReviewScreen.value = save
    }

    private fun validateFavorite(context: Context, id: Int){
        val managerFavorites = ManageFavorites(context)
        _isFavorite.value = managerFavorites.isFavorite(id)
    }

    fun addFavorite(context: Context){
        val managerFavorites = ManageFavorites(context)
        val productI = product.value
        managerFavorites.addFavorite(Favorite(id = productI?.id!!, name = productI.name!!, description = productI.description!!))
        _isFavorite.value = true
    }

    private fun getUserId(context: Context){
        val sharedPreferencesManager = SharedPreferencesManager(context)
        val userData = sharedPreferencesManager.loadData("User")
        if (userData.isNotEmpty()){
            val gson = Gson()
            val objectUser = gson.fromJson(userData, User::class.java)
            _idUser.value = objectUser.id
        }else{
            _idUser.value = 0
        }
    }

    private fun getReviews(){
        if (_product.value != null && _product.value!!.id != null && _product.value!!.id!! > 0) {
            viewModelScope.launch {
                var reviews = _product.value!!.id?.let { reviewProvider.getReviews(it) }
                if (reviews != null && reviews.isNotEmpty()) {
                    _reviews.postValue(reviews!!)
                }
            }
        }
    }

    fun createReview(reviewTitle: String, reviewText: String, selectedStars: Int) {
        if (_product.value != null && _product.value!!.id != null && _product.value!!.id!! > 0 && _idUser.value!! > 0) {
            var productId = _product.value!!.id
            var review = Review(0, reviewTitle, reviewText, selectedStars, _idUser.value, productId)
            viewModelScope.launch {
                var result = createReview(review)
                if (result == true) {
                    _showReviewScreen.value = false
                    _reviewText.value = ""
                    _reviewTitle.value = ""
                    _selectedStars.value = 0
                }
            }
        }
    }
}