package com.example.cueflowsapp.main_screen.parcing.formats_handling.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.data.TextDocsContent
import io.shubham0204.text2summary.Text2Summary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TextFileViewModel : ViewModel() {
    private var fullText: String = ""
    var summary: String = ""
    var keyTerms: String = ""

    fun processContent(content: List<TextDocsContent>) {
        viewModelScope.launch {
            // Extract all text paragraphs
            fullText = content.filterIsInstance<TextDocsContent.Paragraph>()
                .joinToString("\n\n") { it.text }

            // Generate summary
            summary = withContext(Dispatchers.IO) {
                if (fullText.isNotEmpty()) {
                    Text2Summary.summarize(fullText, compressionRate = 0.3f)
                } else {
                    "No text to summarize"
                }
            }
        }
    }
}