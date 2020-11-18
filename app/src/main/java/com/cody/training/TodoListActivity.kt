package com.cody.training

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cody.training.databinding.ActivityTodoListBinding
import com.cody.training.ui.todo.TodoListAdapter
import com.cody.training.ui.todo.TodoListViewModel

class TodoListActivity : AppCompatActivity() {

    private lateinit var viewModel: TodoListViewModel

    private val viewManager = LinearLayoutManager(this)

    private val viewAdapter = TodoListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)

        val binding = DataBindingUtil.setContentView<ActivityTodoListBinding>(
            this,
            R.layout.activity_todo_list
        )

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.listTodo.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        binding.buttonAdd.setOnClickListener {
            val intent = Intent(this, TodoWriteActivity::class.java).apply {
                putExtra("todo", null as Parcelable?)
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val todos = viewModel.fetch()
        if (todos.isEmpty()) {
            viewAdapter.submitList(listOf(TodoListAdapter.TodoListItem.Empty))
        } else {
            val list = todos.map { todo ->
                TodoListAdapter.TodoListItem.Body(todo)
            }
            viewAdapter.submitList(list)
        }
    }
}