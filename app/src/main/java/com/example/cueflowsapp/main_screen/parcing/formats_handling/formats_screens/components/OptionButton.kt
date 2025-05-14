package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R

@Composable
fun OptionButton(
    image: Int,
    text: String,
    color: Int,
    textColor: Int,
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(27.dp))
            .background(color = Color(color))
    ){
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = image),
                contentDescription = text,
                modifier = Modifier.size(22.dp)
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text,
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.inter_medium)),
                color = Color(textColor)
            )
        }
    }
}