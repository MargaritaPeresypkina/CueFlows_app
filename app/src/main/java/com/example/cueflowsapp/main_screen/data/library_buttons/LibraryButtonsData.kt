package com.example.cueflowsapp.main_screen.data.library_buttons

import androidx.compose.ui.graphics.Color
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.button_menu.Screen
import com.example.cueflowsapp.main_screen.parcing.ParsingScreen
import com.example.cueflowsapp.ui.theme.ArchiveButton
import com.example.cueflowsapp.ui.theme.AudioButton
import com.example.cueflowsapp.ui.theme.EBookButton
import com.example.cueflowsapp.ui.theme.ImageButton
import com.example.cueflowsapp.ui.theme.PPTXButton
import com.example.cueflowsapp.ui.theme.StructureButton
import com.example.cueflowsapp.ui.theme.TableButton
import com.example.cueflowsapp.ui.theme.TextDocsButton
import com.example.cueflowsapp.ui.theme.TextEBookButton
import com.example.cueflowsapp.ui.theme.TextImageButton
import com.example.cueflowsapp.ui.theme.TextPPTXButton
import com.example.cueflowsapp.ui.theme.TextStructureButton
import com.example.cueflowsapp.ui.theme.VideoButton
import com.example.cueflowsapp.ui.theme.WebPageButton

val LibraryButtonsListLeft = listOf(
    LibraryButtonData( text = "Video", image = R.drawable.video_button, background = VideoButton, textColor = Color.White, isLarge = false),
    LibraryButtonData( text = "PPTX", image = R.drawable.pptx_button, background = PPTXButton, textColor = TextPPTXButton, isLarge = true),
    LibraryButtonData( text = "Audio", image = R.drawable.audio_button, background = AudioButton, textColor = Color.White, isLarge = false),
    LibraryButtonData( text = "E-books", image = R.drawable.e_book_button, background = EBookButton, textColor = TextEBookButton, isLarge = true),
    LibraryButtonData( text = "Structure", image = R.drawable.structure_button, background = StructureButton, textColor = TextStructureButton, isLarge = false)
)
val LibraryButtonsListRight = listOf(
    LibraryButtonData( text = "Text Docs", image = R.drawable.text_docs_button, background = TextDocsButton, textColor = Color.White, isLarge = true),
    LibraryButtonData( text = "Image", image = R.drawable.image_button, background = ImageButton, textColor = TextImageButton, isLarge = false),
    LibraryButtonData( text = "Table", image = R.drawable.table_button, background = TableButton, textColor = Color.White, isLarge = true),
    LibraryButtonData( text = "Archive", image = R.drawable.archive_button, background = ArchiveButton, textColor = TextStructureButton, isLarge = false),
    LibraryButtonData( text = "Web page", image = R.drawable.web_page_button, background = WebPageButton, textColor = TextStructureButton, isLarge = true)
)