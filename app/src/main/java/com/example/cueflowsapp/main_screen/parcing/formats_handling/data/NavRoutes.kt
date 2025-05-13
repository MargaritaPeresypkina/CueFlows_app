package com.example.cueflowsapp.main_screen.parcing.formats_handling.data

import kotlinx.serialization.Serializable

sealed interface NavRoutes {
    @Serializable
    data class DocumentViewer(
        val documentId: String? = null,
        val fileUri: String? = null,
        val fileName: String,
        val backgroundColor: Int,
        val formatType: DocumentFormat
    ) : NavRoutes
}

@Serializable
enum class DocumentFormat {
    TXT, DOCX, PDF
}