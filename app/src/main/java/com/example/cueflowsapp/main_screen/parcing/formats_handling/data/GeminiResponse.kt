package com.example.cueflowsapp.main_screen.parcing.formats_handling.data

data class GeminiResponse(
    val candidates: List<Candidate>?
) {
    data class Candidate(
        val content: Content?
    ) {
        data class Content(
            val parts: List<Part>?
        ) {
            data class Part(
                val text: String?
            )
        }
    }

    fun getFirstTextResponse(): String? {
        return candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
    }
}