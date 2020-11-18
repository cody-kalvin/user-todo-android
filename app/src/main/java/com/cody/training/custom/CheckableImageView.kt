package com.cody.training.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.cody.training.R

class CheckableImageView constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val imageDisplay: ImageView

    private var checked: Boolean = false
        set(value) {
            field = value

            val resource = if (value) {
                R.drawable.ic_checkbox_checked
            } else {
                R.drawable.ic_checkbox_unchecked
            }
            imageDisplay.setImageResource(resource)
        }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.view_checkable_imageview, this, true)
        imageDisplay = root.findViewById(R.id.image_display)
    }

    companion object {
        @BindingAdapter("checked")
        @JvmStatic
        fun setChecked(view: CheckableImageView, newValue: Boolean) {
            if (view.checked != newValue) {
                view.checked = newValue
            }
        }

        @InverseBindingAdapter(attribute = "checked")
        @JvmStatic
        fun getChecked(view: CheckableImageView): Boolean {
            return view.checked
        }

        @BindingAdapter("app:checkedAttrChanged")
        @JvmStatic
        fun setListeners(view: CheckableImageView, listener: InverseBindingListener?) {
            view.setOnClickListener {
                val checkableImageView = it as CheckableImageView
                checkableImageView.checked = !checkableImageView.checked
                listener?.onChange()
            }
        }
    }
}