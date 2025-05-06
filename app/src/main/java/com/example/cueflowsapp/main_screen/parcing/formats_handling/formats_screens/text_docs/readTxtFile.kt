package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs

import android.content.Context
import android.net.Uri
import java.nio.charset.Charset

fun readTxtFile(uri: Uri, context: Context): String {
    return try {
        context.contentResolver.openInputStream(uri)?.use { stream ->
            val bytes = stream.readBytes()
            val charset = detectCharset(bytes) ?: Charsets.UTF_8
            String(bytes, charset)
        } ?: "Failed to read file"
    } catch (e: Exception) {
        "Error reading file: ${e.localizedMessage}"
    }
}

private fun detectCharset(bytes: ByteArray): Charset? {
    return try {
        if (bytes.size >= 3 && bytes[0] == 0xEF.toByte() &&
            bytes[1] == 0xBB.toByte() && bytes[2] == 0xBF.toByte()) {
            return Charsets.UTF_8
        }
        Charset.defaultCharset()
    } catch (e: Exception) {
        null
    }
}