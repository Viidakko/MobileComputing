package com.example.composetutorial

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.icu.util.TimeUnit
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "my_channel_id",
            "My Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply { description = "Channel for app notifications" }
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}

fun sendNotification(context: Context, title: String, text: String) {
    val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager

    val notification = NotificationCompat.Builder(context, "my_channel_id")
        .setSmallIcon(R.drawable.missing_avatar)
        .setContentTitle(title)
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    notificationManager.notify(1, notification)
}

class NotificationWorker(context: Context, workerParams: WorkerParameters) :
        Worker(context, workerParams) {

    override fun doWork(): Result {
        sendNotification(
            applicationContext,
            title = "Timed notification",
            text = "This is a notification to test timed notification")
        return Result.success()
    }
}

fun scheduleNotification(context: Context) {
    val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(10, java.util.concurrent.TimeUnit.SECONDS)
        .build()
}