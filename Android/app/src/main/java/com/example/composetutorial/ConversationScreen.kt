package com.example.composetutorial

import android.content.Context
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.Uri
import coil3.toCoilUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun ConversationScreen(navController: NavController, messages: List<Message>, context: Context) {
    val database = remember { getDatabase(context) }
    var name by remember { mutableStateOf("Guest") }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val userData = database.userDao().getUserData(1)
            if (userData != null) {
                name = userData.username
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.offset(0.dp, 25.dp)
    ) {
        Button(
            onClick = {
                navController.navigate(Screen.StartScreen.route) {
                    popUpTo(Screen.StartScreen.route) {inclusive = true}
                }
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
            MessageCard(message, name)
        }
    }
}