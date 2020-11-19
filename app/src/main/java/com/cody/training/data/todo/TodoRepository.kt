package com.cody.training.data.todo

import com.cody.training.model.Todo
import io.realm.Realm
import io.realm.RealmResults

class TodoRepository(private val realm: Realm) {

    fun insert(todo: Todo) {
        realm.executeTransactionAsync {
            it.insert(todo)
        }
    }

    fun update(todo: Todo) {
        realm.executeTransactionAsync {
            val row = it.where(Todo::class.java).equalTo("id", todo.id).findFirst()
            row?.apply {
                description = todo.description
                status = todo.status
                preview = todo.preview
                notify = todo.notify
            }
        }
    }

    fun fetchActive(): RealmResults<Todo> {
        return realm.where(Todo::class.java).notEqualTo("status", "deleted").findAllAsync()
    }

    fun fetchPending(): RealmResults<Todo> {
        return realm.where(Todo::class.java).equalTo("status", "pending").findAllAsync()
    }
}