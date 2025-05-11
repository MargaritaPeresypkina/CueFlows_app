package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.GeminiRequest
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.OptionButtonInfo
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.RetrofitClient
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.optionButtons
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.components.OptionButton
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.data.TextDocsContent
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.TextDocsTableView
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.parseTextDocsContent
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.readPdfFile
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.readTxtFile
import kotlinx.coroutines.launch


private const val MAX_TEXT_LENGTH = 1000
@Composable
fun TextFileContent(
    uri: Uri,
    format: String,
    modifier: Modifier = Modifier,
    onGeminiResponse: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var showGeminiDialog by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var showResponseDialog by remember { mutableStateOf(false) }
    var geminiResponseText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val content = remember {
        when(format) {
            "txt" -> listOf(TextDocsContent.Paragraph(readTxtFile(uri, context)))
            "docx" -> parseTextDocsContent(uri, context)
            "pdf" -> {
                val pdfContent = readPdfFile(uri, context)
                val items = mutableListOf<TextDocsContent>()
                items.add(TextDocsContent.Paragraph(pdfContent.text))
                pdfContent.images.forEach { image ->
                    items.add(TextDocsContent.Image(image.data, image.width, image.height))
                }
                items
            }
            else -> listOf(TextDocsContent.Paragraph("error in text format reading"))
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
            items(optionButtons + OptionButtonInfo(
                image = R.drawable.logo, // Add your Gemini icon
                text = "Ask Gemini",
                color = 0xFF4285F4.toInt(),
                textColor = 0xFFFFFFFF.toInt()
            )) { item ->
                OptionButton(
                    image = item.image,
                    text = item.text,
                    color = item.color,
                    textColor = item.textColor,
                    onClick = {
                        if (item.text == "Ask Gemini") {
                            selectedText = (content.firstOrNull() as? TextDocsContent.Paragraph)?.text ?: ""
                            showGeminiDialog = true
                        }
                    }
                )
            }
        }

        if (showGeminiDialog) {
            AlertDialog(
                onDismissRequest = {
                    focusManager.clearFocus()
                    showGeminiDialog = false
                },
                title = { Text("Ask Gemini") },
                text = {
                    Column {
                        Text("Document Text:")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = selectedText.take(500) + if (selectedText.length > 500) "..." else "",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = selectedText,
                            onValueChange = {
                                if (it.length <= MAX_TEXT_LENGTH) {
                                    selectedText = it
                                }
                            },
                            label = { Text("Ask about this text") },
                            supportingText = {
                                Text("${selectedText.length}/$MAX_TEXT_LENGTH",
                                    color = if (selectedText.length > MAX_TEXT_LENGTH) Color.Red else Color.Gray)
                            },
                            isError = selectedText.length > MAX_TEXT_LENGTH
                        )
                    }
                },
                confirmButton = {

                    Button(
                        onClick = {
                            focusManager.clearFocus()

                            // Добавьте проверку здесь
                            if (selectedText.length > MAX_TEXT_LENGTH) {
                                onGeminiResponse("Error: Text is too long (max $MAX_TEXT_LENGTH characters)")
                                return@Button
                            }


                            coroutineScope.launch {
                                try {
                                    val response = RetrofitClient.geminiApiService.generateContent(
                                        "YOUR_API_KEY",
                                        GeminiRequest.create(selectedText)
                                    )
                                    response.getFirstTextResponse()?.let {
                                        onGeminiResponse(it)
                                    }
                                } catch (e: Exception) {
                                    onGeminiResponse("Error: ${e.message}")
                                }
                                showGeminiDialog = false
                            }
                        }
                    ) {
                        Text("Submit")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            showGeminiDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }


        if (showResponseDialog) {
            AlertDialog(
                onDismissRequest = { showResponseDialog = false },
                title = { Text("Gemini Response") },
                text = { Text(geminiResponseText) },
                confirmButton = {
                    Button(onClick = { showResponseDialog = false }) {
                        Text("OK")
                    }
                }
            )
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

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 18.dp)
        ) {
            itemsIndexed(content) { index, item ->
                when (item) {
                    is TextDocsContent.Image -> {
                        FullWidthImage(image = item)
                        if (index < content.lastIndex && content[index + 1] !is TextDocsContent.Image) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                    is TextDocsContent.Paragraph -> {
                        Text(
                            text = item.text,
                            modifier = Modifier.padding(vertical = 4.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF6B6D78))
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

@Composable
fun FullWidthImage(image: TextDocsContent.Image) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val density = LocalDensity.current

    val bitmap = remember(image.data) {
        BitmapFactory.decodeByteArray(image.data, 0, image.data.size)
    }

    val imageHeight = remember(bitmap, screenWidth) {
        bitmap?.let {
            val aspectRatio = it.height.toFloat() / it.width.toFloat()
            (screenWidth.value * aspectRatio).dp
        } ?: 0.dp
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight)
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Document image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(
                "Could not load image",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}