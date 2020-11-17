package com.cody.training.utils

import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cody.training.R

//@BindingAdapter("todoWriteStrike")
//fun TextView.setTodoWriteStrike(status: String) {
//    val string = SpannableString(text)
//    val strikeThrough = StrikethroughSpan()
//    val blackColor = ForegroundColorSpan(ContextCompat.getColor(context, R.color.black))
//    val redCo
//
//    if (status == "done") {
//        string.setSpan(strikeThrough, 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//    } else {
//        string.removeSpan(strikeThrough)
//    }
//
//    setText(string)
//}

@BindingAdapter("todoWriteStatus")
fun ImageButton.setTodoWriteStatus(status: String) {
    setImageResource(
        if (status == "done") {
            R.drawable.ic_checkbox_checked
        } else {
            R.drawable.ic_checkbox_unchecked
        }
    )
}

