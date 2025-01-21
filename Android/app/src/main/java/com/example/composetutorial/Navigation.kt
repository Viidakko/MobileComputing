package com.example.composetutorial

import SampleData
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class Message(val author: String, val body: String)

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.StartScreen.route) {
        composable(route = Screen.StartScreen.route) {
            StartScreen(navController = navController)
        }
        composable(route = Screen.ConversationScreen.route) {
            ConversationScreen(navController = navController, messages = SampleData.conversationSample)
        }
    }
}

