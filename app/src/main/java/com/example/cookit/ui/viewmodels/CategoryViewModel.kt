package com.example.cookit.ui.viewmodels

import android.util.Log
import androidx.compose.ui.util.trace
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.api.MealAPIService
import com.example.cookit.models.Category
import com.example.cookit.models.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()
    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals = _meals
    private val _hasError = MutableStateFlow(false)
    val hasError = _hasError.asStateFlow()

    init {
        getCategories()
    }


    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _categories.update {
                    MealAPIService.callable.getCategories().categories
                }
                _hasError.update { false }
            } catch (e: Exception) {
                Log.d("trace", "Error:${e.message}")
                _hasError.update { true }
            }


        }


    }

    fun getMeals(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _meals.update {
                    MealAPIService.callable.getMeals(category).meals
                }
                _hasError.update { false }
            } catch (e: Exception) {
                Log.d("trace", "Error:${e.message}")
                _hasError.update { true }
            }

        }

    }



}