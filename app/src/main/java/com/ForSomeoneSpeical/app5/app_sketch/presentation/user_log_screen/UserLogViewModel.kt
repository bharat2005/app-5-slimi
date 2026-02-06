package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen

import android.app.Dialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Meal
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAResponse
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.getCalories
import com.ForSomeoneSpeical.app5.app_sketch.domain.repo.UserLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.Boolean


enum class FoodCategory(val displayName : String) {
    FOUNDATION("Foundation"),
    SR_LEGACY("SR Legacy"),
    SURVEY_FNDDS("Survey (FNDDS)"),
    BRANDED("Branded"),

}
data class UserLogState @RequiresApi(Build.VERSION_CODES.O) constructor(

    //Global States
    val currentDate : LocalDate = LocalDate.now(),
    val errorMessage : String? = null,
    val isLoading : Boolean = false,
    val loggedFoodForDay : List<USDAFoodItem> = emptyList(),




    //Meal States
    val currentMealType : Meal = Meal.BREAKFAST,
    val selectedFoodCategory : FoodCategory = FoodCategory.FOUNDATION,
    val isFoodSearching : Boolean = false,
    val showMealDialog : Boolean = false,
    val searchedFoodItems : USDAResponse = USDAResponse(emptyList()),


    //Exercise States
    val showExerciseDialog: Boolean = false,



    )



@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class UserLogViewModel @Inject constructor(
    private val userLogRepository: UserLogRepository
) : ViewModel() {



    @RequiresApi(Build.VERSION_CODES.O)
    private val _uiState = MutableStateFlow(UserLogState())
    @RequiresApi(Build.VERSION_CODES.O)
    val uiState = _uiState.asStateFlow()
    private var job : Job? = null




    //Local State Updates for Date Selector
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDate(offset : Long){
        val newDate = uiState.value.currentDate.plusDays(offset)
        _uiState.update { it.copy(currentDate = newDate, loggedFoodForDay = emptyList(), isLoading = true) }

        listenForLoggedFoodItems(newDate)
    }



    //Local State Updates

        //---Meal Dialog
    @RequiresApi(Build.VERSION_CODES.O)
    fun onMealDialogOpen(meal : Meal){
        _uiState.update { it.copy(currentMealType = meal, showMealDialog = true) }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onMealDialogClose(){
        _uiState.update { it.copy(
            showMealDialog = false,
            selectedFoodCategory = FoodCategory.FOUNDATION,
             isFoodSearching = false,
             searchedFoodItems = USDAResponse(emptyList()),
        ) }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSelectedCategory(category: FoodCategory){
        _uiState.update { it.copy(selectedFoodCategory = category) }
    }


        //---Error Handling
    fun clearError(){
        _uiState.update { it.copy(errorMessage = null) }
    }




    //Repository Interactions for Meal Dialog
    @RequiresApi(Build.VERSION_CODES.O)
    fun searchFoodItems(query : String){
        _uiState.update { it.copy(isFoodSearching = true) }
        viewModelScope.launch {
            userLogRepository.searchFoodItems(query).collect { result ->
                result.fold(
                    onSuccess = { foodItemsList ->
                   _uiState.update { it.copy(isFoodSearching = false, searchedFoodItems = foodItemsList ) }
                    },
                    onFailure = {
                    _uiState.update { it.copy(isFoodSearching = false) }
                    }
                )
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onAddFoodItemToLog(foodItem : USDAFoodItem){
        val dateString = uiState.value.currentDate.format(DateTimeFormatter.ISO_DATE)

        _uiState.update { it.copy(isFoodSearching = true) }
        viewModelScope.launch {
            userLogRepository.addFoodItemToLog(foodItem, dateString).collect { result ->
                result.fold(
                    onSuccess = {
                        onMealDialogClose()
                    },
                    onFailure = {
                        onMealDialogClose()
                    }
                )
            }
        }
    }





    //Repository Interactions for Logged Food Items

    fun onUpdateFoodItemQuantity(foodItem : USDAFoodItem, delta : Int){
        val  updatedQuantity = foodItem.quantity + delta
        val newKcal = updatedQuantity * (foodItem.getCalories() ?: 0.0)

        _uiState.update { it.copy(isLoading = true) }
        val dateString = uiState.value.currentDate.format(DateTimeFormatter.ISO_DATE)
        viewModelScope.launch {
            runCatching {
                userLogRepository.updateFoodItemQuantity(foodItem.docId, dateString, updatedQuantity, newKcal)
            }.onSuccess {
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }

        }
    }
    fun onKcalUpdate(foodItem : USDAFoodItem, newKcal : Double){

        _uiState.update { it.copy(isLoading = true) }
        val dateString = uiState.value.currentDate.format(DateTimeFormatter.ISO_DATE)
        viewModelScope.launch {
            runCatching {
                userLogRepository.updateCalorie(foodItem.docId, dateString, newKcal)
            }.onSuccess {
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }

        }
    }
    fun onDeleteFoodItem(foodItem : USDAFoodItem){
        val dateString = uiState.value.currentDate.format(DateTimeFormatter.ISO_DATE)

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            runCatching {
                userLogRepository.onDeleteFoodItem(foodItem.docId, dateString )
            }.onSuccess {
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }





    //Listeners
    @RequiresApi(Build.VERSION_CODES.O)
    fun listenForLoggedFoodItems(date : LocalDate){
        job?.cancel()

        val dateString = date.format(DateTimeFormatter.ISO_DATE)

        job = viewModelScope.launch {
            userLogRepository.listenForFoodLogs(dateString).collect { loggedFoodItems ->
                _uiState.update { it.copy(loggedFoodForDay = loggedFoodItems, isLoading = false) }
            }
        }
    }




    init {
        listenForLoggedFoodItems(LocalDate.now())
    }


}