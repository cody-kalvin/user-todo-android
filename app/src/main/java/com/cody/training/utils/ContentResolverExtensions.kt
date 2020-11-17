package com.cody.training.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore

fun ContentResolver.getFileName(fileUri: Uri): String {
    this.query(fileUri, null, null, null, null)?.use { cursor ->
        val columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        val string = cursor.getString(columnIndex)
        cursor.close()
        return string
    }
    return ""
}