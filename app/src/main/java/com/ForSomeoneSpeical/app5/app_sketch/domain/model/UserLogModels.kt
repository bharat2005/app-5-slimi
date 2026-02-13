package com.ForSomeoneSpeical.app5.app_sketch.domain.model

import androidx.compose.material3.Text

data class USDAFoodItem(
    val fdcId : Int = 0,
    val dataType : String =  "Branded",
    val description : String =  "BROCCOLI",
    val foodCode : String =  "string",
    val brandOwner : String? = null,
    val foodNutrients : List<FoodNutrient> = emptyList(),
    val servingSize : Double? = null,
    val servingSizeUnit : String? = null,
    val householdServingFullText : String? = null,

    //custome fields
    val mealType : Meal? = null,
    val calories : Double? = null,
    val quantity : Int = 1,

    val docId : String = "",

)

fun USDAFoodItem.getFullName() : String {
    val item = this
    val foodName = item.description
    val foodBrand = if(!item.brandOwner.isNullOrEmpty()) "(${item.brandOwner})" else ""
    val servingString = item.servingSize?.let { "(${String.format("%.2f",it)} ${item.servingSizeUnit}) (${item.householdServingFullText})" } ?: "(per 100g)"

    return "${foodName} ${foodBrand} ${servingString}"
}

fun USDAFoodItem.getCalories() : Double? {
    return this.foodNutrients.find { it.unitName == "KCAL" }?.let { it.value }
}




data class USDAResponse(
    val foods : List<USDAFoodItem>
)


data class FoodNutrient(
    val nutrientName: String =  "",
    val value: Double = 0.0,
    val unitName: String = "",
)







data class LoggedExercise(
    val docId : String = "",
    val name : String = "",
    val durationMinutes : Int? = 0,
    val caloriesBurned : Double = 0.0,
    val baseCaloriesBurnPerMinute : Double = 0.0
)


data class Exercise(
    val name : String = "",
    val metValue : Double = 0.0,
)

data class ExerciseUIItem(
    val name : String = "",
    val burnCalories : Double = 0.0,
    val perMinutes : Int = 10,
)


enum class ExerciseEditType {
    CALORIES,
    MINUTES
}


enum class Physiological{
    YES,
    NO
}

enum class Message{
    YES,
    NO
}

enum class Feeling{
    GOOD,
    NORMAL,
    BAD
}

data class DailyVitalsDTO(
    val bodyWeightKg : Double? = null,
    val bodyFatPercentage : Double? = null,

    val mensuration : Physiological? = null,
    val bowelMomentum : Message? = null,
    val mood : Feeling? = null,

    val chestCm : Double? = null,
    val waistCm : Double? = null,
    val hipsCm : Double? = null,
    val forearmsCm : Double? = null,
    val calfCm : Double? = null,
)

data class DailyVitals(
    val bodyWeight : Double? = null,
    val bodyFat : Double? = null,

    val physiological : Physiological? = null,
    val message : Message? = null,
    val feeling : Feeling? = null,

    val chest : Double? = null,
    val waist : Double? = null,
    val hips : Double? = null,
    val forearms : Double? = null,
    val calf : Double? = null,
)