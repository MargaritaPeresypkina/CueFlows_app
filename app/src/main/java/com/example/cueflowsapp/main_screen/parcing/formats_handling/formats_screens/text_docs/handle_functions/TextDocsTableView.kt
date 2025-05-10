package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions

import android.R.attr.thickness
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.data.TextDocsContent

@Composable
fun TextDocsTableView(table: TextDocsContent.Table) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        table.rows.forEachIndexed { rowIndex, row ->
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(if (rowIndex == 0) Color(0xFFF5F5F5)
                        else if (rowIndex % 2 == 1) Color.White
                        else Color(0xFFFAFAFA))
                ) {
                    row.forEach { cellContent ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = cellContent,
                                fontSize = 14.sp,
                                color = if (rowIndex == 0) Color(0xFF343434)
                                else Color(0xFF6B6D78),
                                fontFamily = if (rowIndex == 0) FontFamily(Font(R.font.inter_semibold))
                                else FontFamily(Font(R.font.inter_regular))
                            )
                        }
                    }
                }
                if (rowIndex < table.rows.size - 1) {
                    HorizontalDivider(thickness = 1.dp, color = Color(0xFFF5F5F5))
                }
            }
        }
    }
}