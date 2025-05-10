package com.example.cueflowsapp.main_screen.parcing.formats_handling

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.DocumentFormat
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.TextFileViewModel
//import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.TextFileViewModel
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.TextFileContent


@Composable
fun SelectFileOptionScreen(
    fileUri: String,
    fileName: String,
    backgroundColor: Int,
    formatType: DocumentFormat,
    onNavigateBack: () -> Unit,
    viewModel: TextFileViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.14f)
                .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                .background(Color(backgroundColor))
        ){
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 50.dp, start = 35.dp, end = 35.dp, bottom = 25.dp ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(
                    onClick = onNavigateBack
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_white),
                        contentDescription = "arrow_white",
                        modifier = Modifier.size(18.dp)
                        )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = fileName,
                    fontFamily = FontFamily(Font(R.font.inter_semibold)),
                    fontSize = 20.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,

                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth().padding(top = 15.dp)){
            when (formatType) {
                DocumentFormat.TXT -> TextFileContent(fileUri.toUri(), "txt", viewModel)
                DocumentFormat.DOCX -> TextFileContent(fileUri.toUri(), "docx", viewModel)
                DocumentFormat.PDF -> TextFileContent(fileUri.toUri(), "pdf", viewModel)
            }
        }
    }
}
