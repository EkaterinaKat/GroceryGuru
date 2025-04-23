package com.katysh.groceryguru.util

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore

fun saveTextFileToDownloads(context: Context, text: String, fileName: String): Boolean {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, "$fileName.txt")
            put(MediaStore.Downloads.MIME_TYPE, "text/plain")
            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            resolver.openOutputStream(uri).use { outputStream ->
                outputStream?.write(text.toByteArray())
            }
            return true
        } else {
            return false
        }
    } else {
        return false
    }


}