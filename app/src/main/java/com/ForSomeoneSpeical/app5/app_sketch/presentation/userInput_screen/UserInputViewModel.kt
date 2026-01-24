package com.ForSomeoneSpeical.app5.app_sketch.presentation.userInput_screen

import androidx.lifecycle.ViewModel
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.ActivityLevel
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.DietCourse
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.FinalPlan
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Gender
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.HealthProblem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Meal
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.MuscleMakeUpVariant
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.PFC
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.PFCRatio
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.UpdateCourseNVariantEvent
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


    fun updateCourseNVariant(event: UpdateCourseNVariantEvent){
        when(event){
            is UpdateCourseNVariantEvent.UpdateDietCourse -> {
                _uiState.update { it.copy(
                    dietCourse = event.dietCourse,
                    muscleMakeUpVariant = MuscleMakeUpVariant.RECOMMENDED
                ) }
            }
            is UpdateCourseNVariantEvent.UpdateMuscleMakeUpVariant -> {
                _uiState.update { it.copy(
                    muscleMakeUpVariant = event.variant
                ) }
            }
        }
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
        val dietCourse = state.dietCourse ?: return


        //Calculate BMR
        val bmr = if(gender == Gender.MALE){
            (currentWeight * 10) + (height * 6.25) - (age * 5) + 5
        } else
        {
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

        //Calculate Daily Pfc Target in Grams
        val dailyPFCTargetInGrams = when(dietCourse){
            DietCourse.MUSCLE_MAKE_UP -> {
                when(state.muscleMakeUpVariant){
                    MuscleMakeUpVariant.RECOMMENDED -> {
                        val proteinInGrams = (currentWeight * 2.0).roundToInt()
                        val proteinCalories = (proteinInGrams * 4.0).roundToInt()

                        val fatsCalories = (dailyCaloriesIntakeTarget * 0.25).roundToInt()
                        val fatsInGram = (fatsCalories / 9.0).roundToInt()

                        val remainingCalories = dailyCaloriesIntakeTarget - proteinCalories - fatsCalories
                        val carbsInGrams = (remainingCalories / 4.0).roundToInt()

                        mutableMapOf(
                            PFC.PROTEIN to proteinInGrams,
                            PFC.FATS to fatsInGram,
                            PFC.CARBS to carbsInGrams
                        )

                    }
                    MuscleMakeUpVariant.HIGH_IN_PROTEIN -> {
                        val proteinInGrams = (currentWeight * 2.3).roundToInt()
                        val proteinCalories = (proteinInGrams * 4.0).roundToInt()

                        val fatsCalories = (dailyCaloriesIntakeTarget * 0.25).roundToInt()
                        val fatsInGram = (fatsCalories / 9.0).roundToInt()

                        val remainingCalories = dailyCaloriesIntakeTarget - proteinCalories - fatsCalories
                        val carbsInGrams = (remainingCalories / 4.0).roundToInt()

                        mutableMapOf(
                            PFC.PROTEIN to proteinInGrams,
                            PFC.FATS to fatsInGram,
                            PFC.CARBS to carbsInGrams
                        )
                    }
                    MuscleMakeUpVariant.LESS_IN_CARBOHYDRATES -> {
                        val proteinInGrams = (currentWeight * 2.0).roundToInt()
                        val proteinCalories = (proteinInGrams * 4.0).roundToInt()

                        val fatsCalories = (dailyCaloriesIntakeTarget * 0.35).roundToInt()
                        val fatsInGram = (fatsCalories / 9.0).roundToInt()

                        val remainingCalories = dailyCaloriesIntakeTarget - proteinCalories - fatsCalories
                        val carbsInGrams = (remainingCalories / 4.0).roundToInt()

                        mutableMapOf(
                            PFC.PROTEIN to proteinInGrams,
                            PFC.FATS to fatsInGram,
                            PFC.CARBS to carbsInGrams
                        )
                    }
                }
            }
            DietCourse.EASY_CARB_RESTRICTION_DIET -> {
                val pfcRatio = PFCRatio(0.25, 0.45, 0.3)
                mutableMapOf(
                    PFC.PROTEIN to ((dailyCaloriesIntakeTarget * pfcRatio.protein) / 4).roundToInt(),
                    PFC.FATS to ((dailyCaloriesIntakeTarget * pfcRatio.fat) / 9).roundToInt(),
                    PFC.CARBS to ((dailyCaloriesIntakeTarget * pfcRatio.carbs) / 4).roundToInt(),
                )
            }
            DietCourse.DIETARY_FIBER -> {
                val pfcRatio = PFCRatio(0.25, 0.2, 0.55)
                mutableMapOf(
                    PFC.PROTEIN to ((dailyCaloriesIntakeTarget * pfcRatio.protein) / 4).roundToInt(),
                    PFC.FATS to ((dailyCaloriesIntakeTarget * pfcRatio.fat) / 9).roundToInt(),
                    PFC.CARBS to ((dailyCaloriesIntakeTarget * pfcRatio.carbs) / 4).roundToInt(),
                )
            }
        }


        //Add SafeGuards
        val minProteinGrams = (currentWeight * 1.2).roundToInt()
        if(dailyPFCTargetInGrams[PFC.PROTEIN]!! < minProteinGrams){
            dailyPFCTargetInGrams[PFC.PROTEIN] = minProteinGrams
        }
        val minFatsCalories = (dailyCaloriesIntakeTarget * 0.2).roundToInt()
        val minFatsGrams = (minFatsCalories / 9.0).roundToInt()
        if(dailyPFCTargetInGrams[PFC.FATS]!! < minFatsGrams){
            dailyPFCTargetInGrams[PFC.FATS] = minFatsGrams
        }


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