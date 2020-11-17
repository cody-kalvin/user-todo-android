package com.cody.training.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.cody.training.R

@BindingAdapter("todoStrike")
fun TextView.setTodoStrike(status: String) {
    val string = SpannableString(text)
    val strike = StrikethroughSpan()
    val black = ForegroundColorSpan(ContextCompat.getColor(context, R.color.black))
    val gray = ForegroundColorSpan(ContextCompat.getColor(context, R.color.gray_600))

    if (status == "done") {
        string.apply {
            setSpan(strike, 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(gray, 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    } else {
        string.apply {
            removeSpan(strike)
            setSpan(black, 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    text = string
}

@BindingAdapter("todoTime")
fun TextView.setTodoTime(status: String) {
    visibility = if (status == "done") GONE else VISIBLE
}

@BindingAdapter("todoPreview")
fun ImageView.setTodoPreview(preview: String?) {
    visibility = if (preview != null) VISIBLE else GONE

    Glide
        .with(context)
        .load(preview)
        .transform(CenterCrop(), RoundedCorners(8))
        .into(this)
}

