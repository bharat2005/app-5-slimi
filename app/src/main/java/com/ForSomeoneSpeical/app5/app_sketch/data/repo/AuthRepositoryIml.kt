package com.ForSomeoneSpeical.app5.app_sketch.data.repo

import com.ForSomeoneSpeical.app5.app_sketch.domain.model.FinalPlan
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Meal
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.PFC
import com.ForSomeoneSpeical.app5.app_sketch.domain.repo.AuthReporistory
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryIml @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : AuthReporistory{

    override fun saveUserData(finalPlan: FinalPlan): Flow<Result<Unit>> = flow{
        try {
            val myFinalPlan = mapOf(
                "dailyCaloriesIntakeTarget" to finalPlan.dailyCaloriesIntakeTarget,
                "dailyCaloriesBurnTarget" to finalPlan.dailyCaloriesBurnTarget,
                "mealTargets" to mapOf(
                    "BreakFast" to finalPlan.mealTargets[Meal.BREAKFAST],
                    "Lunch" to finalPlan.mealTargets[Meal.LUNCH],
                    "Snacks" to finalPlan.mealTargets[Meal.SNACK],
                    "Dinner" to finalPlan.mealTargets[Meal.DINNER],
                ),
                "dailyPFCTargetInGrams" to mapOf(
                    "Protein" to finalPlan.dailyPFCTargetInGrams[PFC.PROTEIN],
                    "Fats" to finalPlan.dailyPFCTargetInGrams[PFC.FATS],
                    "Carbs" to finalPlan.dailyPFCTargetInGrams[PFC.CARBS],
                )
            )
            firebaseFirestore.collection("users")
                .document("user1")
                .set(myFinalPlan)
                .await()

            emit(Result.success(Unit))

        }catch (e : Exception){
            emit(Result.failure(e))
        }

    }
}