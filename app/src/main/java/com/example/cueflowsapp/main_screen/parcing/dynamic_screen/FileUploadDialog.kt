package com.example.cueflowsapp.main_screen.parcing.dynamic_screen

import android.R.attr.onClick
import android.net.http.UploadDataSink
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cueflowsapp.R
import com.example.cueflowsapp.ui.theme.DarkGrey1
import com.example.cueflowsapp.ui.theme.UploadButton
import com.example.cueflowsapp.ui.theme.uploadedFile
import java.nio.file.WatchEvent

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
    )
            {
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
                    ){

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

                    // Показываем прогресс-бар при загрузке
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(50.dp))
                        Spacer(Modifier.height(15.dp))
                        Text("Uploading...", color = DarkGrey1)
                    }
                    // Показываем сообщение об ошибке, если есть
                    else if (errorMessage != null) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                    // Основное содержимое в зависимости от состояния
                    else when {
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
                Text(
                    "$fileName was uploaded",
                    fontFamily = FontFamily(Font(R.font.inter_light)),
                    fontSize = 12.sp,
                    color = DarkGrey1
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