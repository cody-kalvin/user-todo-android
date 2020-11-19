package com.cody.training.utils

import com.cody.training.data.todo.TodoRepository
import io.realm.Realm

fun Realm.todoRepository(): TodoRepository = TodoRepository(this)