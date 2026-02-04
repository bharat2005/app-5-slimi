package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen

import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Meal
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.getFullName
import com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components.DateSelector
import com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components.MealDialog
import com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components.MealSection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserLogScreen(
    viewModel: UserLogViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    var mealType = uiState.currentMealType
    val showDialog = uiState.showDialog





    Scaffold { paddingValues ->



            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //Date Selector
                item {
                    DateSelector(
                        onDateUpdate = viewModel::updateDate,
                        currentDate = uiState.currentDate
                    )
                }


                //Meals Sections (Multi)
                Meal.entries.forEach { meal ->
                    val loggedFoodItemsList = uiState.loggedFoodForDay.filter { it.mealType == meal }
                    val totalCalories = loggedFoodItemsList.sumOf { if (it?.calories != null) it.calories * it.quantity else 0.0 }

                    item {
                        MealSection(
                            meal = meal,
                            updateMealDialog = viewModel::updateMealDialog,
                            loggedFoodItemsList = loggedFoodItemsList,
                            totalCalories = totalCalories,
                            updateFoodItemQuantity = viewModel::onUpdateFoodItemQuantity
                        )
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
            }



    }


    if (showDialog) {
        MealDialog(
            mealType = mealType,
            seletedCategory = uiState.seletedCategory,
            searchedFoodItems = uiState.searchedFoodItems,
            onSearchClick = viewModel::searchFoodItems,
            isSearching = uiState.isSearching,
            onCategoryClick = viewModel::updateSelectedCategory,
            onLogFoodItem = viewModel::onAddFoodItemToLog,
            onDissmissDialog = {
                viewModel.resetUiState()
            },
        )
    }


    if(uiState.isLoading){
        Box(
            modifier = Modifier.fillMaxSize().pointerInput(Unit){},
            contentAlignment = Alignment.Center
        )
        {
           CircularProgressIndicator()
        }
    }


}














