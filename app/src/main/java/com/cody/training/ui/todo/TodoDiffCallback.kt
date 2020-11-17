package com.cody.training.ui.todo

import androidx.recyclerview.widget.DiffUtil
import com.cody.training.ui.todo.TodoListAdapter.TodoListItem

class TodoDiffCallback : DiffUtil.ItemCallback<TodoListItem>() {

    override fun areItemsTheSame(oldItem: TodoListItem, newItem: TodoListItem): Boolean {
        return if (oldItem is TodoListItem.Body && newItem is TodoListItem.Body) {
            oldItem.todo.id == newItem.todo.id
        } else {
            oldItem is TodoListItem.Empty && newItem is TodoListItem.Empty
        }
    }

    override fun areContentsTheSame(oldItem: TodoListItem, newItem: TodoListItem): Boolean {
        return if (oldItem is TodoListItem.Body && newItem is TodoListItem.Body) {
            oldItem.todo == newItem.todo
        } else {
            oldItem is TodoListItem.Empty && newItem is TodoListItem.Empty
        }
    }
}