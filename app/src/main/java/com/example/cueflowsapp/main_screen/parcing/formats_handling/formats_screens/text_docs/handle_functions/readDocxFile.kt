package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions

import android.content.Context
import android.net.Uri
import org.apache.poi.xwpf.usermodel.XWPFDocument
fun readDocxFile(uri: Uri, context: Context): String {
    return try {
        context.contentResolver.openInputStream(uri)?.use { stream ->
            XWPFDocument(stream).use { doc ->
                val result = StringBuilder()

                // Обработка параграфов
                doc.paragraphs.forEach { paragraph ->
                    result.append(paragraph.text).append("\n")
                }

                // Обработка таблиц
                doc.tables.forEach { table ->
                    result.append("\n[TABLE START]\n")
                    table.rows.forEach { row ->
                        row.tableCells.forEach { cell ->
                            result.append(cell.text).append("\t")
                        }
                        result.append("\n")
                    }
                    result.append("[TABLE END]\n")
                }

                result.toString()
            }
        } ?: "Failed to read DOCX file"
    } catch (e: Exception) {
        "Error reading DOCX file: ${e.localizedMessage}"
    }
}