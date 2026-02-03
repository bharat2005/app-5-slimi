package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Meal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun UserLogScreen(
    viewModel: UserLogViewModel = hiltViewModel()
) {
    var mealType by remember { mutableStateOf(Meal.BREAKFAST) }
    var showDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    val filteredList = remember(uiState.seletedCategory, uiState.searchedFoodItems) {
        uiState.searchedFoodItems.foods.filter {
            it.dataType == uiState.seletedCategory.displayName
        }
    }


    Log.d("FoodList", "${filteredList} --- ${uiState.searchedFoodItems}")





    Scaffold {  paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {

            Button(
                onClick = {
                    mealType = Meal.LUNCH
                    showDialog = true
                }
            ) {
                Text("Lunch")
            }


        }
    }


    if(showDialog){
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
                        viewModel.searchFoodItems(searchQuery)
                    }) {Text("Search") }





                    if(uiState.isSearching){
                        CircularProgressIndicator()
                    } else {
                        Column {
                            FoodCategory.entries.forEach { category ->
                                Button(
                                    enabled = uiState.seletedCategory != category,
                                    onClick = {viewModel.updateSelectedCategory(category)}
                                ) { Text(category.displayName)}
                            }
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(280.dp)
                        )
                        {
                            itemsIndexed(filteredList) { index, item ->
                                Surface(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
                                ) {
                                    val foodName = item.description
                                    val foodBrand = if(!item.brandOwner.isNullOrEmpty()) "(${item.brandOwner})" else ""
                                    val servingString = item.servingSize?.let { "(${String.format("%.2f",it)} ${item.servingSizeUnit}) (${item.householdServingFullText})" } ?: "(per 100g)"
                                    val kcalString = item.foodNutrients.find { it.unitName == "KCAL" }?.let { "${it.value} ${it.unitName.toLowerCase()}" }

                                    Text("${foodName} ${foodBrand} ${servingString} ${kcalString}")
                                }
                            }

                        }
                    }
                }

            },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                }) { Text("Confirm")}

            }
        )
    }

}














