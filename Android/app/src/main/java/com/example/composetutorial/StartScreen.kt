package com.example.composetutorial

import android.content.Context
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.Image
import coil3.Uri
import coil3.compose.AsyncImage
import coil3.toCoilUri
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun StartScreen(navController: NavController, context: Context) {
    val database = remember { getDatabase(context) }
    var imageUri by remember  { mutableStateOf<android.net.Uri?>(null) }
    var name by remember { mutableStateOf("") }
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        imageUri = uri
    }
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Text(
            text = "Welcome to MessageApp!",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Button(
            onClick = {
                navController.navigate(Screen.ConversationScreen.route)
            },
        ) {
            Text(text = "Conversation")
        }
        if (imageUri != null) {
            Spacer(modifier = Modifier.height(75.dp))
            AsyncImage(
                model = imageUri,
                contentDescription = "profile pic",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(75.dp))
        } else {
            Spacer(modifier = Modifier.height(200.dp))
        }
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
                CoroutineScope(Dispatchers.IO).launch {
                    database.userDao().insertUserData(User(username = name, imageUri = imageUri.toString()))
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Save image and name")
        }

    }
}

@Preview
@Composable
fun PreviewStartPage() {
    ComposeTutorialTheme {
        StartScreen(navController = rememberNavController(), context = LocalContext.current)
    }
}