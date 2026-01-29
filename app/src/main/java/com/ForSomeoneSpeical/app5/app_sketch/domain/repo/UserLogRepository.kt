package com.ForSomeoneSpeical.app5.app_sketch.domain.repo

import com.ForSomeoneSpeical.app5.app_sketch.domain.model.NutritionixFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.NutritionixSearchResponse
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface UserLogRepository {

    suspend fun getPost() : Post

}