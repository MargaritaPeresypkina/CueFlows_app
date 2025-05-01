package com.example.cueflowsapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.cueflowsapp.login.FirstScreen
import com.example.cueflowsapp.login.SignIn
import com.example.cueflowsapp.login.SignUp
import com.example.cueflowsapp.login.data.GetStartedDataObject
import com.example.cueflowsapp.login.data.SignInObject
import com.example.cueflowsapp.login.data.SignUpObject
import com.example.cueflowsapp.login.data.StartScreenObject
import com.example.cueflowsapp.main_screen.AccountScreen
import com.example.cueflowsapp.main_screen.GetStarted
import com.example.cueflowsapp.main_screen.MainScreen
import com.example.cueflowsapp.main_screen.data.AccountScreenObject
import com.example.cueflowsapp.main_screen.data.MainScreenDataObject
import com.example.cueflowsapp.main_screen.parcing.dynamic_screen.DynamicScreen
import com.example.cueflowsapp.main_screen.parcing.dynamic_screen.data.DynamicScreenDataObject
import com.example.cueflowsapp.main_screen.parcing.dynamic_screen.data.DynamicScreenObjectsDataLeft
import com.example.cueflowsapp.main_screen.parcing.dynamic_screen.data.DynamicScreenObjectsDataRight


@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = StartScreenObject
    ) {
        composable<StartScreenObject>{
            FirstScreen(
                onNavigateToSignIn = { navController.navigate(route = SignInObject) },
                onNavigateToSignUp = { navController.navigate(route = SignUpObject) }
            )
        }
        composable<SignInObject> {
            SignIn(
                onNavigateToPreviousScreen = { navController.popBackStack()},
                onNavigateToGetStarted = { navData ->
                    navController.navigate(route = navData)
                },
                onNavigateToSignUp = {navController.navigate(route = SignUpObject)}
            )
        }
        composable<SignUpObject> {
            SignUp(
                onNavigateToPreviousScreen = { navController.popBackStack()},
                onNavigateToGetStarted = { navData ->
                    navController.navigate(route = navData)}
            )
        }
        composable<GetStartedDataObject> { navEntry ->
            val navData = navEntry.toRoute<GetStartedDataObject>()
            GetStarted(
                onNavigateToLibraryScreen = {
                    navController.navigate( route =
                        MainScreenDataObject(
                            navData.uid,
                            navData.email,
                            navData.username
                        )
                    )}
            )
        }
        composable<MainScreenDataObject> { navEntry ->
            val mainScreen: MainScreenDataObject  = navEntry.toRoute()
            MainScreen(navController)
        }
        composable<AccountScreenObject> {
            AccountScreen()
        }

        composable<DynamicScreenDataObject> { navEntry ->
            val navData = navEntry.toRoute<DynamicScreenDataObject>()
            val screenData = remember(navData.screenId) {
                DynamicScreenObjectsDataLeft.find { it.screenName == navData.screenId }
                    ?: DynamicScreenObjectsDataRight.find { it.screenName == navData.screenId }
                    ?: error("Screen not found")
            }
            DynamicScreen(
                content = screenData,
                onNavigateToPreviousScreen = { navController.popBackStack() }
            )
        }
    }
}