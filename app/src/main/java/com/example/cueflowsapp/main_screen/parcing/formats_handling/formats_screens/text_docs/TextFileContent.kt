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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.optionButtons
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.components.OptionButton
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.data.TextDocsContent
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.TextDocsImageContent
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.TextDocsTableView
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.parseTextDocsContent
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.readPdfFile
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.readTxtFile

@Composable
fun TextFileContent(uri: Uri, format: String) {
    val context = LocalContext.current
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