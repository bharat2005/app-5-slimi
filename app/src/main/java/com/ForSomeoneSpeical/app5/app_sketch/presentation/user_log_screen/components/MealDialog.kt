package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Meal
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAResponse
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.getFullName
import com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.FoodCategory

@Composable
fun MealDialog(
    mealType : Meal,
    seletedCategory: FoodCategory,
    searchedFoodItems : USDAResponse,
    onSearchClick : (String) -> Unit,
    isSearching : Boolean,
    onCategoryClick : (FoodCategory) -> Unit,
    onLogFoodItem : (USDAFoodItem) -> Unit,
    onDissmissDialog : () -> Unit,

    ) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredList = remember(seletedCategory, searchedFoodItems) {
        searchedFoodItems.foods.filter {
            it.dataType == seletedCategory.displayName
        }
    }

        AlertDialog(
            onDismissRequest = {},
            title = {Text(mealType.name)},
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = searchQuery,
                        onValueChange = {searchQuery = it}
                    )

                    Button(onClick = {
                        onSearchClick(searchQuery)
                    }) {Text("Search") }





                    if(isSearching){
                        CircularProgressIndicator()
                    } else {
                        Column {
                            FoodCategory.entries.forEach { category ->
                                Button(
                                    enabled = seletedCategory != category,
                                    onClick = {onCategoryClick(category)}
                                ) { Text(category.displayName)}
                            }
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(280.dp)
                        )
                        {
                            itemsIndexed(filteredList) { index, item ->
                                Surface(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                                    onClick = {
                                        onLogFoodItem(item)
                                    }
                                ) {
                                    Text("${item.getFullName()}")
                                }
                            }

                        }
                    }
                }

            },
            confirmButton = {
                Button(onClick = onDissmissDialog) { Text("Confirm")}

            }
        )
    }

