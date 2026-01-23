package com.ForSomeoneSpeical.app5.app_sketch.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ForSomeoneSpeical.app5.app_sketch.presentation.userInput_screen.UserInputScreen


fun NavGraphBuilder.appSketchNavGraph(navController: NavController) {
    navigation(
        route = "appSketchRoute",
        startDestination = AppSketchRoutes.UserInputRoute
    ) {
        composable(AppSketchRoutes.UserInputRoute) {
            UserInputScreen()
        }

        composable(AppSketchRoutes.UserLogRoute) {

        }

        composable(AppSketchRoutes.UserAdviceRoute) {

        }

    }
}
