package com.ForSomeoneSpeical.app5.app_sketch.domain.model

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
)


data class USDAResponse(
    val foods : List<USDAFoodItem>
)


data class FoodNutrient(
    val nutrientName: String =  "",
    val value: Double = 0.0,
    val unitName: String = "",
)
