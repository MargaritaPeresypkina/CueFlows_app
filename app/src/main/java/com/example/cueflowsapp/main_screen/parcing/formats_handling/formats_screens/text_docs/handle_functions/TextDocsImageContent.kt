package com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.handle_functions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.cueflowsapp.main_screen.parcing.formats_handling.formats_screens.text_docs.data.TextDocsContent

@Composable
fun TextDocsImageContent(image: TextDocsContent.Image) {
    val bitmap = BitmapFactory.decodeByteArray(image.data, 0, image.data.size)?.let {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val scaleFactor = screenWidth.value / image.width
        val scaledHeight = (image.height * scaleFactor).toInt()

        Bitmap.createScaledBitmap(it, screenWidth.value.toInt(), scaledHeight, true)
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Document image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        } ?: Text("Could not load image")
    }
}