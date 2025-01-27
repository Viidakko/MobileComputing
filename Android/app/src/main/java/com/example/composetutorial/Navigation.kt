package com.example.composetutorial

import SampleData
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.Uri
import coil3.toCoilUri


@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.StartScreen.route) {
        composable(route = Screen.StartScreen.route) {
            StartScreen(navController = navController, context)
        }
        composable(route = Screen.ConversationScreen.route) {
            ConversationScreen(navController = navController, SampleData.conversationSample, context)
        }
    }
}

