package com.example.cueflowsapp.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.example.cueflowsapp.ui.theme.BeidgeButtonContent
import com.example.cueflowsapp.ui.theme.PurpleButton

@Composable
fun LoginButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick ={onClick()},
        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp).height(63.dp),
        colors = ButtonColors(
            containerColor = PurpleButton,
            contentColor = BeidgeButtonContent,
            disabledContainerColor = PurpleButton,
            disabledContentColor = BeidgeButtonContent
        )
    ){
        Text(
            "$text",
            fontFamily = FontFamily(Font(R.font.inter_semibold)),
            fontSize = 14.sp,
            letterSpacing = 0.5.sp
        )
    }

}