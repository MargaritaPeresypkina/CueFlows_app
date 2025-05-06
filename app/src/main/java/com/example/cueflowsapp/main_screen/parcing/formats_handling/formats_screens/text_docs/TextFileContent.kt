package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.OptionButtonInfo
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.components.OptionButton
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.nio.charset.Charset

@Composable
fun TextFileContent(uri: Uri) {

    val context = LocalContext.current
    val text = try {
        readTxtFile(uri, context)
    } catch (e: Exception) {
        "Error on emulator: ${e.message}"
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        val optionButtons = listOf(
            OptionButtonInfo(image = R.drawable.original_icon, text = "Original", color = 0xFF205984.toInt(), textColor = 0xFFFFFFFF.toInt()),
            OptionButtonInfo(image = R.drawable.summary_icon, text = "Summary", color = 0xFF205984.toInt(), textColor = 0xFFFFFFFF.toInt()),
            OptionButtonInfo(image = R.drawable.keyterms_icon, text = "Key terms", color = 0xFF205984.toInt(), textColor = 0xFFFFFFFF.toInt())
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            items(optionButtons) { item ->
                OptionButton(
                    image = item.image,
                    text = item.text,
                    color = item.color,
                    textColor = item.textColor
                )
            }
        }
        Column{
            Spacer(Modifier.height(30.dp))
            Text(
                "Summary",
                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                color = Color(0xFF343434),
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
            Spacer(Modifier.height(13.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp)
            ) {
                item{
                    Text(
                        text,
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        color = Color(0xFF6B6D78),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}
