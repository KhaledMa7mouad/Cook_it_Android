package com.example.cookit.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookit.api.MealAPIService
import com.example.cookit.models.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel(){

    private val _meals = MutableStateFlow(Meal())
    val meals = _meals


    private val _hasErorr = MutableStateFlow(false)
    val hasError = _hasErorr.asStateFlow()

    fun getRecipe(id : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _meals.update {
                    MealAPIService.callable.getRecipe(id.meals[0])
                }
                _hasErorr.update { false }
            } catch (e: Exception) {
                Log.d("trace", "Error:${e.message}")
                _hasErorr.update { true }
            }

        }

    }

}