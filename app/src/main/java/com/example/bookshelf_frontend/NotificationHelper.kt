package com.example.bookshelf_frontend.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.bookshelf_frontend.R

class NotificationHelper(private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val channelId = "bookshelf_demo"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Bookshelf Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Demo notifications for Bookshelf app"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showDemoNotification() {
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Bookshelf Demo")
            .setContentText("This is a demo notification from your Bookshelf app!")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)
    }
}