package com.cody.training.ui.todo

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.cody.training.model.Todo
import com.cody.training.receiver.AlarmReceiver
import com.cody.training.utils.NotificationRequestCode
import com.cody.training.utils.cancelNotifications

class TodoAlarmViewModel(private val app: Application) : AndroidViewModel(app) {

    fun startTimer(todos: List<Todo>) {
        val notificationManager = ContextCompat.getSystemService(
            app,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.cancelNotifications()

        val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        for (todo in todos) {
            val triggerTime = SystemClock.elapsedRealtime() + todo.notify * 60

            val notifyIntent = Intent(app, AlarmReceiver::class.java).apply {
                putExtra("todo", todo)
            }

            val notifyPendingIntent = PendingIntent.getBroadcast(
                getApplication(),
                NotificationRequestCode.TODO_ALARM,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime,
                notifyPendingIntent
            )
        }
    }
}