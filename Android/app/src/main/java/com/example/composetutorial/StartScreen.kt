package com.example.composetutorial

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.Uri

@Composable
fun StartScreen(navController: NavController) {
    var imageUri by remember { mutableStateOf<android.net.Uri?>(null) }
    var name by remember { mutableStateOf("") }
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: android.net.Uri? ->
        imageUri = uri
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Text(
            text = "Welcome to MessageApp!",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Row (horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        ) {
            Text(text = "Add profile picture")
        }
        Spacer(modifier = Modifier.width(5.dp))
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = { Text("Enter name") }

        )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                navController.navigate(Screen.ConversationScreen.route)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Start here")
        }
    }
}