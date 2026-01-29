package com.ForSomeoneSpeical.app5.app_sketch.domain.repo

import com.ForSomeoneSpeical.app5.app_sketch.domain.model.NutritionixFoodItem
import kotlinx.coroutines.flow.Flow

interface UserLogRepository {
    fun searchFoodItems(query : String) : Flow<Result<List<NutritionixFoodItem>>>
}