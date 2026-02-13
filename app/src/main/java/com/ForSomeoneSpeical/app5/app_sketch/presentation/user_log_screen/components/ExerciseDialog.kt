package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.ExerciseUIItem

@Composable
fun ExerciseDialog(
    exercisesList : List<ExerciseUIItem>,
    onAddExerciseToLog : (ExerciseUIItem) -> Unit,
    onExerciseDialogClose : () -> Unit,
) {

    AlertDialog(
        onDismissRequest = {},
        title = { Text("Exercise") },
        text = {
            Column {
                var searchQuery by remember { mutableStateOf("") }
                val filteredExercises by remember { derivedStateOf {
                    if(searchQuery.isBlank()){
                        exercisesList
                    } else {
                        exercisesList.filter { it.name.contains(searchQuery, true) }
                    }
                } }

                TextField(
                    value = searchQuery,
                    onValueChange = {searchQuery = it},
                )

                LazyColumn {
                    items(filteredExercises, key = {it.name}){
                        Surface(
                            onClick = {
                               onAddExerciseToLog(it)
                            }
                        ) {
                            Column {
                                Text(it.name)
                                Text("${it.burnCalories} kcal per ${it.perMinutes} minutes", color = Color.Gray)
                            }
                        }
                    }

                }
            }
        },
        confirmButton = {
            Button(
                onClick = onExerciseDialogClose
            ){
                Text("Confirm")
            }
        }
    )


}