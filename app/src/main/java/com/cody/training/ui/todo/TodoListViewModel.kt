package com.cody.training.ui.todo

import androidx.lifecycle.ViewModel
import com.cody.training.model.Todo
import com.cody.training.utils.todoRepository
import io.realm.Realm

class TodoListViewModel : ViewModel() {

    private val realm: Realm = Realm.getDefaultInstance()

    fun fetch(): List<Todo> {
        return realm.copyFromRealm(realm.todoRepository().fetch())
    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }
}