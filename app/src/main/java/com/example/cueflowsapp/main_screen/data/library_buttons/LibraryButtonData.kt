package com.example.cueflowsapp.main_screen.data.library_buttons

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class LibraryButtonData(
    val text: String,
    @DrawableRes val image: Int,
    val background: Color,
    val textColor: Color,
    val isLarge: Boolean
)
