package com.example.cueflowsapp.main_screen.button_menu

import com.example.cueflowsapp.R

sealed class BottomBarScreen(
    val screen: Screen,
    val title: String,
    val iconId: Int
) {
    object Library: BottomBarScreen(
        screen = Screen.Library,
        title = "Library",
        iconId = R.drawable.lib_icon
    )
    object List: BottomBarScreen(
        screen = Screen.List,
        title = "My list",
        iconId = R.drawable.list_icon
    )
    object Account: BottomBarScreen(
        screen = Screen.Account,
        title = "Account",
        iconId = R.drawable.person_icon
    )
    object AI: BottomBarScreen(
        screen = Screen.AI,
        title = "Assistant",
        iconId = R.drawable.ai_icon
    )
}