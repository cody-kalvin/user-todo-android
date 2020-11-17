package com.cody.training.data.todo

import androidx.lifecycle.LiveData
import com.cody.training.model.Todo
import com.cody.training.utils.asLiveData
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class TodoRepository(private val realm: Realm) {

    fun insert(todo: Todo) {
        realm.executeTransactionAsync {
            it.insert(todo)
        }
    }

    fun update(todo: Todo) {
        realm.executeTransactionAsync {
            val row = it.where<Todo>().equalTo("id", todo.id).findFirst()
            row?.apply {
                description = todo.description
                status = todo.status
                preview = todo.preview
                notify = todo.notify
            }
        }
    }

    fun fetch(): LiveData<RealmResults<Todo>> {
        return realm.where(Todo::class.java).findAllAsync().asLiveData()
    }
}