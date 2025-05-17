package com.example.cueflowsapp.main_screen.parcing.formats_handling

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.data.DocumentListViewModel
import com.example.cueflowsapp.main_screen.data.DocumentModel
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.DocumentFormat
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.TextFileContent
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.data.TextDocsContent
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.parseTextDocsContent
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.readPdfFile
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.readTxtFile
import com.example.cueflowsapp.network.GeminiViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun SelectFileOptionScreen(
    documentId: String?,
    fileUri: String?,
    fileName: String,
    backgroundColor: Int,
    formatType: DocumentFormat,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: DocumentListViewModel = viewModel()
    val geminiViewModel: GeminiViewModel = viewModel()
    var savedDocumentId by remember { mutableStateOf(documentId) }
    var initialContent by remember { mutableStateOf<List<TextDocsContent>?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val summary by geminiViewModel.summary.collectAsState()
    val keyTerms by geminiViewModel.keyTerms.collectAsState()
    val geminiError by geminiViewModel.error.collectAsState()

    LaunchedEffect(fileUri) {
        if (fileUri != null && savedDocumentId == null) {
            isLoading = true
            try {
                val content = when (formatType) {
                    DocumentFormat.TXT -> listOf(TextDocsContent.Paragraph(readTxtFile(fileUri.toUri(), context)))
                    DocumentFormat.DOCX -> parseTextDocsContent(fileUri.toUri(), context)
                    DocumentFormat.PDF -> {
                        val pdfContent = readPdfFile(fileUri.toUri(), context)
                        buildList {
                            add(TextDocsContent.Paragraph(pdfContent.text))
                            pdfContent.images.forEach { img ->
                                add(TextDocsContent.Image(img.data, img.width, img.height))
                            }
                        }
                    }
                }
                initialContent = content
                val textContent = content.filterIsInstance<TextDocsContent.Paragraph>().joinToString("\n\n") { it.text }
                geminiViewModel.generateSummary(textContent)
                geminiViewModel.generateKeyTerms(textContent)
                val document = DocumentModel(
                    title = fileName,
                    content = textContent,
                    images = content.filterIsInstance<TextDocsContent.Image>().map {
                        DocumentModel.ImageData(it.data, it.width, it.height)
                    },
                    tables = content.filterIsInstance<TextDocsContent.Table>().map { Json.encodeToString(it.rows) },
                    format = formatType,
                    fileUri = fileUri,
                    backgroundColor = backgroundColor
                )
                savedDocumentId = viewModel.saveDocument(document)
            } catch (e: Exception) {
                errorMessage = "Error saving document: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        } else if (savedDocumentId != null && initialContent == null) {
            isLoading = true
            try {
                val document = viewModel.getDocumentById(savedDocumentId!!)
                if (document != null) {
                    initialContent = buildList {
                        if (document.content.isNotBlank()) {
                            add(TextDocsContent.Paragraph(document.content))
                        }
                        document.images.forEach { img ->
                            add(TextDocsContent.Image(img.toByteArray(), img.width, img.height))
                        }
                        document.tables.forEach { tableJson ->
                            try {
                                val table = Json.decodeFromString<List<List<String>>>(tableJson)
                                add(TextDocsContent.Table(table))
                            } catch (e: Exception) {
                                Log.e("SelectFileOptionScreen", "Error decoding table", e)
                            }
                        }
                    }
                    geminiViewModel.generateSummary(document.content)
                    geminiViewModel.generateKeyTerms(document.content)
                } else {
                    errorMessage = "Document not found"
                }
            } catch (e: Exception) {
                errorMessage = "Error loading document: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.14f)
                .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                .background(Color(backgroundColor))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, start = 15.dp, end = 15.dp, bottom = 25.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = onNavigateBack) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_white),
                        contentDescription = "arrow_white",
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = fileName,
                    fontFamily = FontFamily(Font(R.font.inter_semibold)),
                    fontSize = 20.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth().padding(top = 15.dp)) {
            if (errorMessage != null || geminiError != null) {
                Text(
                    text = errorMessage ?: geminiError ?: "",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            } else if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            } else {
                TextFileContent(
                    documentId = savedDocumentId ?: "",
                    initialContent = initialContent,
                    format = formatType.name.lowercase(),
                    summary = summary,
                    keyTerms = keyTerms,
                    geminiError = geminiError
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}