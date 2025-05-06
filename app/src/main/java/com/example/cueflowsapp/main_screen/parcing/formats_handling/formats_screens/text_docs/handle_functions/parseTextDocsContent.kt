package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.data.TextDocsContent
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import org.apache.poi.xwpf.usermodel.XWPFPictureData
import org.apache.poi.xwpf.usermodel.XWPFTable
import java.io.ByteArrayInputStream

fun parseTextDocsContent(uri: Uri, context: Context): List<TextDocsContent> {
    return try {
        context.contentResolver.openInputStream(uri)?.use { stream ->
            XWPFDocument(stream).use { doc ->
                val content = mutableListOf<TextDocsContent>()

                doc.bodyElements.forEach { element ->
                    when (element) {
                        is XWPFParagraph -> {
                            // Обрабатываем текст параграфа
                            if (element.text.isNotBlank()) {
                                content.add(TextDocsContent.Paragraph(element.text))
                            }

                            // Обрабатываем изображения внутри параграфа
                            element.runs.forEach { run ->
                                run.embeddedPictures.forEach { picture ->
                                    val options = BitmapFactory.Options().apply {
                                        inJustDecodeBounds = true
                                    }
                                    ByteArrayInputStream(picture.pictureData.data).use {
                                        BitmapFactory.decodeStream(it, null, options)
                                    }
                                    content.add(
                                        TextDocsContent.Image(
                                            data = picture.pictureData.data,
                                            width = options.outWidth,
                                            height = options.outHeight
                                        )
                                    )
                                }
                            }
                        }
                        is XWPFTable -> {
                            val rows = element.rows.map { row ->
                                row.tableCells.map { cell -> cell.text }
                            }
                            content.add(TextDocsContent.Table(rows))
                        }
                    }
                }

                content
            }
        } ?: listOf(TextDocsContent.Paragraph("Failed to read DOCX file"))
    } catch (e: Exception) {
        listOf(TextDocsContent.Paragraph("Error reading DOCX file: ${e.localizedMessage}"))
    }
}