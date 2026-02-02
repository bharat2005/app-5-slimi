package com.ForSomeoneSpeical.app5.app_sketch.domain.model

data class USDAFoodItem(
    val fdcId : Int = 0,
    val dataType : String =  "Branded",
    val description : String =  "BROCCOLI",
    val foodCode : String =  "string",
)


data class USDAResponse(
    val foods : List<USDAFoodItem>
)


