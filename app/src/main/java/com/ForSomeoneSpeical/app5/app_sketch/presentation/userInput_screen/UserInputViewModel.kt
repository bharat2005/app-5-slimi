package com.ForSomeoneSpeical.app5.app_sketch.presentation.userInput_screen

import androidx.lifecycle.ViewModel
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.UserInputUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class UserInputViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(UserInputUiState())
    val uiState = _uiState.asStateFlow()

    fun updateState(state : UserInputUiState){
        _uiState.update { state }
    }

}