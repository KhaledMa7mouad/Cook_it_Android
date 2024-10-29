package com.example.cookit.api

import com.example.cookit.constants.Constants
import com.example.cookit.constants.Constants.INGREDIENT_QUERY
import com.example.cookit.constants.Constants.LOOKUP_ENDPOINT
import com.example.cookit.models.CategoryRoot
import com.example.cookit.models.MealRoot
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPICallable {

    @GET(Constants.CATEGORIES_ENDPOINT)
    suspend fun getCategories(): CategoryRoot

    @GET(Constants.FILTER_ENDPOINT)
    suspend fun getMeals(@Query(Constants.CATEGORY_QUERY) category: String): MealRoot

    @GET(LOOKUP_ENDPOINT)
    suspend fun getRecipe(@Query(INGREDIENT_QUERY) mealId: String): MealRoot
}