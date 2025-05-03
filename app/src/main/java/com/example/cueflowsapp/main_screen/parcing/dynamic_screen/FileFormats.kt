package com.example.cueflowsapp.main_screen.parcing.dynamic_screen

object FileFormats {
    private val formatToMime = mapOf(
        "txt" to "text/plain",
        "rtf" to "application/rtf",
        "pdf" to "application/pdf",
        "docx" to "application/vnd.openxmlformats-officedocument.wordprocessingml.document",

        "youtube" to "video/mp4",
        "mp4" to "video/mp4",

        "jpeg" to "image/jpeg",
        "png" to "image/png",
        "jpg" to "image/jpeg",
        "gif" to "image/gif",
        "pdf_image" to "application/pdf",
        "svg" to "image/svg+xml",

        "epub" to "application/epub+zip",

        "xml" to "application/xml",
        "json" to "application/json",

        "zip" to "application/zip",
        "rar" to "application/rar",

        "html" to "text/html",

        "xlsx" to "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "gsheets" to "application/vnd.google-apps.spreadsheet",

        "aac" to "audio/aac",
        "mp3" to "audio/mpeg",
        "aiff" to "audio/x-aiff",
        "wav" to "audio/wav",

        "pptx_pdf" to "application/pdf",
        "pptx" to "application/vnd.openxmlformats-officedocument.presentationml.presentation"

    )

    fun getMimeType(extension: String) : String {
        return formatToMime[extension.lowercase()] ?: "*/*"
    }
}