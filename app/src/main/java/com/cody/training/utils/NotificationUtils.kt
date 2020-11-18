package com.cody.training.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.cody.training.R
import com.cody.training.TodoWriteActivity
import com.cody.training.model.Todo

fun NotificationManager.sendNotification(todo: Todo, applicationContext: Context) {
    val notificationId = todo.id.hashCode()

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    ).apply {
        setSmallIcon(R.drawable.ic_list)
        setContentTitle(applicationContext.getString(R.string.notification_title))
        setContentText(todo.description)

        val contentIntent = Intent(applicationContext, TodoWriteActivity::class.java).apply {
            putExtra("todo", todo)
        }
        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            notificationId,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        setContentIntent(contentPendingIntent)
        setAutoCancel(true)

        if (todo.preview != null) {
            val imagePreview = BitmapFactory.decodeFile(todo.preview, BitmapFactory.Options())
            val bigPicStyle = NotificationCompat.BigPictureStyle()
                .bigPicture(imagePreview)
                .bigLargeIcon(null)
            setStyle(bigPicStyle)
            setLargeIcon(imagePreview)
        }

        priority = NotificationCompat.PRIORITY_HIGH
    }
    notify(notificationId, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}