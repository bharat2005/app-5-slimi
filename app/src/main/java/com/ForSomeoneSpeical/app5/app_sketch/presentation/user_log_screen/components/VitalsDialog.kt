package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Feeling
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Message
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Physiological

@Composable
fun VitalsDialog(

) {
    var bodyWeight by remember { mutableStateOf("") }
    var bodyFat by remember { mutableStateOf("") }

    var selectedPhysiological by remember { mutableStateOf<Physiological?>(null) }
    var selectedMessage by remember { mutableStateOf<Message?>(null) }
    var selectedFeeling by remember { mutableStateOf<Feeling?>(null) }

    var chest by remember { mutableStateOf("") }
    var waist by remember { mutableStateOf("") }
    var hips by remember { mutableStateOf("") }
    var forearms by remember { mutableStateOf("") }
    var calf by remember { mutableStateOf("") }


    AlertDialog(
        onDismissRequest = {},
        title = { Text("Vitals") },
        text = {


            Column(
                modifier = Modifier.fillMaxWidth(),
            )
            {


                //Body Weight
                TextField(
                    value = bodyWeight,
                    onValueChange = { bodyWeight = it },
                    placeholder = { Text("Body Weight (kg)") }
                )
                //Body Fat
                TextField(
                    value = bodyFat,
                    onValueChange = { bodyFat = it },
                    placeholder = { Text("Body Fat (%)") }
                )


                //Physiological
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text("Physiological")

                    Physiological.entries.forEach { item ->
                        Button(
                            enabled = selectedPhysiological != item,
                            onClick = {
                                selectedPhysiological = item
                            }
                        ) { Text(item.name) }
                    }

                }
                //Message
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text("Message")

                    Message.entries.forEach { item ->
                        Button(
                            enabled = selectedMessage != item,
                            onClick = { selectedMessage = item }
                        ) { Text(item.name) }
                    }

                }
                //How are you today?
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text("How feeling today?")

                    Feeling.entries.forEach { item ->
                        Button(
                            enabled = selectedFeeling != item,
                            onClick = { selectedFeeling = item }
                        ) { Text(item.name) }
                    }

                }


                //Body Measurements
                TextField(
                    value = chest,
                    onValueChange = { chest = it },
                    placeholder = { Text("Chest (cm)") }
                )
                TextField(
                    value = waist,
                    onValueChange = { waist = it },
                    placeholder = { Text("Waist (cm)") }
                )
                TextField(
                    value = hips,
                    onValueChange = { hips = it },
                    placeholder = { Text("Hips (cm)") }
                )
                TextField(
                    value = forearms,
                    onValueChange = { forearms = it },
                    placeholder = { Text("Forearms (cm)") }
                )
                TextField(
                    value = calf,
                    onValueChange = { calf = it },
                    placeholder = { Text("Calf (cm)") }
                )

            }
        },
        confirmButton = {
            Button(onClick = {}) { Text("Confirm") }
        }
    )

}