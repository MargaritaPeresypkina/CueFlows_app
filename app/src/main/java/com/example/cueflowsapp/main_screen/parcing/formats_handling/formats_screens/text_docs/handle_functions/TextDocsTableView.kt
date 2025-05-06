package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
            .border(width = 1.dp,
                color = Color(0xFF9FA1B0),
                shape = RectangleShape
            ),
                verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
                table.rows.forEachIndexed { index, row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (index % 2 == 0) Color.White else Color(0xFFF5F5F5)
                            )
                    ) {
                        row.forEach { cell ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 12.dp, horizontal = 8.dp)
                            ) {
                                Text(
                                    text = cell,
                                    fontSize = 14.sp,
                                    color = Color(0xFF343434),
                                    fontFamily = FontFamily(Font(R.font.inter_regular))
                                )
                            }
                        }
                    }
                }
            }
}