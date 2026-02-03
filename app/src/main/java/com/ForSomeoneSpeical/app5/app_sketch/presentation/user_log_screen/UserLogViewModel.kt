package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen

import android.app.Dialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Meal
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAResponse
import com.ForSomeoneSpeical.app5.app_sketch.domain.repo.UserLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject



enum class FoodCategory(val displayName : String) {
    FOUNDATION("Foundation"),
    SR_LEGACY("SR Legacy"),
    SURVEY_FNDDS("Survey (FNDDS)"),
    BRANDED("Branded"),

}
data class UserLogState @RequiresApi(Build.VERSION_CODES.O) constructor(
    val currentDate : LocalDate = LocalDate.now(),
    val showDialog : Boolean = false,
    val currentMealType : Meal = Meal.BREAKFAST,
    val isSearching : Boolean = false,
    val searchedFoodItems : USDAResponse = USDAResponse(emptyList()),
    val seletedCategory : FoodCategory = FoodCategory.FOUNDATION
)



@HiltViewModel
class UserLogViewModel @Inject constructor(
    private val userLogRepository: UserLogRepository
) : ViewModel() {



    @RequiresApi(Build.VERSION_CODES.O)
    private val _uiState = MutableStateFlow(UserLogState())
    @RequiresApi(Build.VERSION_CODES.O)
    val uiState = _uiState.asStateFlow()


    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSelectedCategory(category: FoodCategory){
        _uiState.update { it.copy(seletedCategory = category) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun resetUiState(){
        _uiState.update { UserLogState() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateMealDialog(meal : Meal, showMealDialog : Boolean){
        _uiState.update { it.copy(currentMealType = meal, showDialog = showMealDialog) }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun searchFoodItems(query : String){
        _uiState.update { it.copy(isSearching = true) }
        viewModelScope.launch {
            userLogRepository.searchFoodItems(query).collect { result ->
                result.fold(
                    onSuccess = { foodItemsList ->
                   _uiState.update { it.copy(isSearching = false, searchedFoodItems = foodItemsList ) }
                    },
                    onFailure = {
                    _uiState.update { it.copy(isSearching = false) }
                    }
                )
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun onAddFoodItemToLog(foodItem : USDAFoodItem){
        val dateString = uiState.value.currentDate.format(DateTimeFormatter.ISO_DATE_TIME)

        _uiState.update { it.copy(isSearching = true) }
        viewModelScope.launch {
            userLogRepository.addFoodItemToLog(foodItem, dateString).collect { result ->
                result.fold(
                    onSuccess = {
                        _uiState.update { it.copy(isSearching = false) }
                    },
                    onFailure = {
                        _uiState.update { it.copy(isSearching = false) }
                    }
                )
            }
        }
    }

    
}