package com.cody.training.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cody.training.model.Todo
import com.cody.training.utils.todoRepository
import io.realm.Realm
import io.realm.RealmResults

class TodoListViewModel : ViewModel() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    val fetchResult: LiveData<RealmResults<Todo>> = realm.todoRepository().fetch()

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }
}