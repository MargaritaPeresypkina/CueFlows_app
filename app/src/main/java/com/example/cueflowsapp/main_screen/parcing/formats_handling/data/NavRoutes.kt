package com.example.cueflowsapp.main_screen.parcing.formats_handling.data

import kotlinx.serialization.Serializable

sealed interface NavRoutes {
//    @Serializable
//    data object StartScreen : NavRoutes

    @Serializable
    data class DocumentViewer(
        val fileUri: String,
        val fileName: String,
        val backgroundColor: Int,
        val formatType: DocumentFormat
    ) : NavRoutes
}

@Serializable
enum class DocumentFormat {
    TXT, DOCX, PDF
}