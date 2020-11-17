@file:JvmName("BindingUtils")

package com.cody.training.utils

import androidx.databinding.InverseMethod
import com.cody.training.R

@InverseMethod("buttonIdToTodoNotify")
fun todoNotifyToButtonId(notify: Int): Int {
    return when (notify) {
        5 -> R.id.radio_five_min
        10 -> R.id.radio_ten_min
        else -> -1
    }
}

fun buttonIdToTodoNotify(selectedButtonId: Int): Int {
    return when (selectedButtonId) {
        R.id.radio_five_min -> 5
        R.id.radio_ten_min -> 10
        else -> 0
    }
}

@InverseMethod("checkedToTodoStatus")
fun todoStatusToChecked(status: String): Boolean {
    return status == "done"
}

fun checkedToTodoStatus(checked: Boolean): String {
    return if (checked) "done" else "pending"
}