package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Exercise
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.ExerciseEditType
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.LoggedExercise

@Composable
fun LoggedExerciseItem(
    exercise : LoggedExercise,
    onDeleteExerciseItem : () -> Unit,
    onUpdateCaloriesBurned : (Double, Int?) -> Unit,
) {
    var textInput by remember { mutableStateOf("") }
    var editState by remember { mutableStateOf(ExerciseEditType.MINUTES) }

    Row(
        modifier = Modifier.fillMaxWidth().background(Color.Gray),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        //Food Name
        Column(
            modifier = Modifier.fillMaxWidth(0.4f),
        ) {
            Text(exercise.name)
            Text(exercise.caloriesBurned.toString(), color = Color.Red)
        }

        //Food Update Quantity Actions
        Column(
            modifier = Modifier.weight(1f)
        ) {

                TextField(
                    value = textInput,
                    onValueChange = { textInput = it},
                    modifier = Modifier.height(48.dp)
                )

                Button(
                    enabled = editState != ExerciseEditType.MINUTES,
                    onClick = {
                        editState = ExerciseEditType.MINUTES
                        textInput = ""
                    },
                )
                {
                    Text("Minutes")
                }

                Button(
                    enabled = editState != ExerciseEditType.CALORIES,
                    onClick = {
                        editState = ExerciseEditType.CALORIES
                        textInput = ""
                    }
                )
                {
                    Text("Colorizes")
                  }


            //Update Kcal Button
            Button(
                onClick = {
                    when(editState){
                        ExerciseEditType.MINUTES -> {
                            val minutes = textInput.toIntOrNull()
                            if(minutes != null){
                                val calories = exercise.baseCaloriesBurnPerMinute * minutes
                                onUpdateCaloriesBurned(calories, minutes)
                            }
                        }
                        ExerciseEditType.CALORIES -> {
                            val calories = textInput.toDoubleOrNull()
                            val minutes = null
                            if(calories != null){
                                onUpdateCaloriesBurned(calories, minutes)
                            }
                        }
                    }

                }) {Text("Save") }
        }


        //Delete Button
        IconButton(
            onClick = onDeleteExerciseItem

        )
        {
            Icon(Icons.Default.Delete, null, tint = Color.Red)
        }
    }
}