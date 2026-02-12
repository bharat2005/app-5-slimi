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
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.DailyVitals
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.DailyVitalsDTO
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Feeling
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Message
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Physiological

@Composable
fun VitalsDialog(
    onVitalsDialogClose : () -> Unit,
    dailyVitals : DailyVitals
) {
    var bodyWeight by remember { mutableStateOf(dailyVitals.bodyWeight ?: "") }
    var bodyFat by remember { mutableStateOf(dailyVitals.bodyFat ?: "") }

    var selectedPhysiological by remember { mutableStateOf<Physiological?>(dailyVitals.physiological) }
    var selectedMessage by remember { mutableStateOf<Message?>(dailyVitals.message) }
    var selectedFeeling by remember { mutableStateOf<Feeling?>(dailyVitals.feeling) }

    var chest by remember { mutableStateOf(dailyVitals.chest ?: "") }
    var waist by remember { mutableStateOf(dailyVitals.waist ?: "") }
    var hips by remember { mutableStateOf(dailyVitals.hips ?: "") }
    var forearms by remember { mutableStateOf(dailyVitals.forearms ?: "") }
    var calf by remember { mutableStateOf(dailyVitals.forearms ?: "") }


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
                    value = bodyWeight.toString(),
                    onValueChange = { bodyWeight = it },
                    placeholder = { Text("Body Weight (kg)") }
                )
                //Body Fat
                TextField(
                    value = bodyFat.toString(),
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
                Column (
                    modifier = Modifier.fillMaxWidth(),
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
                    value = chest.toString(),
                    onValueChange = { chest = it },
                    placeholder = { Text("Chest (cm)") }
                )
                TextField(
                    value = waist.toString(),
                    onValueChange = { waist = it },
                    placeholder = { Text("Waist (cm)") }
                )
                TextField(
                    value = hips.toString(),
                    onValueChange = { hips = it },
                    placeholder = { Text("Hips (cm)") }
                )
                TextField(
                    value = forearms.toString(),
                    onValueChange = { forearms = it },
                    placeholder = { Text("Forearms (cm)") }
                )
                TextField(
                    value = calf.toString(),
                    onValueChange = { calf = it },
                    placeholder = { Text("Calf (cm)") }
                )

            }
        },
        confirmButton = {
            Button(onClick = onVitalsDialogClose) { Text("Confirm") }
        }
    )

}