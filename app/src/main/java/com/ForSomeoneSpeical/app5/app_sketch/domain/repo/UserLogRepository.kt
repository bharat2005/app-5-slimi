package com.ForSomeoneSpeical.app5.app_sketch.domain.repo

import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAResponse
import kotlinx.coroutines.flow.Flow

interface UserLogRepository {
    fun searchFoodItems(query : String) : Flow<Result<USDAResponse>>

    fun addFoodItemToLog(foodItem : USDAFoodItem, dateString : String) : Flow<Result<Unit>>

    fun listenForFoodLogs(dateString : String) : Flow<List<USDAFoodItem>>
}