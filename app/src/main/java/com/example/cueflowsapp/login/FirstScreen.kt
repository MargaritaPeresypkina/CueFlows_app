package com.example.cueflowsapp.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.example.cueflowsapp.login.data.SignInObject
import com.example.cueflowsapp.login.data.SignUpObject
import com.example.cueflowsapp.ui.theme.Beidge11
import com.example.cueflowsapp.ui.theme.BeidgeButtonContent
import com.example.cueflowsapp.ui.theme.Blue1
import com.example.cueflowsapp.ui.theme.DarkGrey1
import com.example.cueflowsapp.ui.theme.Grey1
import com.example.cueflowsapp.ui.theme.LightGrey1
import com.example.cueflowsapp.ui.theme.PurpleButton

@Composable
fun FirstScreen(
    onNavigateToSignIn:(SignInObject) -> Unit,
    onNavigateToSignUp: (SignUpObject) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.background_image_first),
                contentDescription = "background",
                modifier = Modifier.fillMaxHeight(0.55f).fillMaxWidth(),
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.FillBounds
            )
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 55.dp, horizontal = 20.dp),
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
                        modifier = Modifier.fillMaxHeight(0.03f).padding(horizontal = 10.dp)
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.main_img_first_screen),
                        contentDescription = "First Image",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }
        }
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Learn Smarter\n" +
                            "Not Harder",
                    color = DarkGrey1,
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.offset(y = (-10).dp),
                    lineHeight = 40.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Your learning, simplified. Parse, organize, and \n" +
                        "understand information effortlessly.",
                    color = LightGrey1,
                    fontFamily = FontFamily(Font(R.font.inter_light)),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 11.dp)
                )
            }
            Button(
                onClick = {
                    onNavigateToSignUp(SignUpObject)
                          },
                modifier = Modifier.fillMaxWidth().padding(top = 47.dp, start = 20.dp, end = 20.dp).fillMaxHeight(0.3f),
                colors = ButtonColors(
                    containerColor = PurpleButton,
                    contentColor = BeidgeButtonContent,
                    disabledContainerColor = PurpleButton,
                    disabledContentColor = Beidge11
                )
            ){
                Text(
                    text = "SIGN UP",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.inter_semibold))
                )
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 27.dp),
                horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "ALREADY HAVE AN ACCOUNT?",
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    color = Grey1,
                    modifier = Modifier.padding(end = 5.dp),
                    fontSize = 14.sp
                )

                Text(
                    text = "LOG IN",
                    fontFamily = FontFamily(Font(R.font.inter_semibold)),
                    color = Blue1,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        onNavigateToSignIn(SignInObject)
                    }
                )
            }
        }
    }
}


