package com.example.cueflowsapp.main_screen.parcing.formats_handling.gemini

import kotlinx.serialization.Serializable

@Serializable
data class GeminiResponse(
    val candidates: List<Candidate>
) {
    @Serializable
    data class Candidate(
        val content: Content
    ) {
        @Serializable
        data class Content(
            val parts: List<Part>,
            val role: String
        ) {
            @Serializable
            data class Part(
                val text: String
            )
        }
    }
}