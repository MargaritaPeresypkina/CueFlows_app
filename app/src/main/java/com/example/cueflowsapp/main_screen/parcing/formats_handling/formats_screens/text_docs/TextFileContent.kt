package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs

import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.data.DocumentListViewModel
import com.example.cueflowsapp.main_screen.data.DocumentModel
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.OptionButtonInfo
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.optionButtons
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.components.OptionButton
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.data.TextDocsContent
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.TextDocsTableView
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.TextDocsImageContent
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import android.util.Log

@Composable
fun TextFileContent(
    documentId: String,
    initialContent: List<TextDocsContent>?,
    format: String,
    modifier: Modifier = Modifier
) {
    val viewModel: DocumentListViewModel = viewModel()
    var document by remember { mutableStateOf<DocumentModel?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(documentId) {
        if (documentId.isNotEmpty() && initialContent == null) {
            try {
                document = viewModel
                    .getDocumentById(documentId)
                if (document == null) {
                    errorMessage = "Document not found in database"
                }
            } catch (e: Exception) {
                errorMessage = "Error loading document: ${e.localizedMessage}"
            }
        }
    }

    val content = remember(document, initialContent) {
        when {
            initialContent != null -> initialContent
            document != null -> {
                buildList {
                    if (document!!.content.isNotBlank()) {
                        add(TextDocsContent.Paragraph(document!!.content))
                    }
                    document!!.images.forEach { img ->
                        try {
                            val imageData = img.toByteArray()
                            add(TextDocsContent.Image(imageData, img.width, img.height))
                        } catch (e: Exception) {
                            errorMessage = "Error decoding image: ${e.localizedMessage}"
                        }
                    }
                    document!!.tables.forEach { tableJson ->
                        try {
                            val table = Json.decodeFromString<List<List<String>>>(tableJson)
                            add(TextDocsContent.Table(table))
                        } catch (e: Exception) {
                            Log.e("TextFileContent", "Error decoding table", e)
                            errorMessage = "Error decoding table: ${e.localizedMessage}"
                        }
                    }
                }
            }
            else -> listOf(TextDocsContent.Paragraph("Loading document..."))
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent),
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            contentPadding = PaddingValues(start = 10.dp)
        ) {
            items(optionButtons) { item ->
                OptionButton(
                    image = item.image,
                    text = item.text,
                    color = item.color,
                    textColor = item.textColor
                )
            }
        }
        Spacer(Modifier.height(30.dp))
        Text(
            "Original",
            fontFamily = FontFamily(Font(R.font.inter_semibold)),
            color = Color(0xFF343434),
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 18.dp)
        )
        Spacer(Modifier.height(13.dp))

        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 18.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(horizontal = 18.dp)
            ) {
                itemsIndexed(content) { index, item ->
                    when (item) {
                        is TextDocsContent.Image -> {
                            TextDocsImageContent(image = item)
                            if (index < content.lastIndex && content[index + 1] !is TextDocsContent.Image) {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }

                        is TextDocsContent.Paragraph -> {
                            Text(
                                text = item.text,
                                modifier = Modifier.padding(vertical = 4.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(
                                        0xFF6B6D78
                                    )
                                )
                            )
                            if (index < content.lastIndex) {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }

                        is TextDocsContent.Table -> {
                            TextDocsTableView(table = item)
                            if (index < content.lastIndex) {
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}