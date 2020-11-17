package com.cody.training.ui.todo

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cody.training.model.Todo
import com.cody.training.utils.todoRepository
import io.realm.Realm

class TodoWriteViewModel(todo: Todo?) : ViewModel() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    private val entry: Todo = todo ?: Todo()

    private val isEdit = todo != null

    val id: MutableLiveData<String> = MutableLiveData(entry.id)

    val description: MutableLiveData<String> = MutableLiveData(entry.description)

    val status: MutableLiveData<String> = MutableLiveData(entry.status)

    val preview: MutableLiveData<String?> = MutableLiveData(entry.preview)

    val notify: MutableLiveData<Int> = MutableLiveData(entry.notify)

    val formValid: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(description) {
            value = isFormValid()
        }
        addSource(status) {
            value = isFormValid()
        }
        addSource(notify) {
            value = isFormValid()
        }
    }

    private fun isFormValid(): Boolean {
        return !description.value.isNullOrBlank() && notify.value!! > 0
    }

    fun saveTodo() {
        val todo = Todo(
            id.value!!,
            description.value!!,
            status.value!!,
            preview.value,
            notify.value!!
        )

        if (!isEdit) {
            realm.todoRepository().insert(todo)
        } else {
            realm.todoRepository().update(todo)
        }
    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }
}