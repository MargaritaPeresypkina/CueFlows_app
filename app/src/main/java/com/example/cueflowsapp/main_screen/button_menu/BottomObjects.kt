package com.example.cueflowsapp.main_screen.button_menu

sealed class Screen(val route: String) {
    object Library: Screen("library")
    object List: Screen("list")
    object AI: Screen("ai")
    object Account: Screen("account")
}