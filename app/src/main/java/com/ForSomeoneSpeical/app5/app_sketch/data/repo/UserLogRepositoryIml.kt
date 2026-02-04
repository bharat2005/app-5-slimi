package com.ForSomeoneSpeical.app5.app_sketch.data.repo

import android.util.Log
import com.ForSomeoneSpeical.app5.app_sketch.data.remote.api.ApiService
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAResponse
import com.ForSomeoneSpeical.app5.app_sketch.domain.repo.UserLogRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserLogRepositoryIml @Inject constructor(
    private val api : ApiService,
    private val firestore : FirebaseFirestore
): UserLogRepository {

    val userUid = "user1"

    override fun searchFoodItems(query: String): Flow<Result<USDAResponse>> = flow {
        try {
            val foodItems = api.getFoodItems(query)
            emit(Result.success(foodItems))
        } catch (e: Exception) {
            Log.d("Error", e.message ?: "")
            emit(Result.failure(e))
        }
    }


    override fun addFoodItemToLog(foodItem: USDAFoodItem, dateString : String): Flow<Result<Unit>> = flow {
        try {
            firestore
                .collection("users")
                .document(userUid)
                .collection("dailyLogs")
                .document(dateString)
                .collection("foodItems").add(foodItem)

            emit(Result.success(Unit))

        } catch (e : Exception){
            emit(Result.failure(e))
        }
    }


}