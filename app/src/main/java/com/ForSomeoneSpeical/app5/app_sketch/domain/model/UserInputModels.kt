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
enum class DietCourse(val displayValue : String, val pfcRatio : PFCRatio? ){
    MUSCLE_MAKE_UP("Muscle Body Make Up Course", null),
    EASY_CARB_RESTRICTION_DIET("Easy Carbohydrates Restriction Diet Course", PFCRatio(0.25,0.45,0.3)),
    DIETARY_FIBER("Dietary Fiber Health Support Course", PFCRatio(0.25,0.2,0.55))
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
    val finalPlan : FinalPlan? = null,

)