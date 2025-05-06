package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions

import android.content.Context
import android.net.Uri
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.data.TextDocsContent
import org.apache.poi.xwpf.usermodel.XWPFDocument

fun parserTextDocsContent(uri: Uri, context: Context): List<TextDocsContent> {
    return try {
        context.contentResolver.openInputStream(uri)?.use { stream ->
            XWPFDocument(stream).use { doc ->
                val content = mutableListOf<TextDocsContent>()

                // Параграфы
                doc.paragraphs.forEach { paragraph ->
                    if (paragraph.text.isNotBlank()) {
                        content.add(TextDocsContent.Paragraph(paragraph.text))
                    }
                }

                // Таблицы
                doc.tables.forEach { table ->
                    val rows = table.rows.map { row ->
                        row.tableCells.map { cell -> cell.text }
                    }
                    content.add(TextDocsContent.Table(rows))
                }

                // Изображения (отметка наличия)
                doc.allPictures.forEach { picture ->
                    content.add(TextDocsContent.Image(picture.data))
                }

                content
            }
        } ?: listOf(TextDocsContent.Paragraph("Failed to read DOCX file"))
    } catch (e: Exception) {
        listOf(TextDocsContent.Paragraph("Error reading DOCX file: ${e.localizedMessage}"))
    }
}