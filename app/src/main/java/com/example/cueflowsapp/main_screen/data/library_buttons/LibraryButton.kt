package com.example.cueflowsapp.main_screen.data.library_buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import com.example.cueflowsapp.R
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LibraryButton(
    height: Dp,
    text: String,
    @DrawableRes image: Int,
    background: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val horizontalSpacing = 25.dp
    val spacing = 20.dp

    Column(
        modifier = Modifier
            .height(height)
            .width((screenWidthDp - (horizontalSpacing * 2 + spacing)) / 2)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = background)
            .clickable{
                onClick()
            },
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Image(
            painter = painterResource(id = image),
            contentDescription = "button_$text",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text,
            fontFamily = FontFamily(Font(R.font.inter_bold)),
            color = textColor,
            modifier = Modifier.padding(bottom = 12.dp, start = 13.dp),
            fontSize = 20.sp
        )
    }
}


//import androidx.annotation.DrawableRes
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import com.example.cueflowsapp.R
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun LibraryButton(
//    height: Dp,
//    text: String,
//    @DrawableRes image: Int,
//    background: Color,
//    textColor: Color,
//    onClick: () -> Unit
//) {
//    val configuration = LocalConfiguration.current
//    val screenWidthDp = configuration.screenWidthDp.dp
//    val horizontalSpacing = 25.dp
//    val spacing = 20.dp
//
//    Column(
//        modifier = Modifier
//            .height(height)
//            .width((screenWidthDp - (horizontalSpacing * 2 + spacing)) / 2)
//            .clip(shape = RoundedCornerShape(10.dp))
//            .background(color = background)
//            .clickable{
//                onClick()
//            },
//        verticalArrangement = Arrangement.SpaceBetween
//    ){
//        Image(
//            painter = painterResource(id = image),
//            contentDescription = "button_$text",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 10.dp),
//            contentScale = ContentScale.Crop
//        )
//        Text(
//            text,
//            fontFamily = FontFamily(Font(R.font.inter_bold)),
//            color = textColor,
//            modifier = Modifier.padding(bottom = 12.dp, start = 13.dp),
//            fontSize = 20.sp
//        )
//    }
//}