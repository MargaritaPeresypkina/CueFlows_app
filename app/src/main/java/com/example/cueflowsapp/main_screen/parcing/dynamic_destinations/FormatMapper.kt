package com.example.cueflowsapp.main_screen.parcing.dynamic_destinations

object FormatMapper {
    fun String.toDocumentFormat(): DocumentFormat? {
        return when (this.lowercase()) {
            "txt" -> DocumentFormat.TXT
            "rtf" -> DocumentFormat.RTF
            "docx" -> DocumentFormat.DOCX
            "pdf" -> DocumentFormat.PDF
            else -> null
        }
    }
}