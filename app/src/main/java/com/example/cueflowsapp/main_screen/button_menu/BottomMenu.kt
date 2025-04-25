package com.example.cueflowsapp.main_screen.button_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.example.cueflowsapp.ui.theme.BottomMenuContentColor
import com.example.cueflowsapp.ui.theme.BottomMenuItemBack

@Composable
fun BottomMenu(
    onLibrary: () -> Unit = {},
    onAI: () -> Unit = {},
    onList: () -> Unit = {},
    onAccount: () -> Unit = {}
){
    val items = listOf(
        BottomMenuItem.AI,
        BottomMenuItem.Library,
        BottomMenuItem.List,
        BottomMenuItem.Account
    )

    val selectedItem = remember { mutableStateOf("Library") }
    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEach{ item ->
            NavigationBarItem(
                selected = selectedItem.value == item.title, onClick = {
                    selectedItem.value = item.title
                    when (item.title) {
                        BottomMenuItem.AI.title -> onAI()
                        BottomMenuItem.Library.title -> onLibrary()
                        BottomMenuItem.List.title -> onList()
                        BottomMenuItem.Account.title -> onAccount()
                    }
                },
                icon = {
                    Icon(
                        painterResource(id = item.iconId),
                        contentDescription = item.title,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 0.dp).size(20.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontFamily = FontFamily(Font(R.font.inter_semibold)),
                        textAlign = TextAlign.Center
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.White,
                    selectedTextColor = BottomMenuItemBack,
                    selectedIndicatorColor = BottomMenuItemBack,
                    unselectedIconColor = BottomMenuContentColor,
                    unselectedTextColor = BottomMenuContentColor,
                    disabledIconColor = BottomMenuContentColor,
                    disabledTextColor = BottomMenuContentColor
                )
            )
        }
    }

}