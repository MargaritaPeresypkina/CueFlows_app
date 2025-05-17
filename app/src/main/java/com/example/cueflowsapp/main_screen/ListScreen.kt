package com.example.cueflowsapp.main_screen

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.data.DocumentListViewModel
import com.example.cueflowsapp.main_screen.data.DocumentModel
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.DocumentFormat
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.NavRoutes
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ListScreen(navController: NavHostController) {
    val viewModel: DocumentListViewModel = viewModel()
    val documents by viewModel.documents.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredDocuments = documents.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp))
                .background(
                    brush = androidx.compose.ui.graphics.Brush.linearGradient(
                        colors = listOf(Color(0xFF7C3CB6), Color(0xFF5B2A9D))
                    )
                )
        ) {
            Text(
                "My Documents",
                fontSize = 26.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.inter_bold)),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 24.dp)
            )
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .background(Color.White),
            placeholder = { Text("Search documents...") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.search_ic),
                    contentDescription = "Search",
                    tint = Color(0xFF6B6D78),
                    modifier = Modifier.size(20.dp)
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF7C3CB6),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(Modifier.height(8.dp))

        if (filteredDocuments.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (searchQuery.isEmpty()) "No saved documents yet" else "No documents found",
                    color = Color(0xFF6B6D78),
                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredDocuments) { document ->
                    DocumentItem(
                        document = document,
                        onClick = {
                            navController.navigate(
                                NavRoutes.DocumentViewer(
                                    documentId = document.id,
                                    fileUri = null,
                                    fileName = document.title,
                                    backgroundColor = document.backgroundColor,
                                    formatType = document.format
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DocumentItem(document: DocumentModel, onClick: () -> Unit) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(Date(document.createdAt))

    val thumbnailBitmap = remember(document.images) {
        if (document.images.isNotEmpty()) {
            try {
                Base64.decode(document.images.first().data, Base64.DEFAULT).let { bytes ->
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                }
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                when {
                    thumbnailBitmap != null -> {
                        Image(
                            bitmap = thumbnailBitmap.asImageBitmap(),
                            contentDescription = "Document thumbnail",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    document.format == DocumentFormat.PDF -> {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_textdocs),
                            contentDescription = "PDF Icon",
                            tint = Color(0xFFD32F2F),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    document.format == DocumentFormat.DOCX -> {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_textdocs),
                            contentDescription = "DOCX Icon",
                            tint = Color(0xFF1976D2),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    else -> {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_textdocs),
                            contentDescription = "TXT Icon",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = document.title,
                    fontSize = 18.sp,
                    color = Color(0xFF1A1A1A),
                    fontFamily = FontFamily(Font(R.font.inter_semibold)),
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Format: ${document.format.name}",
                    fontSize = 12.sp,
                    color = Color(0xFF6B6D78),
                    fontFamily = FontFamily(Font(R.font.inter_regular))
                )
                Text(
                    text = "Created: $formattedDate",
                    fontSize = 12.sp,
                    color = Color(0xFF6B6D78),
                    fontFamily = FontFamily(Font(R.font.inter_regular))
                )
                if (document.images.isNotEmpty()) {
                    Text(
                        text = "Images: ${document.images.size}",
                        fontSize = 12.sp,
                        color = Color(0xFF6B6D78),
                        fontFamily = FontFamily(Font(R.font.inter_regular))
                    )
                }
                if (document.tables.isNotEmpty()) {
                    Text(
                        text = "Tables: ${document.tables.size}",
                        fontSize = 12.sp,
                        color = Color(0xFF6B6D78),
                        fontFamily = FontFamily(Font(R.font.inter_regular))
                    )
                }
            }

            Icon(
                painter = painterResource(id = R.drawable.row),
                contentDescription = "Open document",
                tint = Color(0xFF7C3CB6),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
