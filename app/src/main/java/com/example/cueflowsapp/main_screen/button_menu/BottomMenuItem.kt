package com.example.cueflowsapp.main_screen.button_menu

import com.example.cueflowsapp.R

sealed class BottomMenuItem(
    val route: String,
    val title: String,
    val iconId: Int
) {
    object Library: BottomMenuItem(
        route = "library",
        title = "Library",
        iconId = R.drawable.lib_icon
    )
    object List: BottomMenuItem(
        route = "list",
        title = "My list",
        iconId = R.drawable.list_icon
    )
    object Account: BottomMenuItem(
        route = "account",
        title = "Account",
        iconId = R.drawable.person_icon
    )
    object AI: BottomMenuItem(
        route = "ai",
        title = "Assistant",
        iconId = R.drawable.ai_icon
    )
}