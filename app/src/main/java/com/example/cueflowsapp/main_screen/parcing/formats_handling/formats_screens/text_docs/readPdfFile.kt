package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs

import android.content.Context
import android.net.Uri
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper


fun readPdfFile(uri: Uri, context: Context): String {
    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            PDDocument.load(inputStream).use { document ->
                val stripper = PDFTextStripper()
                stripper.getText(document)
            }
        } ?: "Failed to read PDF file"
    } catch (e: Exception) {
        "Error reading PDF file: ${e.localizedMessage}"
    }
}