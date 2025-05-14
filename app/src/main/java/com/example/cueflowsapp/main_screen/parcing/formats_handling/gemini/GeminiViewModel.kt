package com.example.cueflowsapp.network

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cueflowsapp.BuildConfig
import com.example.cueflowsapp.main_screen.parcing.formats_handling.gemini.GeminiRequest
import com.example.cueflowsapp.main_screen.parcing.formats_handling.gemini.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow

class GeminiViewModel : ViewModel() {
    private val apiKey = BuildConfig.GEMINI_API_KEY

    private val _summary = MutableStateFlow<String?>(null)
    val summary: StateFlow<String?> = _summary.asStateFlow()

    private val _keyTerms = MutableStateFlow<String?>(null)
    val keyTerms: StateFlow<String?> = _keyTerms.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun generateSummary(text: String) {
        viewModelScope.launch {
            try {
                val request = GeminiRequest(
                    contents = listOf(
                        GeminiRequest.Content(
                            parts = listOf(
                                GeminiRequest.Content.Part(
                                    text = "Составь краткое резюме следующего текста, если необходимо разделяй ответ на абзацы:\n$text"
                                )
                            )
                        )
                    )
                )
                val response = RetrofitClient.geminiApiService.generateContent(apiKey, request)
                val summaryText = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                _summary.value = summaryText ?: "No summary generated"
            } catch (e: Exception) {
                _error.value = "Error generating summary: ${e.localizedMessage}"
                Log.e("GeminiViewModel", "Summary error", e)
            }
        }
    }

    fun generateKeyTerms(text: String) {
        viewModelScope.launch {
            try {
                val request = GeminiRequest(
                    contents = listOf(
                        GeminiRequest.Content(
                            parts = listOf(
                                GeminiRequest.Content.Part(
                                    text = "Проанализируйте следующий текст и найдите слова, которые могут потребовать объяснения. Для каждого слова дай краткое определение. Оформите в виде списка, каждый элемент которого имеет вид: '- Слово - Определение».'. Если встретится аббревиатура, но не делай из неё определения, если из контекста не понятно, что она значит \n\nТекст:\n$text"
                                )
                            )
                        )
                    )
                )
                val response = RetrofitClient.geminiApiService.generateContent(apiKey, request)
                val keyTermsText = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                Log.d("GeminiViewModel", "Key Terms Response: $keyTermsText") // Add logging
                _keyTerms.value = keyTermsText ?: "No key terms generated"
            } catch (e: Exception) {
                _error.value = "Error generating key terms: ${e.localizedMessage}"
                Log.e("GeminiViewModel", "Key terms error", e)
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}