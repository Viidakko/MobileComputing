package com.example.composetutorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ConversationScreen(navController: NavController, messages: List<Message>) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.offset(0.dp, 25.dp)
    ) {
        Button(
            onClick = {
                navController.navigate(Screen.StartScreen.route)
            },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("<-- Back")
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn(
        modifier = Modifier.offset(0.dp, 75.dp)
    ) {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}