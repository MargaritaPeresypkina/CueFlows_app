package com.example.cueflowsapp.main_screen.data

sealed class BottomMenuPage {
    object Library: BottomMenuPage()
    object List: BottomMenuPage()
    object AI: BottomMenuPage()
    object Account: BottomMenuPage()
}