package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.USDAResponse
import com.ForSomeoneSpeical.app5.app_sketch.domain.repo.UserLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



enum class FoodCategory(val displayName : String) {
    FOUNDATION("Foundation"),
    SR_LEGACY("SR Legacy"),
    SURVEY_FNDDS("Survey (FNDDS)"),
    BRANDED("Branded"),

}
data class UserLogState(
    val isSearching : Boolean = false,
    val searchedFoodItems : USDAResponse = USDAResponse(emptyList()),
    val seletedCategory : FoodCategory = FoodCategory.FOUNDATION
)



@HiltViewModel
class UserLogViewModel @Inject constructor(
    private val userLogRepository: UserLogRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserLogState())
    val uiState = _uiState.asStateFlow()


    fun updateSelectedCategory(category: FoodCategory){
        _uiState.update { it.copy(seletedCategory = category) }
    }

    fun resetUiState(){
        _uiState.update { UserLogState() }
    }


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

    
}