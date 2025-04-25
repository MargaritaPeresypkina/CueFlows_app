package com.example.cueflowsapp.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.example.cueflowsapp.ui.theme.Grey21
import com.example.cueflowsapp.ui.theme.GreyBackField2
import com.example.cueflowsapp.ui.theme.LightBlack2

@Composable
fun RoundedCornerTextField(
    text: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = {onValueChange(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 17.dp)
            .background(
                color = GreyBackField2,
                shape = RoundedCornerShape(15.dp)
            )
            .height(63.dp),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.instrument_sans_regular)),
            letterSpacing = 0.5.sp
        ),
        label= {
            Text(
                text = label,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.instrument_sans_regular)),
                letterSpacing = 0.5.sp
            )
        },
        shape = RoundedCornerShape(15.dp),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = LightBlack2,
            focusedTextColor = LightBlack2,
            unfocusedTextColor = LightBlack2,
            unfocusedLabelColor = Grey21

        )
    )

}