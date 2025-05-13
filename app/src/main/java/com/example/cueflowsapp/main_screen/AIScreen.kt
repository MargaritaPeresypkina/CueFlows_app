package com.example.cueflowsapp.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.nio.file.WatchEvent

@Composable
fun AIScreen() {
    val auth = Firebase.auth
    val emailState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(top = 50.dp, bottom = 0.dp,  start = 20.dp, end = 20.dp)
    ){
        Text(
            "Assistant",
            fontSize = 24.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.inter_bold)),
            modifier = Modifier.padding(horizontal = 40.dp),
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                "Hi! Currently, our artificial intelligence assistant is under development. Follow the news. Thank you for your patience!",
                fontSize = 20.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(Font(R.font.inter_medium)),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }
    }
}