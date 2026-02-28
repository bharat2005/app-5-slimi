package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components


import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.DailyVitals
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.DailyVitalsDTO
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Feeling
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Message
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Physiological
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.SleepInterval

import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VitalsDialog(
    onVitalsDialogClose : () -> Unit,
    dailyVitals : DailyVitals,
    onUpdateVitals : (DailyVitals) -> Unit,
) {
    var bodyWeight by remember { mutableStateOf(dailyVitals.bodyWeight?.toString() ?: "") }
    var bodyFat by remember { mutableStateOf(dailyVitals.bodyFat?.toString() ?: "") }

    var selectedPhysiological by remember { mutableStateOf<Physiological?>(dailyVitals.physiological) }
    var selectedMessage by remember { mutableStateOf<Message?>(dailyVitals.message) }
    var selectedFeeling by remember { mutableStateOf<Feeling?>(dailyVitals.feeling) }

    var chest by remember { mutableStateOf(dailyVitals.chest?.toString() ?: "") }
    var waist by remember { mutableStateOf(dailyVitals.waist?.toString() ?: "") }
    var hips by remember { mutableStateOf(dailyVitals.hips?.toString() ?: "") }
    var forearms by remember { mutableStateOf(dailyVitals.forearms?.toString() ?: "") }
    var calf by remember { mutableStateOf(dailyVitals.calf?.toString() ?: "") }

    var sleepIntervalsList by remember { mutableStateOf(
        dailyVitals.sleepIntervalList
    ) }

    val context = LocalContext.current



    AlertDialog(
        onDismissRequest = onVitalsDialogClose,
        title = { Text("Vitals") },
        text = {

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {

                item {
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
                }

                item {
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
                    Column(
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
                }

                item {
                //Body Measurements
                Column {
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
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        //Add SleepInterval Button
                        Button(
                            onClick = {
                              sleepIntervalsList = sleepIntervalsList + SleepInterval()
                            }
                        ) {Text("Add Sleep Interval") }

                        sleepIntervalsList.forEach { interval ->
                            Row(
                                modifier = Modifier.fillMaxWidth().background(Color.Gray),
                                horizontalArrangement = Arrangement.SpaceBetween

                            ){
                                //Start Time
                                TextButton(
                                    onClick = {
                                        val now = Calendar.getInstance()
                                        TimePickerDialog(
                                            context,
                                            {_, hour, minute ->
                                                val startString = "%02d:%02d".format(hour, minute)
                                                sleepIntervalsList = sleepIntervalsList.map {
                                                    if(it.id == interval.id) it.copy(start = startString) else it
                                                }
                                            },
                                            now.get(Calendar.HOUR_OF_DAY),
                                            now.get(Calendar.MINUTE),
                                            true
                                        ).show()
                                    }
                                ) { Text(interval.start) }

                                //End Time
                                TextButton(
                                    onClick = {
                                        val now = Calendar.getInstance()
                                        TimePickerDialog(
                                            context,
                                            {_, hour, minute ->
                                                val endString = "%02d:%02d".format(hour, minute)
                                                sleepIntervalsList = sleepIntervalsList.map {
                                                    if(it.id == interval.id) it.copy(end = endString) else it
                                                }
                                            },
                                            now.get(Calendar.HOUR_OF_DAY),
                                            now.get(Calendar.MINUTE),
                                            true
                                        ).show()
                                    }
                                ) { Text(interval.end) }
                            }
                        }


                    }
                }




            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedDailyVitals = dailyVitals.copy(
                    bodyWeight = bodyWeight.toDoubleOrNull(),
                    bodyFat = bodyFat.toDoubleOrNull(),
                    physiological = selectedPhysiological,
                    message = selectedMessage,
                    feeling = selectedFeeling,
                    chest = chest.toDoubleOrNull(),
                    waist = waist.toDoubleOrNull(),
                    hips = hips.toDoubleOrNull(),
                    forearms = forearms.toDoubleOrNull(),
                    calf = calf.toDoubleOrNull()
                )
                onUpdateVitals(updatedDailyVitals)
            }) { Text("Confirm") }
        }
    )

}