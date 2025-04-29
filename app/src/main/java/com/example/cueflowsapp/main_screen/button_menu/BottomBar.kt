package com.example.cueflowsapp.main_screen.button_menu

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.cueflowsapp.R
import com.example.cueflowsapp.ui.theme.BottomMenuContentColor
import com.example.cueflowsapp.ui.theme.BottomMenuItemBack
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomBar(
    navController: NavHostController
) {
    val screens = listOf(
        BottomBarScreen.AI,
        BottomBarScreen.Library,
        BottomBarScreen.List,
        BottomBarScreen.Account
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    NavigationBar(
        containerColor = Color.White
    ) {
        screens.forEach{ screen->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
){
    NavigationBarItem(
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.screen.route
        } == true,
        onClick = {
            navController.navigate(screen.screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = {
            Icon(
                painterResource(id = screen.iconId),
                contentDescription = screen.title,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 0.dp).size(20.dp)
            )
        },
        label = {
            Text(
                text = screen.title,
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