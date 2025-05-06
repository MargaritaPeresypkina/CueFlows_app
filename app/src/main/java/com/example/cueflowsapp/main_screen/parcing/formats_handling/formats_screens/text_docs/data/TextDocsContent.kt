package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.data


sealed class TextDocsContent {
    data class Paragraph(val text: String) : TextDocsContent()
    data class Table(val rows: List<List<String>>) : TextDocsContent()
    data class Image(val data: ByteArray, val width: Int, val height: Int) : TextDocsContent() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as Image
            return data.contentEquals(other.data)
        }
        override fun hashCode(): Int = data.contentHashCode()
    }
}
