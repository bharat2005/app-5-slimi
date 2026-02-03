package com.ForSomeoneSpeical.app5.app_sketch.domain.model

data class USDAFoodItem(
    val fdcId : Int = 0,
    val dataType : String =  "Branded",
    val description : String =  "BROCCOLI",
    val foodCode : String =  "string",
    val brandOwner : String? = null,
    val foodNutrients : List<FoodNutrient> = emptyList()
)


data class USDAResponse(
    val foods : List<USDAFoodItem>
)




data class FoodNutrient(
    val name: String =  "Iron, Fe",
    val amount: Double =  0.53,
    val unitName: String = "mg",
)
