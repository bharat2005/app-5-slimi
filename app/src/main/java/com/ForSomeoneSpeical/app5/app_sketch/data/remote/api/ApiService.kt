package com.ForSomeoneSpeical.app5.app_sketch.data.remote.api

import com.ForSomeoneSpeical.app5.app_sketch.domain.model.NutritionixFoodItem

interface ApiService {


    suspend fun searchFoodItems(query: String): List<NutritionixFoodItem>

}