package com.example.cueflowsapp.main_screen.parcing.formats_handling.data

class GeminiRepository {
    private val apiService = RetrofitClient.geminiApiService

    suspend fun generateText(
        apiKey: String,
        prompt: String
    ): String {
        val request = GeminiRequest.create(prompt)
        val response = apiService.generateContent(apiKey, request)

        return response.getFirstTextResponse()
            ?: throw Exception("Empty response from API")
    }
}