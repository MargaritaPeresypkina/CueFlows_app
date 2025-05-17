package com.example.cueflowsapp.main_screen.parcing.dynamic_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.example.cueflowsapp.ui.theme.DarkGrey1
import com.example.cueflowsapp.ui.theme.UploadButton
import com.example.cueflowsapp.ui.theme.uploadedFile

@Composable
fun FileUploadDialog(
    isFileUploaded: Boolean,
    fileName: String?,
    dialogName: String,
    onUploadClick: () -> Unit,
    onNextStepClick: () -> Unit,
    onDismiss: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.8f)
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .padding(horizontal = 35.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Close",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(5.dp)
                        .size(20.dp)
                        .clickable { onDismiss() },
                    tint = Color.Black
                )
                Text(
                    dialogName,
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.inter_semibold)),
                    textAlign = TextAlign.Center,
                    color = DarkGrey1,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(15.dp))

            Text(
                "Please upload your file",
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.inter_regular)),
                textAlign = TextAlign.Center,
                color = DarkGrey1
            )

            Spacer(Modifier.height(15.dp))

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
                Spacer(Modifier.height(15.dp))
                Text("Uploading...", color = DarkGrey1)
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            } else when {
                isFileUploaded -> {
                    UploadedStateContent(
                        fileName = fileName,
                        onUploadClick = onUploadClick,
                        onNextStepClick = onNextStepClick
                    )
                }
                else -> {
                    InitialStateContent(onUploadClick = onUploadClick)
                }
            }
        }
    }
}

@Composable
private fun UploadedStateContent(
    fileName: String?,
    onUploadClick: () -> Unit,
    onNextStepClick: () -> Unit
) {
    Column {
        Button(
            onClick = onUploadClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonColors(
                containerColor = UploadButton,
                contentColor = Color.White,
                disabledContainerColor = UploadButton,
                disabledContentColor = Color.White
            )
        ) {
            Text(
                "Upload another file",
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }

        Spacer(Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = uploadedFile, shape = RoundedCornerShape(12.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val MAX_FILE_NAME_LINES = 3
                val MAX_TOTAL_LINES = 4

                val suffix = " was uploaded"

                if (fileName == null) {
                    Text(
                        text = "File was uploaded",
                        fontFamily = FontFamily(Font(R.font.inter_light)),
                        fontSize = 12.sp,
                        color = DarkGrey1,
                        maxLines = MAX_TOTAL_LINES,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    return
                }

                val fullText = "$fileName$suffix"

                val maxFileNameLength = fileName.length - (fileName.length * MAX_FILE_NAME_LINES / MAX_TOTAL_LINES)

                Text(
                    text = if (fileName.length > maxFileNameLength) {
                        "${fileName.take(maxFileNameLength)}...$suffix"
                    } else {
                        fullText
                    },
                    fontFamily = FontFamily(Font(R.font.inter_light)),
                    fontSize = 12.sp,
                    color = DarkGrey1,
                    maxLines = MAX_TOTAL_LINES,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.tick_green),
                    contentDescription = "Uploaded",
                    tint = Color(0xFF6CB28E),
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(Modifier.height(15.dp))

        Text(
            "The next step",
            fontFamily = FontFamily(Font(R.font.inter_bold)),
            fontSize = 15.sp,
            color = Color(0xFF265980),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNextStepClick() },
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun InitialStateContent(onUploadClick: () -> Unit) {
    Column {
        Button(
            onClick = onUploadClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonColors(
                containerColor = UploadButton,
                contentColor = Color.White,
                disabledContainerColor = UploadButton,
                disabledContentColor = Color.White
            )
        ) {
            Text(
                "Upload from a device",
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }

        Spacer(Modifier.height(30.dp))

        Text(
            "Nothing has been uploaded yet",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            textAlign = TextAlign.Center,
            color = DarkGrey1
        )
    }
}