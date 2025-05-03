package com.example.cueflowsapp.main_screen.parcing.dynamic_destinations

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.cueflowsapp.main_screen.parcing.dynamic_destinations.destination_screens.DocxFileContent
import com.example.cueflowsapp.main_screen.parcing.dynamic_destinations.destination_screens.PdfFileContent
import com.example.cueflowsapp.main_screen.parcing.dynamic_destinations.destination_screens.RtfFileContent
import com.example.cueflowsapp.main_screen.parcing.dynamic_destinations.destination_screens.TextFileContent


@Composable
fun DocumentViewerScreen(
    fileUri: String,
    fileName: String,
    backgroundColor: Int,
    formatType: DocumentFormat,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(backgroundColor))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = fileName,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            when (formatType) {
                DocumentFormat.TXT -> TextFileContent(fileUri.toUri())
                DocumentFormat.RTF -> RtfFileContent(fileUri.toUri())
                DocumentFormat.DOCX -> DocxFileContent(fileUri.toUri())
                DocumentFormat.PDF -> PdfFileContent(fileUri.toUri())
            }
        }
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}
