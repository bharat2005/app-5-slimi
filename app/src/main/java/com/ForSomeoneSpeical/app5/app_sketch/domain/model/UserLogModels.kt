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

    val mealType : Meal? = null,

)

fun USDAFoodItem.getFullName() : String {
    val item = this
    val foodName = item.description
    val foodBrand = if(!item.brandOwner.isNullOrEmpty()) "(${item.brandOwner})" else ""
    val servingString = item.servingSize?.let { "(${String.format("%.2f",it)} ${item.servingSizeUnit}) (${item.householdServingFullText})" } ?: "(per 100g)"
    val kcalString = item.foodNutrients.find { it.unitName == "KCAL" }?.let { "${it.value} ${it.unitName.toLowerCase()}" }

    return "${foodName} ${foodBrand} ${servingString} ${kcalString}"

}




data class USDAResponse(
    val foods : List<USDAFoodItem>
)


data class FoodNutrient(
    val nutrientName: String =  "",
    val value: Double = 0.0,
    val unitName: String = "",
)
