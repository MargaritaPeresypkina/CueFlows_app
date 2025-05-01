package com.example.cueflowsapp.main_screen.parcing.dynamic_screen

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

fun getFileNameFromUri(
    context: Context,
    uri: Uri
) : String {
    return context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        cursor.getString(nameIndex)
    } ?: "documentNothing.txt"
}