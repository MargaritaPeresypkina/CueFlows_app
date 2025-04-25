package com.example.cueflowsapp.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.example.cueflowsapp.ui.theme.Background4
import com.example.cueflowsapp.ui.theme.BeidgeFont4
import com.example.cueflowsapp.ui.theme.Button4
import com.example.cueflowsapp.ui.theme.ButtonContent4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun GetStarted(
    onNavigateToLibraryScreen: () -> Unit
) {
    val auth = Firebase.auth
    Box(modifier = Modifier.fillMaxSize().background(color = Background4)){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box {
                Image(
                    painter = painterResource(id = R.drawable.ellipse),
                    contentDescription = "ellipse",
                    modifier = Modifier.fillMaxHeight(0.5f).offset(y = 120.dp),
                    contentScale = ContentScale.FillHeight
                )
                Image(
                    painter = painterResource(id = R.drawable.get_started),
                    contentDescription = "get_started",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.offset(y = (-130).dp)
                )

            }
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 55.dp, start = 20.dp, end = 20.dp, bottom = 60.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Cue",
                        fontFamily = FontFamily(Font(R.font.inter_bold)),
                        style = TextStyle(
                            color = BeidgeFont4.copy(alpha = 0.8f),
                            letterSpacing = 2.4.sp,
                            fontSize = 16.sp
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription ="logo",
                        modifier = Modifier.fillMaxHeight(0.03f).padding(horizontal = 10.dp)
                    )
                    Text(
                        text = "Flows",
                        fontFamily = FontFamily(Font(R.font.inter_bold)),
                        style = TextStyle(
                            color = BeidgeFont4.copy(alpha = 0.8f),
                            letterSpacing = 2.4.sp,
                            fontSize = 16.sp
                        )
                    )
                }
                Text(
                    "Hi ${auth.currentUser?.displayName ?: "friend"}, Welcome",
                    color = BeidgeFont4,
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    letterSpacing = 0.1.sp,
                    lineHeight = 48.sp
                )
                Text(
                    "to Cue Flows",
                    color = BeidgeFont4,
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.inter_light)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    letterSpacing = 0.1.sp,
                )
                Text(
                    "Download sources of interest to you\n" +
                            "Work and learn at your own convenience",
                    color = BeidgeFont4,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    letterSpacing = 0.1.sp,
                )
            }
            Button(
                onClick = { onNavigateToLibraryScreen() },
                modifier = Modifier.fillMaxWidth().height(63.dp),
                colors = ButtonColors(
                    containerColor = Button4,
                    contentColor = ButtonContent4,
                    disabledContainerColor = Button4,
                    disabledContentColor = ButtonContent4
                )
            ) {
                Text(
                    "GET STARTED",
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    style = TextStyle(
                        letterSpacing = 0.5.sp,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}


