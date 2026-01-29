package com.ForSomeoneSpeical.app5.app_sketch.domain.model

import com.squareup.moshi.JsonClass

data class NutritionixFoodItem(
    val food_name: String,
    val serving_qty: Double,
    val serving_unit: String,
    val serving_weight_grams: Double,
    val nf_calories: Double,
    val nf_protein: Double,
    val nf_total_fat: Double,
    val nf_total_carbohydrate: Double,
)


data class NutritionixSearchResponse(
    val foods : List<NutritionixFoodItem>
)

data class NutritionixRequest(
    val query : String
)





@JsonClass(generateAdapter = true)
data class Post(
    val id : Int = 0,
    val title : String = "",
    val body : String = ""
)