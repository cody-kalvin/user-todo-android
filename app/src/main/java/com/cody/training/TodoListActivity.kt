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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cody.training.databinding.ActivityTodoListBinding
import com.cody.training.model.Todo
import com.cody.training.ui.todo.TodoAlarmViewModel
import com.cody.training.ui.todo.TodoListAdapter
import com.cody.training.ui.todo.TodoListViewModel
import com.cody.training.utils.showConfirmation
import com.google.gson.Gson

class TodoListActivity : AppCompatActivity(), TodoListAdapter.OnItemClickListener {

    private lateinit var listViewModel: TodoListViewModel

    private val listAdapter = TodoListAdapter(this)

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

        val touchCallback = object : SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (viewHolder is TodoListAdapter.TodoViewHolder) {
                    val activity = this@TodoListActivity
                    val todo = viewHolder.todo
                    val oldStatus = todo.status

                    listViewModel.delete(todo)

                    showConfirmation(
                        activity,
                        "Are you sure you want to delete ${todo.description}",
                        {
                            populateList()
                        },
                        {
                            listViewModel.retrieve(todo, oldStatus)
                            populateList()
                        }
                    )
                }

            }
        }
        val touchHelper = ItemTouchHelper(touchCallback)
        touchHelper.attachToRecyclerView(binding.listTodo)

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
        populateList()
        sendNotifications()
    }

    override fun onItemClick(todo: Todo) {
        val intent = Intent(this, TodoWriteActivity::class.java).apply {
            val json = Gson().toJson(todo)
            putExtra("todo", json)
        }
        startActivity(intent)
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

    private fun populateList() {
        val todos = listViewModel.fetchActive()
        if (todos.isEmpty()) {
            listAdapter.submitList(listOf(TodoListAdapter.TodoListItem.Empty))
        } else {
            val list = todos.map { todo ->
                TodoListAdapter.TodoListItem.Body(todo)
            }
            listAdapter.submitList(list)
        }
    }

    private fun sendNotifications() {
        val pending = listViewModel.fetchPending()
        alarmViewModel.startTimer(pending)
    }
}