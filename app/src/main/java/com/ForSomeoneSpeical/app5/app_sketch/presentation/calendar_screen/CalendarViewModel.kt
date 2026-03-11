package com.ForSomeoneSpeical.app5.app_sketch.presentation.calendar_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val _currentDate = MutableStateFlow<LocalDate>(savedStateHandle.get<String>("date_string")?.let { LocalDate.parse(it)} ?: LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDate = _currentDate.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDate(offset : Long) : LocalDate {
        _currentDate.update { it.plusDays(offset) }
        return currentDate.value
    }




}