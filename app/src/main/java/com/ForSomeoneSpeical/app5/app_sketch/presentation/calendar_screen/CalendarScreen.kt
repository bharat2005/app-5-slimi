package com.ForSomeoneSpeical.app5.app_sketch.presentation.calendar_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components.DateSelector
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel(),
    onNavigateBack : (String) -> Unit,
) {

    val currentDate by viewModel.currentDate.collectAsState()


    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            DateSelector(
                onDateUpdate = { offset ->
                    //update state
                    val updatedDate = viewModel.updateDate(offset)

                    //mutate prev savedstatehandle
                    //pop current backstack
                    onNavigateBack(updatedDate.toString())
                },
                currentDate = currentDate,
                onCalendarClick = {},//unnecessary
            )



        }
    }

}