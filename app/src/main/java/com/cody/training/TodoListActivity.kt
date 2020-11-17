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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewManager = LinearLayoutManager(this)

        val viewAdapter = TodoListAdapter()

        val viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)

        viewModel.fetchResult.observe(this@TodoListActivity) { results ->
            if (results.size == 0) {
                viewAdapter.submitList(listOf(TodoListAdapter.TodoListItem.Empty))
            } else {
                val list = results.subList(0, results.size).map { todo ->
                    TodoListAdapter.TodoListItem.Body(todo)
                }
                viewAdapter.submitList(list)
            }
        }

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
}