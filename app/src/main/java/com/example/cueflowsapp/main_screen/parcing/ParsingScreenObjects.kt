package com.example.cueflowsapp.main_screen.parcing

import kotlinx.serialization.Serializable
@Serializable
sealed class ParsingScreen(val route: String) {
    @Serializable object Text: ParsingScreen ("text")
    @Serializable object Video: ParsingScreen ("video")
    @Serializable object PPTX: ParsingScreen ("pptx")
    @Serializable object Image: ParsingScreen ("image")
    @Serializable object Audio: ParsingScreen ("audio")
    @Serializable object EBook: ParsingScreen ("ebook")
    @Serializable object Archive: ParsingScreen ("archive")
    @Serializable object Table: ParsingScreen ("table")
    @Serializable object Structure: ParsingScreen ("structure")
    @Serializable object WebPage: ParsingScreen ("webpage")

    @Serializable
    companion object {
        val parsingScreens = listOf(
            Text, Video, PPTX, Image, Audio, EBook, Archive, Table, Structure, WebPage
        )
    }
}