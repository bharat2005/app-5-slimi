package com.ForSomeoneSpeical.app5.app_root

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ForSomeoneSpeical.app5.app_sketch.presentation.navigation.appSketchNavGraph

@Composable
fun AppRoot(

) {
    val navController = rememberNavController()



    NavHost(
        startDestination = "appSketchRoute",
        navController = navController
    ){
        appSketchNavGraph(navController)
    }

}