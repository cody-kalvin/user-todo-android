package com.cody.training.utils

import com.cody.training.data.RealmLiveData
import com.cody.training.data.todo.TodoRepository
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults

fun <T: RealmModel> RealmResults<T>.asLiveData() = RealmLiveData(this)

fun Realm.todoRepository(): TodoRepository = TodoRepository(this)