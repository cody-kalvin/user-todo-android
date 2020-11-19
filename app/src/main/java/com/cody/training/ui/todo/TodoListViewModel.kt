package com.cody.training.ui.todo

import androidx.lifecycle.ViewModel
import com.cody.training.model.Todo
import com.cody.training.utils.todoRepository
import io.realm.Realm

class TodoListViewModel : ViewModel() {

    private val realm: Realm = Realm.getDefaultInstance()

    fun fetchActive(): List<Todo> {
        return realm.copyFromRealm(realm.todoRepository().fetchActive())
    }

    fun fetchPending(): List<Todo> {
        return realm.copyFromRealm(realm.todoRepository().fetchPending())
    }

    fun delete(todo: Todo) {
        todo.status = "deleted"
        realm.todoRepository().update(todo)
    }

    fun retrieve(todo: Todo, oldStatus: String) {
        todo.status = oldStatus
        realm.todoRepository().update(todo)
    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }
}