package com.example.cookit.api

import com.example.cookit.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MealAPIService {

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val callable by lazy {
        retrofit.create(MealAPICallable::class.java)
    }


}