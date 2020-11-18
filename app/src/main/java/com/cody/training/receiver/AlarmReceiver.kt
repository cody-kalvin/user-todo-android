package com.cody.training.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.cody.training.model.Todo
import com.cody.training.utils.sendNotification
import com.google.gson.Gson

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        val todo = try {
            Gson().fromJson(intent.getStringExtra("todo"), Todo::class.java)
        } catch (e: Exception) {
            null
        }
        notificationManager.sendNotification(todo!!, context)
    }
}