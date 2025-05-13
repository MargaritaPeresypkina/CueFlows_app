package com.example.cueflowsapp.main_screen.parcing.formats_handling

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
    var savedDocumentId by remember { mutableStateOf(documentId) }
    var initialContent by remember { mutableStateOf<List<TextDocsContent>?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(fileUri) {
        if (fileUri != null && savedDocumentId == null) {
            try {
                // Initial load: read from file and save to database
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
                val document = DocumentModel(
                    title = fileName,
                    content = content.filterIsInstance<TextDocsContent.Paragraph>().joinToString("\n\n") { it.text },
                    images = content.filterIsInstance<TextDocsContent.Image>().map {
                        DocumentModel.ImageData(it.data, it.width, it.height)
                    },
                    tables = content.filterIsInstance<TextDocsContent.Table>().map { it.rows },
                    format = formatType,
                    fileUri = fileUri,
                    backgroundColor = backgroundColor
                )
                savedDocumentId = viewModel.saveDocument(document)
            } catch (e: Exception) {
                errorMessage = "Error saving document: ${e.localizedMessage}"
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
            if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                TextFileContent(
                    documentId = savedDocumentId ?: "",
                    initialContent = initialContent,
                    format = formatType.name.lowercase(),
                    onGeminiResponse = { /* Handle Gemini response if needed */ }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
