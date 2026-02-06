package com.ForSomeoneSpeical.app5.app_sketch.domain.repo

import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAResponse
import kotlinx.coroutines.flow.Flow

interface UserLogRepository {
    fun searchFoodItems(query : String) : Flow<Result<USDAResponse>>

    fun addFoodItemToLog(foodItem : USDAFoodItem, dateString : String) : Flow<Result<Unit>>

    fun listenForFoodLogs(dateString : String) : Flow<List<USDAFoodItem>>

    suspend fun updateFoodItemQuantity( docId : String, dateString: String, newQuantity : Int, calories : Double) : Unit

    suspend fun updateCalorie(docId : String, dateString : String, newKcal : Double) : Unit


    suspend fun onDeleteFoodItem(docId : String, dateString : String) : Unit
}