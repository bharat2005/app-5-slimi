package com.ForSomeoneSpeical.app5.app_root

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ForSomeoneSpeical.app5.app_sketch.presentation._navigation.appSketchNavGraph

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