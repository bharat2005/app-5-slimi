package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.NutritionixFoodItem
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.NutritionixSearchResponse
import com.ForSomeoneSpeical.app5.app_sketch.domain.repo.UserLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



data class UserLogState(
    val isSearching : Boolean = false,
    val searchedFoodItems : NutritionixSearchResponse = NutritionixSearchResponse(emptyList()),
)


@HiltViewModel
class UserLogViewModel @Inject constructor(
    private val userLogRepository: UserLogRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserLogState())
    val uiState = _uiState.asStateFlow()


//    fun searchFoodItems(query : String){
//        _uiState.update { it.copy(isSearching = true) }
//        viewModelScope.launch {
//            userLogRepository.searchFoodItems(query).collect { result ->
//                result.fold(
//                    onSuccess = { foodItemsList ->
//                   _uiState.update { it.copy(isSearching = false, searchedFoodItems = foodItemsList ) }
//                    },
//                    onFailure = {
//                    _uiState.update { it.copy(isSearching = false) }
//                    }
//                )
//            }
//        }
//    }

    
}