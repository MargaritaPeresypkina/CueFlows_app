package com.example.cueflowsapp.main_screen.parcing

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.parcing.text_parsing.data.DynamicScreenObject
import com.example.cueflowsapp.ui.theme.TextDocsGray
import com.example.cueflowsapp.ui.theme.WhiteReturnButton

@SuppressLint("ConfigurationScreenWidthHeight")
//@Preview(showBackground = true)
@Composable
fun TextDocs(
    content: DynamicScreenObject,
    onNavigateToPreviousScreen: () -> Unit,

) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val horizontalSpacing = 37.dp
    val spaceBetweenButtons = 9.dp
    val buttonWidth = (screenWidthDp - (horizontalSpacing * 2 + spaceBetweenButtons))/2
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = content.image),
                contentDescription = "header_${content.screenName}",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .padding(top = 43.dp, start = 29.dp)
                    .background(WhiteReturnButton, shape = CircleShape)
                    .size(width = 55.dp, height = 55.dp)
                    .border(
                        width = 1.dp,
                        shape = CircleShape,
                        color = Color.Black
                    )
                    .clickable {
                        onNavigateToPreviousScreen()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "arrow",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.background_sign),
                contentDescription = "background_sign",
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = painterResource(id = R.drawable.back_sign_sacond),
                contentDescription = "background_sign",
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().offset(y = (230).dp),
            )

            Column(
                modifier = Modifier.fillMaxWidth().padding(start = 37.dp, end = 37.dp, top = 28.dp)
            ) {
                Text(
                    content.title,
                    color = Color(content.titleColor),
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    fontSize = 30.sp
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    "Please, choose the format you need",
                    color = TextDocsGray,
                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(9.dp))
                content.firstSubtitle?.let { subtitle ->
                    content.firstSubtitleColor?.let { subtitleColor ->
                        Text(
                            subtitle,
                            color = Color(subtitleColor),
                            fontFamily = FontFamily(Font(R.font.inter_semibold)),
                            fontSize = 22.sp
                        )
                    }
                }
                Spacer(Modifier.height(11.dp))
                val buttonsPerRow = 2
                Column(modifier = Modifier.fillMaxWidth()) {
                    content.firstButtons.chunked(buttonsPerRow).forEach { rowButtons ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            rowButtons.forEachIndexed { index, button ->
                                Button(
                                    onClick = {},
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
                                            fontSize = 26.sp,
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
                        if (rowButtons != content.firstButtons.chunked(buttonsPerRow).last()) {
                            Spacer(modifier = Modifier.height(spaceBetweenButtons))
                        }
                    }
                }
                content.firstDescription?.let { firstDesc ->
                    content.firstDescColor?.let { firstDescColor ->
                        Spacer(Modifier.height(9.dp))
                        Text(
                            firstDesc,
                            color = Color(firstDescColor),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.inter_regular))
                        )
                    }
                }
                content.secondSubtitle?.let { secondSubtitle ->
                    content.secondSubtitleColor?.let { secondSubColor ->
                        Spacer(Modifier.height(19.dp))
                        Text(
                            secondSubtitle,
                            color = Color(secondSubColor),
                            fontFamily = FontFamily(Font(R.font.inter_semibold)),
                            fontSize = 22.sp
                        )
                    }
                }
                Spacer(Modifier.height(11.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    content.secondButtons.chunked(buttonsPerRow).forEach { rowButtons ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            rowButtons.forEachIndexed { index, button ->
                                Button(
                                    onClick = {},
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
                                            fontSize = 26.sp,
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
                        if (rowButtons != content.secondButtons.chunked(buttonsPerRow).last()) {
                            Spacer(modifier = Modifier.height(spaceBetweenButtons))
                        }
                    }
                }
                content.secondDescription?.let { secondDesc ->
                    content.secondDescColor?.let { secondDescColor ->
                        Spacer(Modifier.height(9.dp))
                        Text(
                            secondDesc,
                            color = Color(secondDescColor),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.inter_regular))
                        )
                    }
                }
            }
        }
    }
}