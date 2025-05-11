package com.example.cueflowsapp.main_screen.parcing.formats_handling.data

data class GeminiRequest(
    val contents: List<Content>
) {
    data class Content(
        val parts: List<Part>
    )

    data class Part(
        val text: String
    )

    companion object {
        fun create(text: String): GeminiRequest {
            return GeminiRequest(
                contents = listOf(
                    Content(
                        parts = listOf(
                            Part(text = text)
                        )
                    )
                )
            )
        }
    }
}