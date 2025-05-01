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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonElevation
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
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f))
            .clickable{
                onDismiss()
            },
        contentAlignment = Alignment.Center,
    ) {

        Text("Загрузка $dialogName", style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.inter_light)),
            modifier = Modifier.padding(top = 40.dp, start = 30.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(
                    Color.White, shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 35.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                dialogName,
                fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                textAlign = TextAlign.Center,
                color = DarkGrey1
            )
            Spacer(Modifier.height(15.dp))
            Text(
                "Please upload your file",
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.inter_regular)),
                textAlign = TextAlign.Center,
                color =  DarkGrey1
            )
            Spacer(Modifier.height(15.dp))

            if(isFileUploaded){
                Column {
                    Button(
                        onClick = {onUploadClick()},
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonColors(
                            containerColor = UploadButton,
                            contentColor = Color.White,
                            disabledContainerColor = UploadButton,
                            disabledContentColor = Color.White
                        )
                    ){
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
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 18.dp,
                                    vertical = 8.dp
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(
                                "$fileName was uploaded",
                                fontFamily = FontFamily(Font(R.font.inter_light)),
                                fontSize = 12.sp,
                                color = DarkGrey1
                            )
                            Image(
                                painter = painterResource(id =R.drawable.tick_green),
                                contentDescription = "tick",
                                contentScale = ContentScale.Crop
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
                        modifier = Modifier.fillMaxWidth().clickable{
                            onNextStepClick()
                        },
                        textAlign = TextAlign.Center
                    )
                }
            } else{
                Button(
                    onClick = {onUploadClick()},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonColors(
                        containerColor = UploadButton,
                        contentColor = Color.White,
                        disabledContainerColor = UploadButton,
                        disabledContentColor = Color.White
                    )
                ){
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
    }
}