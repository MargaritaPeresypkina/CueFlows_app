package com.example.cueflowsapp.main_screen.button_menu

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cueflowsapp.main_screen.AIScreen
import com.example.cueflowsapp.main_screen.AccountScreen
import com.example.cueflowsapp.main_screen.LibraryScreen
import com.example.cueflowsapp.main_screen.ListScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController
    ) {
    NavHost(
        navController = navController,
        startDestination = Screen.Library.route
    ) {
        composable(Screen.Library.route) {
            LibraryScreen(
                rootNavController
            )
        }
        composable(Screen.AI.route) {
            AIScreen()
        }
        composable(Screen.List.route) {
            ListScreen(rootNavController)
        }
        composable(Screen.Account.route) {
            AccountScreen(rootNavController)
        }
    }
}
