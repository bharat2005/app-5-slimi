package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Meal
import com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components.DateSelector
import com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components.MealDialog
import com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components.MealSection

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserLogScreen(
    viewModel: UserLogViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    var mealType = uiState.currentMealType
    val showDialog = uiState.showDialog

    val context = LocalContext.current




    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }





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
                            onMealDialogOpen = viewModel::onMealDialogOpen,
                            loggedFoodItemsList = loggedFoodItemsList,
                            totalCalories = totalCalories,
                            updateFoodItemQuantity = viewModel::onUpdateFoodItemQuantity,
                            onDeleteFoodItem = viewModel::onDeleteFoodItem,
                            onUpdateCalories = viewModel::onKcalUpdate
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
                viewModel.onMealDialogClose()
            },
        )
    }


    if(uiState.isLoading){
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)).pointerInput(Unit){},
            contentAlignment = Alignment.Center
        )
        {
           CircularProgressIndicator()
        }
    }


}














