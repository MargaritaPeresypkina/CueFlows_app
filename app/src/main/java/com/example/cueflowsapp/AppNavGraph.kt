package com.example.cueflowsapp

import SignUp
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.cueflowsapp.login.FirstScreen
import com.example.cueflowsapp.login.SignIn
import com.example.cueflowsapp.login.data.GetStartedDataObject
import com.example.cueflowsapp.login.data.SignInObject
import com.example.cueflowsapp.login.data.SignUpObject
import com.example.cueflowsapp.login.data.StartScreenObject
import com.example.cueflowsapp.main_screen.AccountScreen
import com.example.cueflowsapp.main_screen.GetStarted
import com.example.cueflowsapp.main_screen.MainScreen
import com.example.cueflowsapp.main_screen.data.AccountScreenObject
import com.example.cueflowsapp.main_screen.data.MainScreenDataObject
import com.example.cueflowsapp.main_screen.data.DocumentListViewModel
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.NavRoutes
import com.example.cueflowsapp.main_screen.parcing.formats_handling.SelectFileOptionScreen
import com.example.cueflowsapp.main_screen.parcing.dynamic_screen.DynamicScreen
import com.example.cueflowsapp.main_screen.parcing.dynamic_screen.data.DynamicScreenDataObject
import com.example.cueflowsapp.main_screen.parcing.dynamic_screen.data.DynamicScreenObjectsDataLeft
import com.example.cueflowsapp.main_screen.parcing.dynamic_screen.data.DynamicScreenObjectsDataRight
import com.example.cueflowsapp.splash_screen.SplashScreen
import com.example.cueflowsapp.splash_screen.SplashScreenObject
import com.facebook.CallbackManager

@Composable
fun AppNavGraph(
    navController: NavHostController,
    callbackManager: CallbackManager,
    facebookLoginLauncher: ActivityResultLauncher<Intent>,
    googleLoginLauncher: ActivityResultLauncher<Intent>,
    googleLoginResult: MutableState<Intent?>
) {
    val viewModel: DocumentListViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = SplashScreenObject
    ) {
        composable<SplashScreenObject> {
            SplashScreen()
        }

        composable<StartScreenObject> {
            FirstScreen(
                onNavigateToSignIn = { navController.navigate(route = SignInObject) },
                onNavigateToSignUp = { navController.navigate(route = SignUpObject) }
            )
        }
        composable<SignInObject> {
            SignIn(
                onNavigateToPreviousScreen = { navController.popBackStack() },
                onNavigateToGetStarted = { navData ->
                    Log.d("AppNavGraph", "Navigating to GetStarted: $navData")
                    navController.navigate(route = navData)
                },
                onNavigateToSignUp = { navController.navigate(route = SignUpObject) },
                callbackManager = callbackManager,
                facebookLoginLauncher = facebookLoginLauncher,
                googleLoginLauncher = googleLoginLauncher,
                googleLoginResult = googleLoginResult
            )
        }
        composable<SignUpObject> {
            SignUp(
                onNavigateToPreviousScreen = { navController.popBackStack() },
                onNavigateToGetStarted = { navData ->
                    Log.d("AppNavGraph", "Navigating to GetStarted: $navData")
                    navController.navigate(route = navData)
                },
                onNavigateToSignIn = { navController.navigate(route = SignInObject) },
                callbackManager = callbackManager,
                facebookLoginLauncher = facebookLoginLauncher,
                googleLoginLauncher = googleLoginLauncher,
                googleLoginResult = googleLoginResult
            )
        }
        composable<GetStartedDataObject> { navEntry ->
            val navData = navEntry.toRoute<GetStartedDataObject>()
            Log.d("AppNavGraph", "GetStartedDataObject: $navData")
            GetStarted(
                onNavigateToLibraryScreen = {
                    Log.d("AppNavGraph", "Navigating to MainScreen: ${MainScreenDataObject(navData.uid, navData.email, navData.username)}")
                    navController.navigate(
                        route = MainScreenDataObject(
                            navData.uid,
                            navData.email,
                            navData.username
                        )
                    )
                }
            )
        }
        composable<MainScreenDataObject> { navEntry ->
            val mainScreen: MainScreenDataObject = navEntry.toRoute()
            Log.d("AppNavGraph", "MainScreenDataObject: $mainScreen")
            MainScreen(navController)
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
                onNavigateToDocumentViewer = { documentViewer ->
                    navController.navigate(documentViewer)
                },
                onNavigateToPreviousScreen = { navController.popBackStack() }
            )
        }

        composable<NavRoutes.DocumentViewer> { navEntry ->
            val navData = navEntry.toRoute<NavRoutes.DocumentViewer>()
            SelectFileOptionScreen(
                documentId = navData.documentId,
                fileUri = navData.fileUri,
                fileName = navData.fileName,
                backgroundColor = navData.backgroundColor,
                formatType = navData.formatType,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}