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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoggedExerciseItem(
    exerciseName : String,
) {
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
            Text(exerciseName)
            Text("(kcal)", color = Color.Red)
        }

        //Food Update Quantity Actions
        Column(
            modifier = Modifier.weight(1f)
        ) {

                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.height(48.dp)
                )

                Button(
                    onClick = {

                    },
                ) { Text("Minutes") }

                Button(
                    onClick = {

                    }
                ) {
                    Text("Colorizes")
                  }


            //Update Kcal Button
            Button(
                onClick = {

                }) {Text("Save") }
        }


        //Delete Button
        IconButton(
            onClick = {

            }
        )
        {
            Icon(Icons.Default.Delete, null, tint = Color.Red)
        }
    }
}