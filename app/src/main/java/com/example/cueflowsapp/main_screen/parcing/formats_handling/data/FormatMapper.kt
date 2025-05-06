package com.example.cueflowsapp.main_screen.parcing.formats_handling.data

object FormatMapper {
    fun String.toDocumentFormat(): DocumentFormat? {
        return when (this.lowercase()) {
            "txt" -> DocumentFormat.TXT
            "docx" -> DocumentFormat.DOCX
            "pdf" -> DocumentFormat.PDF
            else -> null
        }
    }
}