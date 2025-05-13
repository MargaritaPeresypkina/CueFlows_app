package com.example.cueflowsapp.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.data.DocumentListViewModel
import com.example.cueflowsapp.main_screen.data.DocumentModel
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.NavRoutes

@Composable
fun ListScreen(navController: NavHostController) {
    val viewModel: DocumentListViewModel = viewModel()
    val documents by viewModel.documents.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                .background(Color(0xFF7C3CB6))
        ){
            Text(
                "My list",
                fontSize = 24.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.inter_bold)),
                modifier = Modifier.padding(horizontal = 40.dp).padding(top = 40.dp, bottom = 20.dp)
            )
        }
        Spacer(Modifier.height(15.dp))
        if (documents.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                contentAlignment = Alignment.Center,

            ) {
                Text("No saved documents yet")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 30.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(documents) { document ->
                    DocumentItem(
                        document = document,
                        onClick = {
                            navController.navigate(
                                NavRoutes.DocumentViewer(
                                    fileUri = document.fileUri,
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
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(15.dp)),
        colors = CardDefaults.cardColors(Color(0xFFDBDEFF))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                document.title,
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.inter_semibold)),
            )
            Text(
                "Format: ${document.format.name}",
                fontSize = 12.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.inter_regular)),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}