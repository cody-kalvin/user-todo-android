package com.cody.training.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import com.cody.training.R

fun showAlert(activity: Activity, message: String, onDismiss: (() -> Unit)? = null) {
    val inflate = activity.layoutInflater.inflate(R.layout.dialog_alert, null as ViewGroup?)
    val textView = inflate.findViewById<TextView>(R.id.board)
    textView.text = message
    val builder = AlertDialog.Builder(ContextThemeWrapper(activity, R.style.AlertDialogTheme))
    builder.setView(inflate)
    builder.setPositiveButton("Ok") { _, _ ->
        onDismiss?.invoke()
    }
    val create = builder.create()
    create.setOnDismissListener { onDismiss?.invoke() }
    create.show()
}

fun showConfirmation(
    activity: Activity,
    message: String,
    onConfirm: (() -> Unit),
    onCancel: (() -> Unit)? = null
) {
    val inflate = activity.layoutInflater.inflate(R.layout.dialog_confirmation, null as ViewGroup?)
    val textView = inflate.findViewById<TextView>(R.id.board)
    textView.text = message
    val builder = AlertDialog.Builder(ContextThemeWrapper(activity, R.style.AlertDialogTheme))
    builder.setView(inflate)
    builder.setPositiveButton("Ok") { _, _ ->
        onConfirm.invoke()
    }
    builder.setNegativeButton("Cancel") { dialog, _ ->
        onCancel?.invoke()
        dialog.dismiss()
    }
    val create = builder.create()
    create.setOnDismissListener { onCancel?.invoke() }
    create.show()
}

fun showImagePicker(
    activity: Activity,
    openCameraCode: Int,
    openGalleryCode: Int,
    cameraOutput: Uri
) {
    val builder = AlertDialog.Builder(ContextThemeWrapper(activity, R.style.AlertDialogTheme))
    builder.setTitle("Upload your photo")

    val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery")
    builder.setItems(options) { _, which ->
        if (options[which] == "Take Photo") {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, cameraOutput)
            }
            takePicture.resolveActivity(activity.packageManager)?.also {
                activity.startActivityForResult(takePicture, openCameraCode)
            }
        } else {
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ).apply {
                type = "image/*"
                putExtra(MediaStore.EXTRA_OUTPUT, cameraOutput)
            }
            pickPhoto.resolveActivity(activity.packageManager)?.also {
                activity.startActivityForResult(pickPhoto, openGalleryCode)
            }
        }
    }

    builder.setNegativeButton("Cancel") { dialog, _ ->
        dialog.dismiss()
    }

    val dialog = builder.create()
    dialog.show()
}