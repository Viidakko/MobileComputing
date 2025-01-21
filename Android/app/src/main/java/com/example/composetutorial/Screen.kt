package com.example.composetutorial

sealed class Screen(val route: String) {
    data object StartScreen: Screen("start_screen")
    data object ConversationScreen: Screen("conversation_screen")
}