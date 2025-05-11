package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPage
import com.tom_roush.pdfbox.pdmodel.PDResources
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.ByteArrayOutputStream
import java.io.IOException

object PdfReaderHelper {
    private var isInitialized = false

    fun initialize(context: Context) {
        if (!isInitialized) {
            PDFBoxResourceLoader.init(context)
            isInitialized = true
        }
    }
}

data class PdfContent(
    val text: String,
    val images: List<PdfImage>
)

data class PdfImage(
    val data: ByteArray,
    val width: Int,
    val height: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PdfImage
        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int = data.contentHashCode()
}

fun readPdfFile(uri: Uri, context: Context, maxPages: Int = 50): PdfContent {
    PdfReaderHelper.initialize(context)

    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            PDDocument.load(inputStream).use { document ->
                when {
                    document.isEncrypted -> PdfContent(
                        text = "PDF is encrypted and cannot be read",
                        images = emptyList()
                    )
                    document.numberOfPages == 0 -> PdfContent(
                        text = "PDF contains no pages",
                        images = emptyList()
                    )
                    document.numberOfPages > maxPages -> PdfContent(
                        text = "PDF is too large (over $maxPages pages)",
                        images = emptyList()
                    )
                    else -> {
                        // Extract text
                        val stripper = PDFTextStripper()
                        stripper.setSortByPosition(true)
                        stripper.setStartPage(1)
                        stripper.setEndPage(document.numberOfPages.coerceAtMost(maxPages))
                        val text = stripper.getText(document)

                        // Extract images
                        val images = mutableListOf<PdfImage>()
                        for (pageNum in 0 until document.numberOfPages.coerceAtMost(maxPages)) {
                            val page = document.getPage(pageNum)
                            extractImagesFromPage(page, images)
                        }

                        PdfContent(text, images)
                    }
                }
            }
        } ?: PdfContent(
            text = "Failed to open PDF file",
            images = emptyList()
        )
    } catch (e: Exception) {
        Log.e("PDFReader", "Error reading PDF", e)
        PdfContent(
            text = "Error reading PDF: ${e.localizedMessage}",
            images = emptyList()
        )
    }
}

private fun extractImagesFromPage(page: PDPage, images: MutableList<PdfImage>) {
    try {
        val resources: PDResources = page.resources ?: return

        for (xObjectName in resources.xObjectNames) {
            val xObject = resources.getXObject(xObjectName)
            if (xObject is PDImageXObject) {
                try {
                    // Get image dimensions
                    val width = xObject.width
                    val height = xObject.height

                    // Get the raw image data
                    val inputStream = xObject.createInputStream()
                    val byteArray = inputStream.readBytes()
                    inputStream.close()

                    // Basic validation
                    if (byteArray.isNotEmpty() && width > 0 && height > 0) {
                        images.add(PdfImage(byteArray, width, height))
                    }
                } catch (e: Exception) {
                    Log.e("PDFImageExtractor", "Error processing image", e)
                }
            }
        }
    } catch (e: Exception) {
        Log.e("PDFImageExtractor", "Error extracting images from page", e)
    }
}