package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen

import android.icu.util.LocaleData
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ForSomeoneSpeical.app5.app_sketch.data.local.ExerciseDatabase
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.DailyVitals
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Exercise
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.ExerciseUIItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.LoggedExercise
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Meal
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAResponse
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.getCalories
import com.ForSomeoneSpeical.app5.app_sketch.domain.repo.UserLogRepository
import com.ForSomeoneSpeical.app5.core.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
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
    val loggedExerciseForDay : List<LoggedExercise> = emptyList(),




    //Meal States
    val currentMealType : Meal = Meal.BREAKFAST,
    val selectedFoodCategory : FoodCategory = FoodCategory.FOUNDATION,
    val isFoodSearching : Boolean = false,
    val showMealDialog : Boolean = false,
    val searchedFoodItems : USDAResponse = USDAResponse(emptyList()),


    //Exercise States
    val showExerciseDialog: Boolean = false,

    //Vitals States
    val showVitalsDialog : Boolean = false,
    val loggedVitalsForDay : DailyVitals = DailyVitals()

    )



@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class UserLogViewModel @Inject constructor(
    private val userLogRepository: UserLogRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    val userDataState = userRepository.userDataState


    @RequiresApi(Build.VERSION_CODES.O)
    private val _uiState = MutableStateFlow(UserLogState())
    @RequiresApi(Build.VERSION_CODES.O)
    val uiState = _uiState.asStateFlow()
    private var mealJob : Job? = null
    private var exerciseJob : Job? = null
    private var vitalsJob : Job? = null
    val exercisesUiItemList = userDataState.map { userData ->
        val weight = userData?.weight ?: 60.0
        mapExerciseList(ExerciseDatabase.exercises, weight)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())



    //Local State Updates for Date Selector
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDate(offset : Long){
        val newDate = uiState.value.currentDate.plusDays(offset)
        _uiState.update { it.copy(currentDate = newDate, loggedFoodForDay = emptyList(), isLoading = true) }

        listenForLoggedVitals(newDate)
        listenForLoggedFoodItems(newDate)
        listenForLoggedExercises(newDate)
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
        //---Exercise Dialog
    fun onExerciseDialogOpen(){
        _uiState.update { it.copy(showExerciseDialog = true) }
    }
    fun onExerciseDialogClose(){
        _uiState.update { it.copy(showExerciseDialog = false) }
    }
    fun mapExerciseList(exerciseList : List<Exercise>, userWeight : Double) : List<ExerciseUIItem>{
        return exerciseList.map { item ->
            val minutes = 10
            val kcal = (item.metValue * (userWeight * 3.5) / 200) * minutes
            ExerciseUIItem(
                name = item.name,
                burnCalories = kcal,
                perMinutes = minutes
            )
        }
    }
        //--Vitals Dialog
    fun onVitalsDialogOpen(){
            _uiState.update { it.copy(showVitalsDialog = true) }
        }
    fun onVitalsDialogClose(){
        _uiState.update { it.copy(showVitalsDialog = false) }
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






    //Repository Interactions for Exercise Dialog
    fun onAddExerciseToLog(exercise : ExerciseUIItem){
        val dateString = uiState.value.currentDate.format(DateTimeFormatter.ISO_DATE)
        val exercise = LoggedExercise(
            name = exercise.name,
            durationMinutes = exercise.perMinutes,
            caloriesBurned = exercise.burnCalories,
            baseCaloriesBurnPerMinute = exercise.burnCalories / exercise.perMinutes
        )

        _uiState.update { it.copy(showExerciseDialog = false, isLoading = true) }
        viewModelScope.launch {
            userLogRepository.addExerciseItemToLog(exercise, dateString).collect { result ->
                result.fold(
                    onSuccess = {
                        _uiState.update { it.copy(isLoading = false) }
                    },
                    onFailure = { e ->
                        _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
                    }
                )
            }
        }
    }

    //Reposotry Interactions for Logged Exercise Items
    fun onDeleteExerciseItem(exercise : LoggedExercise) {
        val dateString = uiState.value.currentDate.format(DateTimeFormatter.ISO_DATE)

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            runCatching {
                userLogRepository.onDeleteExerciseItem(exercise.docId, dateString)
            }.onSuccess {
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
    fun onUpdateExerciseCalories(exercise : LoggedExercise , newCalores : Double, minutes : Int?){
        val dateString = uiState.value.currentDate.format(DateTimeFormatter.ISO_DATE)

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            runCatching {
                userLogRepository.onUpdateCaloriesBurned(exercise.docId, dateString, newCalores, minutes)
            }.onSuccess {
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }




    //Repository Interactions for Vitals Dialog
    fun onUpdateVitals(dailyVitals: DailyVitals){
        val dateString = uiState.value.currentDate.format(DateTimeFormatter.ISO_DATE).toString()

        onVitalsDialogClose()
        _uiState.update { it.copy(isLoading = true) }


    }




    //Listeners
    @RequiresApi(Build.VERSION_CODES.O)
    fun listenForLoggedFoodItems(date : LocalDate){
        mealJob?.cancel()

        val dateString = date.format(DateTimeFormatter.ISO_DATE)

        mealJob = viewModelScope.launch {
            userLogRepository.listenForFoodLogs(dateString).collect { loggedFoodItems ->
                _uiState.update { it.copy(loggedFoodForDay = loggedFoodItems, isLoading = false) }
            }
        }
    }
    fun listenForLoggedExercises(date : LocalDate){
        exerciseJob?.cancel()

        exerciseJob = viewModelScope.launch {
            userLogRepository.listenForExerciseLogs(date.format(DateTimeFormatter.ISO_DATE)).collect { loggedExercises ->
                _uiState.update { it.copy(loggedExerciseForDay = loggedExercises, isLoading = false) }
            }
        }

    }
    fun listenForLoggedVitals(date : LocalDate){
        vitalsJob?.cancel()
        vitalsJob = viewModelScope.launch {
            userLogRepository.listenForVitalsLog(date.format(DateTimeFormatter.ISO_DATE)).collect { dailyVital ->
                _uiState.update { it.copy(loggedVitalsForDay =  dailyVital) }
            }
        }
    }



    init {
        listenForLoggedVitals(LocalDate.now())
        listenForLoggedFoodItems(LocalDate.now())
        listenForLoggedExercises(LocalDate.now())
    }


}