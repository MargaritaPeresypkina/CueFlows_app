package com.example.cueflowsapp.main_screen.parcing.formats_handling.data

sealed class TextDocsViewMode {
    object Original : TextDocsViewMode()
    object Summary : TextDocsViewMode()
    object KeyTerms : TextDocsViewMode()
}