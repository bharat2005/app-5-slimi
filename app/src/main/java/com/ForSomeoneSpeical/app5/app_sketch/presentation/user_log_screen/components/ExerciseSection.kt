package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
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
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.LoggedExercise
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.getFullName

@Composable
fun ExerciseSection(
    onAddExerciseClick : () -> Unit,
    loggedExercisesList : List<LoggedExercise>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        //Exercise title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.CenterHorizontally)
        )
        {
            Text("Exercise")
            Text("(total calories burned)")
        }
        Spacer(modifier = Modifier.height(12.dp))


        //Logged Exercise (Multi)
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        )
        {

            loggedExercisesList.forEach{ exercise ->
                //Logged Food Item (Single)
                LoggedExerciseItem(
                    exerciseName =  exercise.name,
                    kcal = exercise.caloriesBurned.toString()
                )

            }

        }



        //Add Exercise Button
        Button(
            onClick = onAddExerciseClick,
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text("Add Exercise")
        }


    }


}