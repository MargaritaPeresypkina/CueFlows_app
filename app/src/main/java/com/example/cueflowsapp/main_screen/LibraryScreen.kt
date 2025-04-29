package com.example.cueflowsapp.main_screen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.data.library_buttons.LibraryButton
import com.example.cueflowsapp.main_screen.data.library_buttons.LibraryButtonsListLeft
import com.example.cueflowsapp.main_screen.data.library_buttons.LibraryButtonsListRight
import com.example.cueflowsapp.main_screen.parcing.text_parsing.data.TextDocsDataObject
import com.example.cueflowsapp.ui.theme.Grey1
import com.example.cueflowsapp.ui.theme.LightBlack2
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Preview(showBackground = true)
@Composable
fun LibraryScreen(rootNavController: NavHostController) {
    val auth = Firebase.auth
    val emailState = remember { mutableStateOf("") }

    val horizontalSpacing = 25.dp
    val spacing = 20.dp
    val kLittle = 0.98255914.dp
    val kLarge = 0.744493392.dp

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val buttonWidth = (screenWidthDp - (horizontalSpacing * 2 + spacing)) / 2
    val heightWithDpForLittleButton = ( buttonWidth / kLittle).dp
    val heightWithDpForLargeButton = ( buttonWidth / kLarge).dp

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(scrollState)
    ){
        Box(modifier = Modifier.fillMaxWidth()){
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 55.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth().padding(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Cue",
                        fontFamily = FontFamily(Font(R.font.inter_bold)),
                        style = TextStyle(
                            color = Color.Black.copy(alpha = 0.7f),
                            letterSpacing = 2.4.sp,
                            fontSize = 16.sp
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription ="logo",
                        modifier = Modifier.height(24.dp).padding(horizontal = 10.dp)
                    )
                    Text(
                        text = "Flows",
                        fontFamily = FontFamily(Font(R.font.inter_bold)),
                        style = TextStyle(
                            color = Color.Black.copy(alpha = 0.7f),
                            letterSpacing = 2.4.sp,
                            fontSize = 16.sp
                        )
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.cloud_back),
                    contentDescription = "cloud",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, start = horizontalSpacing, end = horizontalSpacing)
            ) {
                Text(
                    "Hello, ${auth.currentUser?.displayName ?: "user"}",
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    fontSize = 30.sp,
                    color = LightBlack2,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    "What will you choose today?",
                    fontFamily = FontFamily(Font(R.font.inter_light)),
                    fontSize = 20.sp,
                    color = Grey1,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ){

                    Column{
                        LibraryButtonsListLeft.forEach { data ->
                            val height = if(data.isLarge) heightWithDpForLargeButton else heightWithDpForLittleButton
                            LibraryButton(
                                height  = height,
                                text = data.text,
                                image = data.image,
                                background = data.background,
                                textColor = data.textColor,
                                onClick = {
                                    rootNavController.navigate(TextDocsDataObject(data.text))
                                }
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(spacing))
                    Column{
                        LibraryButtonsListRight.forEach { data ->
                        val height = if(data.isLarge) heightWithDpForLargeButton else heightWithDpForLittleButton
                            LibraryButton(
                                height = height,
                                text = data.text,
                                image = data.image,
                                background = data.background,
                                textColor = data.textColor,
                                onClick = {
                                    rootNavController.navigate(TextDocsDataObject(data.text))
                                }
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                        }

                    }
                }
            }
        }
    }
}

