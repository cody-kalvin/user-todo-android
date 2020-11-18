package com.cody.training

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cody.training.databinding.ActivityTodoListBinding
import com.cody.training.ui.todo.TodoAlarmViewModel
import com.cody.training.ui.todo.TodoListAdapter
import com.cody.training.ui.todo.TodoListViewModel

class TodoListActivity : AppCompatActivity() {

    private lateinit var listViewModel: TodoListViewModel

    private val listAdapter = TodoListAdapter()

    private lateinit var alarmViewModel: TodoAlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        alarmViewModel = ViewModelProvider(this).get(TodoAlarmViewModel::class.java)

        listViewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)

        val binding = DataBindingUtil.setContentView<ActivityTodoListBinding>(
            this,
            R.layout.activity_todo_list
        )

        binding.lifecycleOwner = this

        binding.viewModel = listViewModel

        binding.listTodo.apply {
            layoutManager = LinearLayoutManager(this@TodoListActivity)
            adapter = listAdapter
        }

        binding.buttonAdd.setOnClickListener {
            val intent = Intent(this, TodoWriteActivity::class.java).apply {
                putExtra("todo", null as String?)
            }
            startActivity(intent)
        }

        createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name)
        )
    }

    override fun onResume() {
        super.onResume()

        val todos = listViewModel.fetch()
        if (todos.isEmpty()) {
            listAdapter.submitList(listOf(TodoListAdapter.TodoListItem.Empty))
        } else {
            val list = todos.map { todo ->
                TodoListAdapter.TodoListItem.Body(todo)
            }
            listAdapter.submitList(list)
        }

        alarmViewModel.startTimer(todos)
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setShowBadge(false)
            }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = ContextCompat.getColor(this, R.color.blue_700_light)
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.action_save)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}