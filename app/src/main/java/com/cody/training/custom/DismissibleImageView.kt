package com.cody.training.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.cody.training.R

class DismissibleImageView constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val imageDisplay: ImageView

    private val buttonDismiss: ImageButton

    var src: String? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.view_dismissible_imageview, this, true)

        imageDisplay = root.findViewById(R.id.image_display)

        buttonDismiss = root.findViewById<ImageButton>(R.id.button_dismiss).apply {
            setOnClickListener {
                setSrc(this@DismissibleImageView, null)
            }
        }
    }

    companion object {
        @BindingAdapter("src")
        @JvmStatic
        fun setSrc(view: DismissibleImageView, newValue: String?) {
            if (view.src != newValue) {
                view.src = newValue

                Glide
                    .with(view.context)
                    .applyDefaultRequestOptions(
                        RequestOptions()
                            .placeholder(R.drawable.ic_insert_photo)
                            .error(R.drawable.ic_insert_photo)
                    )
                    .load(view.src)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(view.imageDisplay)

                view.visibility = if (view.src != null) VISIBLE else GONE
            }
        }

        @InverseBindingAdapter(attribute = "src")
        @JvmStatic
        fun getSrc(view: DismissibleImageView): String? {
            return view.src
        }

        @BindingAdapter("app:srcAttrChanged")
        @JvmStatic
        fun setListeners(view: DismissibleImageView, listener: InverseBindingListener?) {
            if (listener != null && !view.isInEditMode) {
                Glide
                    .with(view.context)
                    .applyDefaultRequestOptions(
                        RequestOptions()
                            .placeholder(R.drawable.ic_insert_photo)
                            .error(R.drawable.ic_insert_photo)
                    )
                    .load(view.src)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(view.imageDisplay)

                view.visibility = if (view.src != null) VISIBLE else GONE
            }
        }
    }
}