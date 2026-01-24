package com.ForSomeoneSpeical.app5.app_sketch.domain.model



enum class Gender { MALE, FEMALE }
enum class ActivityLevel(val factor : Double) {
    SEDENTARY(1.2),
    LIGHTLY_ACTIVE(1.375),
    MODERATELY_ACTIVE(1.55),
    VERY_ACTIVE(1.725)
}
enum class HealthProblem{
    NONE, STRESS, POOR_SLEEP, TIRED_EASILY, SKIN_PROBLEM, LIVER_FUNCTION,
    BLOOD_PRESSURE, BLOOD_SUGAR, CHOLESTEROL, BONE_HEALTH, AGING,
    CONSTIPATION, SWELLING, COLD, ANEMIA, TIRED_EYES
}

enum class PFC{
    PROTEIN,FATS, CARBS
}
data class PFCRatio(val protein : Double, val fat : Double, val carbs : Double)
enum class DietCourse(val displayValue : String ){
    MUSCLE_MAKE_UP("Muscle Body Make Up Course"),
    EASY_CARB_RESTRICTION_DIET("Easy Carbohydrates Restriction Diet Course"),
    DIETARY_FIBER("Dietary Fiber Health Support Course")
}

enum class MuscleMakeUpVariant(val displayValue : String){
    RECOMMENDED("Recommended"),
    HIGH_IN_PROTEIN("High in Protein"),
    LESS_IN_CARBOHYDRATES("Less in Carbs")
}
enum class Meal{
    BREAKFAST, LUNCH, DINNER, SNACK
}
data class FinalPlan(
    val dailyCaloriesIntakeTarget : Int,
    val mealTargets : Map<Meal, Int>,

    val dailyCaloriesBurnTarget : Int,

    val dailyPFCTargetInGrams : Map<PFC, Int>
)


sealed class UpdateCourseNVariantEvent{
    data class UpdateDietCourse(val dietCourse : DietCourse) : UpdateCourseNVariantEvent()
    data class UpdateMuscleMakeUpVariant(val variant : MuscleMakeUpVariant) : UpdateCourseNVariantEvent()

}

data class UserInputUiState(
    val gender :Gender? = null,
    val age : String = "",
    val height : String = "",
    val currentWeight : String = "",
    val targetWeight : String = "",
    val pace : String = "",
    val activityLevel : ActivityLevel? = null,
    val healthProblem : HealthProblem? = null,
    val dietCourse : DietCourse? = null,
    val muscleMakeUpVariant : MuscleMakeUpVariant = MuscleMakeUpVariant.RECOMMENDED,
    val finalPlan : FinalPlan? = null,

    )