package com.example.composetutorial

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil3.request.Disposable


@Composable
fun SettingsScreen(navController: NavController, context: Context) {
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val accelerometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            Log.i("Permission: ", "Granted")
        } else {
            Log.i("Permission: ", "Denied")
        }
    }
    var xValue by remember { mutableFloatStateOf(0f) }
    var yValue by remember { mutableFloatStateOf(0f) }
    var zValue by remember { mutableFloatStateOf(0f) }
    var showData by remember { mutableStateOf(false) }

    var enableNoti by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    xValue = it.values[0]
                    yValue = it.values[1]
                    zValue = it.values[2]
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        }
        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_UI)
        onDispose {
            sensorManager.unregisterListener(listener)
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
                navController.navigate(Screen.StartScreen.route) {
                    popUpTo(Screen.StartScreen.route) {inclusive = true}
                }
            },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("<-- Back")
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row {
            Button(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                    }
                    enableNoti  = if (enableNoti) {
                        true
                    } else {
                        true
                    }
                }
            ) {
                Text("Enable notifications")
            }
            if (enableNoti) {
                Button(
                    onClick = {
                        scheduleNotification(context)
                    }
                ) {
                    Text("Schedule Notification")
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                showData = if (showData) {
                    false
                } else {
                    true
                }
            }
        ) { Text("Toggle data") }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showData) {
                Text(text = "Accelerometer Data", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "X: $xValue")
                Text(text = "Y: $yValue")
                Text(text = "Z: $zValue")
            }
        }
    }
}


