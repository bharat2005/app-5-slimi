package com.ForSomeoneSpeical.app5.app_sketch.presentation.userInput_screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.ActivityLevel
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.DietCourse
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Gender
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.HealthProblem

@Composable
fun UserInputScreen(
    viewModel: UserInputViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Gender
            item {
                Text("Gender")
                Row { Gender.entries.forEach { gender -> Button(colors = ButtonDefaults.buttonColors(containerColor = if(uiState.gender == gender) Color.Green else Color.Transparent),onClick = {viewModel.updateState(uiState.copy(gender = gender))}) { Text(gender.name) } } }
            }

            //Age
            item {
                Text("Age")
                TextField(value = uiState.age, onValueChange = {viewModel.updateState(state = uiState.copy(age = it))}) }

            //Height
            item {
                Text("Height")
                TextField(value = uiState.height, onValueChange = {viewModel.updateState(state = uiState.copy(height = it))}) }

            //CurrentWeight
            item {
                Text("CurrentWeight")
                TextField(value = uiState.currentWeight, onValueChange = {viewModel.updateState(state = uiState.copy(currentWeight = it))}) }

            //TargetWeight
            item {
                Text("TargetWeight")
                TextField(value = uiState.targetWeight, onValueChange = {viewModel.updateState(state = uiState.copy(targetWeight = it))}) }

            //Pace
            val currentWeight = uiState.currentWeight.toDoubleOrNull()
            if(currentWeight != null){
                item {
                    val slowPace = "%.1f".format(currentWeight * 0.005 * 4.345)
                    val fastPace = "%.1f".format(currentWeight * 0.01 * 4.345)
                    Text("Pace")
                    Button(onClick = {}) { Text("Slow Pace")}
                    Button(onClick = {}) { Text("Fast Pace")}

                }
            }

            //ActivityLevel
            item{
                ActivityLevel.entries.forEach { activityLevel ->
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(uiState.activityLevel == activityLevel) Color.Green else Color.Transparent
                        ),
                        onClick = {viewModel.updateState(uiState.copy(activityLevel = activityLevel))}
                    ) {Text(activityLevel.name) }
                }
            }


            //HealthProblem
            item {
                HealthProblem.entries.forEach { healthProblem ->
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(uiState.healthProblem == healthProblem) Color.Green else Color.Transparent
                        ),
                        onClick = {viewModel.updateState(uiState.copy(healthProblem = healthProblem))}
                    ) {Text(healthProblem.name) }
                }
            }

            //DietCourse
            item {
                DietCourse.entries.forEach { dietCourse ->
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(uiState.dietCourse == dietCourse) Color.Green else Color.Transparent
                        ),
                        onClick = {viewModel.updateState(uiState.copy(dietCourse = dietCourse))}
                    ) {Text(dietCourse.displayValue) }
                }
            }


        }
    }



}