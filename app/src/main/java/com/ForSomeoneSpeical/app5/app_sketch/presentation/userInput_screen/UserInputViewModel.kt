package com.ForSomeoneSpeical.app5.app_sketch.presentation.userInput_screen

import androidx.lifecycle.ViewModel
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.ActivityLevel
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.DietCourse
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.FinalPlan
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Gender
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.HealthProblem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Meal
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.PFC
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.UserInputUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.math.roundToInt


@HiltViewModel
class UserInputViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(UserInputUiState())
    val uiState = _uiState.asStateFlow()

    fun updateState(state: UserInputUiState) {
        _uiState.update { state }
    }


    fun calculateFinalPlan() {
        val state = uiState.value
        val gender = state.gender ?: return
        val age = state.age.toDoubleOrNull() ?: return
        val height = state.height.toDoubleOrNull() ?: return
        val currentWeight = state.currentWeight.toDoubleOrNull() ?: return
        val targetWeight = state.targetWeight.toDoubleOrNull() ?: return
        val pace = state.pace.toDoubleOrNull() ?: return
        val activityLevel = state.activityLevel ?: return
        val healthProblem = state.healthProblem ?: return
        val dietCourse = state.dietCourse ?: return


        //Calculate BMR
        val bmr = if(gender == Gender.MALE){
            (currentWeight * 10) + (height * 6.25) - (age * 5) + 5
        } else {
            (currentWeight * 10) + (height * 6.25) - (age * 5) - 161
        }

        //Calculate TDEE
        val tdee = bmr * activityLevel.factor


        //Calculate Daily Calories Intake Target
        val dailyAdjustment = ((pace * 7700) / 30.4)
        val signedAdjustment = if(targetWeight > currentWeight) dailyAdjustment else -dailyAdjustment
        val dailyCaloriesIntakeTarget = (tdee + signedAdjustment).roundToInt()

        //Calculate MealTargets
        val mealTargets = mapOf(
            Meal.BREAKFAST  to (dailyCaloriesIntakeTarget * 0.25).roundToInt(),
            Meal.LUNCH      to (dailyCaloriesIntakeTarget * 0.35).roundToInt(),
            Meal.SNACK      to (dailyCaloriesIntakeTarget * 0.10).roundToInt(),
            Meal.DINNER     to (dailyCaloriesIntakeTarget * 0.30).roundToInt(),
        )

        //Calculate Daily Calories Burn Target
        val dailyCaloriesBurnTarget = (tdee - bmr).roundToInt()

        //Calculate PFC Target in Grams
        val proteinInGrams : Int
        val fatsInGrams : Int
        val carbsInGrams : Int

        if(dietCourse == DietCourse.MUSCLE_MAKE_UP){
            proteinInGrams = (currentWeight * 2.0).roundToInt()
            val proteinCalories = (proteinInGrams * 4.0).roundToInt()


            val fatCalories = (dailyCaloriesIntakeTarget * 0.25).roundToInt()
            fatsInGrams = (fatCalories / 9.0).roundToInt()

            val remainingCalories = dailyCaloriesIntakeTarget - proteinCalories - fatCalories
            carbsInGrams = (remainingCalories / 4.0).roundToInt()
        } else
        {
            proteinInGrams = ((dailyCaloriesIntakeTarget * dietCourse.pfcRatio!!.protein) / 4).roundToInt()
            fatsInGrams = ((dailyCaloriesIntakeTarget * dietCourse.pfcRatio.fat) / 9).roundToInt()
            carbsInGrams = ((dailyCaloriesIntakeTarget * dietCourse.pfcRatio.carbs) / 4).roundToInt()
        }

        val dailyPFCTargetInGrams = mapOf(
            PFC.PROTEIN to proteinInGrams,
            PFC.FATS  to fatsInGrams,
            PFC.CARBS to carbsInGrams,
        )

        _uiState.update {
            it.copy(
                finalPlan =
                    FinalPlan(
                        dailyCaloriesIntakeTarget,
                        mealTargets,
                        dailyCaloriesBurnTarget,
                        dailyPFCTargetInGrams,
                        )
            )
        }
    }

}