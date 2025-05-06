package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs

import android.content.Context
import android.net.Uri
import org.apache.poi.xwpf.usermodel.XWPFDocument

fun readDocxFile(uri: Uri, context: Context): String {
    return try {
        context.contentResolver.openInputStream(uri)?.use { stream ->
            XWPFDocument(stream).use { doc ->
                doc.paragraphs.joinToString("\n") { it.text }
            }
        } ?: "Failed to read DOCX file"
    } catch (e: Exception) {
        "Error reading DOCX file: ${e.localizedMessage}"
    }
}
