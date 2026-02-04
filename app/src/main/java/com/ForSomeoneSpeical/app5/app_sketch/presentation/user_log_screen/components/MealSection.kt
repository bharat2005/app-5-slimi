package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Meal
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.getFullName

@Composable
fun MealSection(
    meal : Meal,
    updateMealDialog : (Meal, Boolean) -> Unit,
    loggedFoodItemsList : List<USDAFoodItem>,
    totalCalories : Double,
    updateFoodItemQuantity : (USDAFoodItem, Int) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        //Meal title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.CenterHorizontally)
        )
        {
            Text(meal.name)
            Text(totalCalories.toString())
        }
        Spacer(modifier = Modifier.height(12.dp))


        //Logged Food Items (Multi)
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        )
        {

            loggedFoodItemsList.forEach{ foodItem ->
                //Logged Food Item (Single)
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color.Gray),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {

                    //Food Name
                    Column(
                        modifier = Modifier.fillMaxWidth(0.4f).fillMaxHeight(),
                    )
                    {
                        Text(foodItem.getFullName())
                    }

                    //Food Update Quantity Actions
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        IconButton(
                            onClick = {
                                if(foodItem.quantity > 1){
                                    updateFoodItemQuantity(foodItem, -1)
                                }
                            }
                        ) { Icon(Icons.Default.Close, null)}

                        Text(foodItem.quantity.toString())

                        IconButton(
                            onClick = {
                                updateFoodItemQuantity(foodItem, +1)
                            }
                        ) { Icon(Icons.Default.Add, null)}
                    }

                    //Delete Button
                    IconButton(
                        onClick = {}
                    )
                    {
                        Icon(Icons.Default.Delete, null, tint = Color.Red)
                    }
                }
            }

        }



        //Add Food Button
        Button(
            onClick = {
                updateMealDialog(meal, true)
            },
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text(meal.name)
        }


    }


}