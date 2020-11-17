package com.cody.training.ui.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cody.training.databinding.ListItemEmptyBinding
import com.cody.training.databinding.ListItemTodoBinding
import com.cody.training.model.Todo

class TodoListAdapter :
    ListAdapter<TodoListAdapter.TodoListItem, RecyclerView.ViewHolder>(TodoDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item is TodoListItem.Empty) {
            (holder as EmptyViewHolder).bind("Nothing to do.")
        } else if (item is TodoListItem.Body) {
            (holder as TodoViewHolder).bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (TodoItemType.values()[viewType]) {
            TodoItemType.Empty -> EmptyViewHolder.from(parent)
            TodoItemType.Body -> TodoViewHolder.from(parent)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).itemType.ordinal

    class EmptyViewHolder(
        private val binding: ListItemEmptyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: String) {
            binding.message = message
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EmptyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemEmptyBinding.inflate(layoutInflater, parent, false)
                return EmptyViewHolder(binding)
            }
        }
    }

    class TodoViewHolder private constructor(
        private val binding: ListItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoListItem.Body) {
            binding.todo = item.todo
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TodoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTodoBinding.inflate(layoutInflater, parent, false)
                return TodoViewHolder(binding)
            }
        }
    }

    enum class TodoItemType {
        Empty,
        Body
    }

    sealed class TodoListItem(val itemType: TodoItemType) {
        object Empty : TodoListItem(TodoItemType.Empty)
        data class Body(val todo: Todo) : TodoListItem(TodoItemType.Body)
    }
}