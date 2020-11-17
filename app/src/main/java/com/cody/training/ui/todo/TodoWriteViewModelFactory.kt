package com.cody.training.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cody.training.model.Todo

class TodoWriteViewModelFactory(private val todo: Todo?) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoWriteViewModel::class.java)) {
            return TodoWriteViewModel(todo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}