package com.example.cueflowsapp.main_screen.parcing.formats_handling

import GeminiViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.cueflowsapp.main_screen.data.DocumentModel
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.DocumentFormat
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.TextFileContent
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.data.TextDocsContent
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.parseTextDocsContent
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.readPdfFile
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.readTxtFile


@Composable
fun SelectFileOptionScreen(
    fileUri: String,
    fileName: String,
    backgroundColor: Int,
    formatType: DocumentFormat,
    onNavigateBack: () -> Unit,
    onSaveDocument: (DocumentModel) -> Unit
) {
    val context = LocalContext.current
    var showSaveDialog by remember { mutableStateOf(false) }
    var documentContent by remember { mutableStateOf("") }
    var geminiResponse by remember { mutableStateOf("") }
    var showGeminiResponse by remember { mutableStateOf(false) }

    val viewModel: GeminiViewModel = viewModel()

    LaunchedEffect(viewModel.responseText.value) {
        if (viewModel.responseText.value.isNotEmpty()) {
            geminiResponse = viewModel.responseText.value
            showGeminiResponse = true
        }
    }

    LaunchedEffect(fileUri) {
        documentContent = when(formatType) {
            DocumentFormat.TXT -> readTxtFile(fileUri.toUri(), context)
            DocumentFormat.DOCX -> parseTextDocsContent(fileUri.toUri(), context)
                .filterIsInstance<TextDocsContent.Paragraph>()
                .joinToString("\n\n") { it.text }
            DocumentFormat.PDF -> {
                val pdfContent = readPdfFile(fileUri.toUri(), context)
                // Combine text and image information
                buildString {
                    append(pdfContent.text)
                    if (pdfContent.images.isNotEmpty()) {
                        append("\n\n[Contains ${pdfContent.images.size} images]")
                    }
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.14f)
                .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                .background(Color(backgroundColor))
        ){
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 50.dp, start = 15.dp, end = 15.dp, bottom = 25.dp ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(
                    onClick = onNavigateBack
                ) {
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
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.width(15.dp))
                Box(modifier = Modifier
                    .height(30.dp).width(70.dp)
                    .clickable{showSaveDialog = true}
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                    ,
                    contentAlignment = Alignment.Center,
                    ){
                    Text(
                        "Save",
                        color = Color(backgroundColor),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.inter_regular))
                    )
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth().padding(top = 15.dp)) {
            when (formatType) {
                DocumentFormat.TXT -> TextFileContent(
                    uri = fileUri.toUri(),
                    format = "txt",
                    onGeminiResponse = { response ->
                        viewModel.generateText("YOUR_API_KEY", response)
                    }
                )
                DocumentFormat.DOCX -> TextFileContent(
                    uri = fileUri.toUri(),
                    format = "docx",
                    onGeminiResponse = { response ->
                        viewModel.generateText("YOUR_API_KEY", response)
                    }
                )
                DocumentFormat.PDF -> TextFileContent(
                    uri = fileUri.toUri(),
                    format = "pdf",
                    onGeminiResponse = { response ->
                        viewModel.generateText("YOUR_API_KEY", response)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            title = { Text("Save Document") },
            text = { Text("Do you want to save this document to your list?") },
            confirmButton = {
                Button(
                    onClick = {
                        onSaveDocument(
                            DocumentModel(
                                title = fileName,
                                content = documentContent,
                                format = formatType,
                                fileUri = fileUri,
                                backgroundColor = backgroundColor
                            )
                        )
                        showSaveDialog = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showSaveDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
    // Gemini Response Dialog
    if (showGeminiResponse) {
        AlertDialog(
            onDismissRequest = { showGeminiResponse = false },
            title = { Text("Gemini AI Response") },
            text = { Text(geminiResponse) },
            confirmButton = {
                Button(onClick = { showGeminiResponse = false }) {
                    Text("OK")
                }
            }
        )
    }
}
