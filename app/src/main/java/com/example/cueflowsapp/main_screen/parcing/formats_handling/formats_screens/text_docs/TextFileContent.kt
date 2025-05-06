package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs

import android.net.Uri
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
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
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
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.TextDocsTableView
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.parserTextDocsContent
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.readPdfFile
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions.readTxtFile

@Composable
fun TextFileContent(uri: Uri, format: String) {
    val context = LocalContext.current
    val content = remember {
        when(format) {
            "txt" -> listOf(TextDocsContent.Paragraph(readTxtFile(uri, context)))
            "docx" -> parserTextDocsContent(uri, context)
            "pdf" -> listOf(TextDocsContent.Paragraph(readPdfFile(uri, context)))
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
            items(content) { item ->
                when (item) {
                    is TextDocsContent.Paragraph -> {
                        Text(
                            item.text,
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            color = Color(0xFF6B6D78),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    is TextDocsContent.Table -> {
                        TextDocsTableView(item)
                        Spacer(Modifier.height(16.dp))
                    }
                    is TextDocsContent.Image -> {
                        Text(
                            "[Image content]",
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            color = Color(0xFF6B6D78),
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
            item{
                Spacer(Modifier.height(30.dp))
            }
        }
    }
}
