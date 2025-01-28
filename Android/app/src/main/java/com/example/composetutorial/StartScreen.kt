package com.example.composetutorial

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.Image
import coil3.Uri
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.toCoilUri
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@Composable
fun StartScreen(navController: NavController, context: Context) {
    val database = remember { getDatabase(context) }
    var name by remember { mutableStateOf("") }
    var imageFile = File(context.filesDir, "profile.jpg")
    var updateImage by remember { mutableStateOf(false) }
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {imageUri ->
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val outputFile = File(context.filesDir, "profile.jpg")
            FileOutputStream(outputFile).use { outputStream ->
                inputStream?.copyTo(outputStream)
            }
            imageFile = outputFile
            updateImage = !updateImage
        }
    }
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Button(
            onClick = {
                navController.navigate(Screen.ConversationScreen.route)
            },
            modifier = Modifier.padding(top = 15.dp)
        ) {
            Text(text = "Conversation")
        }
        Text(
            text = "Welcome to MessageApp!",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 25.dp, bottom = 25.dp)
        )
        val painter = if (updateImage) {
            rememberAsyncImagePainter(imageFile)
        } else {
            rememberAsyncImagePainter(imageFile)
        }
        Row (horizontalArrangement = Arrangement.Center) {
            Image(
                painter = painter,
                contentDescription = "Profile pic",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                    .clickable {
                        pickMedia.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
            )
            Spacer(modifier = Modifier.width(5.dp))
            TextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = { Text("Enter name") },
                singleLine = true,
                modifier = Modifier.align(Alignment.CenterVertically)
            )


        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    database.userDao().insertUserData(User(username = name))
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