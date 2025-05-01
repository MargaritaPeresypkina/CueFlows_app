package com.example.cueflowsapp.main_screen.parcing.dynamic_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.parcing.dynamic_screen.data.ActionButton
import kotlin.collections.chunked
import kotlin.collections.forEachIndexed

@Composable
fun ButtonsBlock(buttons: List<ActionButton>,
                 buttonWidth: Dp,
                 spaceBetweenButtons: Dp,
                 onButtonClick: (ActionButton) -> Unit) {
    val buttonsPerRow = 2

    Column(modifier = Modifier.fillMaxWidth()) {
        buttons.chunked(buttonsPerRow).forEachIndexed { i, rowButtons ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                rowButtons.forEachIndexed { index, button ->
                    Button(
                        onClick = { onButtonClick(button) },
                        modifier = Modifier
                            .width(buttonWidth)
                            .height(60.dp)
                            .shadow(elevation = 20.dp),
                        colors = ButtonColors(
                            containerColor = Color(button.backColor),
                            contentColor = Color.White,
                            disabledContainerColor = Color(button.backColor),
                            disabledContentColor = Color.White
                        ),
                        shape = RoundedCornerShape(11.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                button.text,
                                fontSize = if(button.text == "YouTube") 20.sp else 26.sp,
                                fontFamily = FontFamily(Font(R.font.inter_semibold))
                            )
                            Image(
                                painter = painterResource(id = R.drawable.button_information),
                                contentDescription = "button_information",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    if (index < rowButtons.size - 1) {
                        Spacer(modifier = Modifier.width(spaceBetweenButtons))
                    }
                }
            }
            if (i < buttons.chunked(buttonsPerRow).size - 1) {
                Spacer(modifier = Modifier.height(spaceBetweenButtons))
            }
        }
    }

}