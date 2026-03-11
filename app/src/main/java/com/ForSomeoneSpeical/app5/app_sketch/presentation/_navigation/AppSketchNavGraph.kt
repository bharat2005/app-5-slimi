package com.ForSomeoneSpeical.app5.app_sketch.presentation._navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ForSomeoneSpeical.app5.app_sketch.presentation.calendar_screen.CalendarScreen
import com.ForSomeoneSpeical.app5.app_sketch.presentation.user_log_screen.UserLogScreen
import com.ForSomeoneSpeical.app5.app_sketch.presentation.userInput_screen.UserInputScreen


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.appSketchNavGraph(navController: NavController) {
    navigation(
        route = "appSketchRoute",
        startDestination = AppSketchRoutes.UserLogRoute
    ) {
        composable(AppSketchRoutes.UserInputRoute) {
            UserInputScreen(
                onNavigateToTrack = {
                    navController.navigate(AppSketchRoutes.UserLogRoute){
                        popUpTo(AppSketchRoutes.UserInputRoute)
                    }
                }
            )
        }

        composable(AppSketchRoutes.UserLogRoute) {
            UserLogScreen(
                onNavigateToCalendar = { dateString ->
                    navController.navigate("${AppSketchRoutes.CalendarRoute}/${dateString}")
                }
            )

        }

        composable(
            route = "${AppSketchRoutes.CalendarRoute}/{date_string}",
            arguments = listOf(navArgument("date_string"){type = NavType.StringType})
        ) {
            CalendarScreen(
                onNavigateBack = { dateString ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("date_string", dateString)
                    navController.popBackStack()
                }
            )
        }

        composable(AppSketchRoutes.UserAdviceRoute) {

        }

    }
}
