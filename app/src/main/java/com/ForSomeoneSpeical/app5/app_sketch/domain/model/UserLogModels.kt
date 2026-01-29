package com.ForSomeoneSpeical.app5.app_sketch.domain.model

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