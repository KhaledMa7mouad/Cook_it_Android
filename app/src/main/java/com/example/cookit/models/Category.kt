package com.example.cookit.models

import com.example.cookit.constants.Constants
import com.google.gson.annotations.SerializedName

class CategoryRoot(val categories: List<Category>)

class Category (
    @SerializedName(Constants.CATEGORY_NAME)
    val name: String,
    @SerializedName(Constants.CATEGORY_IMAGE)
    val image: String
)
