package com.ForSomeoneSpeical.app5.app_sketch.data.repo

import android.util.Log
import com.ForSomeoneSpeical.app5.app_sketch.data.remote.api.ApiService
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.DailyVitals
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.DailyVitalsDTO
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.LoggedExercise
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAResponse
import com.ForSomeoneSpeical.app5.app_sketch.domain.repo.UserLogRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
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

    override fun listenForFoodLogs(dateString: String): Flow<List<USDAFoodItem>> {
        return firestore
            .collection("users")
            .document(userUid)
            .collection("dailyLogs")
            .document(dateString)
            .collection("foodItems")
            .snapshots()
            .map { querySnapShot ->
                querySnapShot.mapNotNull { docSnapShot ->
                    docSnapShot.toObject<USDAFoodItem>(USDAFoodItem::class.java)?.copy(docId = docSnapShot.id)
                }
            }
    }

    override suspend fun updateFoodItemQuantity( docId : String, dateString: String, newQuantity: Int, calories : Double) {
        firestore
            .collection("users")
            .document(userUid)
            .collection("dailyLogs")
            .document(dateString)
            .collection("foodItems")
            .document(docId)
            .update("quantity", newQuantity, "calories", calories)
            .await()

    }

    override suspend fun updateCalorie(docId: String, dateString: String, newKcal: Double) {
        firestore.collection("users").document(userUid)
            .collection("dailyLogs")
            .document(dateString)
            .collection("foodItems")
            .document(docId)
            .update("calories", newKcal, "quantity" , 1)
            .await()
    }

    override suspend fun onDeleteFoodItem(docId: String, dateString: String) {
        firestore
            .collection("users")
            .document(userUid)
            .collection("dailyLogs")
            .document(dateString)
            .collection("foodItems")
            .document(docId)
            .delete()
            .await()
    }

    override fun addExerciseItemToLog(exercise: LoggedExercise, dateString: String): Flow<Result<Unit>> = flow {
        try {
            firestore.collection("users")
                .document(userUid)
                .collection("dailyLogs")
                .document(dateString)
                .collection("exerciseItems")
                .add(exercise)
                .await()

            emit(Result.success(Unit))
        }catch (e : Exception){
            emit(Result.failure(e))
        }
    }

    override fun listenForExerciseLogs(dateString: String): Flow<List<LoggedExercise>> {
        return firestore
            .collection("users")
            .document(userUid)
            .collection("dailyLogs")
            .document(dateString)
            .collection("exerciseItems")
            .snapshots()
            .map { querySnapshots ->
                querySnapshots.mapNotNull{ docSnapShot ->
                    docSnapShot.toObject(LoggedExercise::class.java)?.copy(docId = docSnapShot.id)
                }
            }
    }


    override suspend fun onDeleteExerciseItem(docId: String, dateString: String) {
        firestore
            .collection("users")
            .document(userUid)
            .collection("dailyLogs")
            .document(dateString)
            .collection("exerciseItems")
            .document(docId)
            .delete().await()
    }

    override suspend fun onUpdateCaloriesBurned(
        docId: String,
        dateString: String,
        newCaloriesBurned: Double,
        newMinutes: Int?
    ) {
        firestore.collection("users")
            .document(userUid)
            .collection("dailyLogs")
            .document(dateString)
            .collection("exerciseItems")
            .document(docId)
            .update(
                mapOf(
                    "caloriesBurned" to newCaloriesBurned,
                    "durationMinutes" to newMinutes
                )
            ).await()
    }


    override fun listenForVitalsLog(dateString: String): Flow<DailyVitals> {
        return firestore
            .collection("users")
            .document(userUid)
            .collection("dailyLogs")
            .document(dateString)
            .snapshots()
            .map { documentSnapshot ->
                documentSnapshot.toObject(DailyVitalsDTO::class.java)?.let {
                        DailyVitals(
                            bodyWeight = it.bodyWeightKg,
                            bodyFat = it.bodyFatPercentage,
                            physiological = it.mensuration,
                            message = it.bowelMomentum,
                            feeling = it.mood,
                            chest = it.chestCm,
                            waist = it.waistCm,
                            hips = it.hipsCm,
                            forearms = it.forearmsCm,
                            calf = it.calfCm
                        )
                    } ?: DailyVitals()
                }

    }



}