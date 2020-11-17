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

    var checked: Boolean = false

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.view_checkable_imageview, this, true)
        imageDisplay = root.findViewById(R.id.image_display)
        imageDisplay.setOnClickListener {
            setChecked(this@CheckableImageView, !checked)
        }
    }

    companion object {
        @BindingAdapter("checked")
        @JvmStatic
        fun setChecked(view: CheckableImageView, newValue: Boolean) {
            if (view.checked != newValue) {
                view.checked = newValue

                val resource = if (view.checked) {
                    R.drawable.ic_checkbox_checked
                } else {
                    R.drawable.ic_checkbox_unchecked
                }
                view.imageDisplay.setImageResource(resource)
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
            if (listener != null && !view.isInEditMode) {
                val resource = if (view.checked) {
                    R.drawable.ic_checkbox_checked
                } else {
                    R.drawable.ic_checkbox_unchecked
                }
                view.imageDisplay.setImageResource(resource)
            }
        }
    }
}