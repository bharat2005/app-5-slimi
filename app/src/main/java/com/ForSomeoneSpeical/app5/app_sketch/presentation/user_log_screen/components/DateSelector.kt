package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateSelector(
    onDateUpdate : (Long) -> Unit,
    currentDate : LocalDate
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally)
    ) {

        IconButton(
            onClick = {onDateUpdate(1)}
        ) { Icon(Icons.Default.ArrowBack, null) }

        Text(
            text = if(currentDate == LocalDate.now()) "Today" else "${currentDate.format(
            DateTimeFormatter.ISO_DATE)}"
        )

        IconButton(
            onClick = {onDateUpdate(-1)}
        ) { Icon(Icons.Default.ArrowForward, null) }

    }

}