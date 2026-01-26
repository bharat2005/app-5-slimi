package com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Meal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun UserLogScreen(
    viewModel: UserLogViewModel = hiltViewModel()
) {
    var mealType by remember { mutableStateOf(Meal.BREAKFAST) }
    var showDialog by remember { mutableStateOf(false) }

    var searchQuery by remember { mutableStateOf("") }
    var isQuerySearchLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()






    Scaffold {  paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {

            Button(
                onClick = {
                    mealType = Meal.LUNCH
                    showDialog = true
                }
            ) {
                Text("Lunch")
            }


        }
    }


    if(showDialog){


        AlertDialog(
            onDismissRequest = {},
            title = {Text(mealType.name)},
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = searchQuery,
                        onValueChange = {searchQuery = it}
                    )

                    Button(onClick = {
                        scope.launch {
                            isQuerySearchLoading = true
                            delay(3000)
                            isQuerySearchLoading = false
                        }

                    }) {Text("Search") }


                    if(isQuerySearchLoading){
                        CircularProgressIndicator()
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(280.dp)
                        )
                        {
                            itemsIndexed(List(20) { it }) { index, item ->
                                Surface(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
                                ) {
                                    Text(item.toString())
                                }
                            }

                        }
                    }
                }

            },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                }) { Text("Confirm")}

            }
        )
    }

}














