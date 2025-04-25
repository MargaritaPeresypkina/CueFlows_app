package com.example.cueflowsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.cueflowsapp.login.FirstScreen
import com.example.cueflowsapp.login.SignIn
import com.example.cueflowsapp.login.SignUp
import com.example.cueflowsapp.login.data.GetStartedDataObject
import com.example.cueflowsapp.login.data.SignInObject
import com.example.cueflowsapp.login.data.SignUpObject
import com.example.cueflowsapp.login.data.StartScreenObject
import com.example.cueflowsapp.main_screen.GetStarted
import com.example.cueflowsapp.main_screen.MainScreen
import com.example.cueflowsapp.main_screen.data.MainScreenDataObject
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fs = Firebase.firestore
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = StartScreenObject
            ) {
                composable<StartScreenObject>{
                    FirstScreen(
                        onNavigateToSignIn = { navController.navigate(SignInObject) },
                        onNavigateToSignUp = { navController.navigate(SignUpObject) }
                    )
                }
                composable<SignInObject> {
                    SignIn(
                        onNavigateToPreviousScreen = { navController.popBackStack()},
                        onNavigateToGetStarted = { navData ->
                            navController.navigate(navData)
                        },
                        onNavigateToSignUp = {navController.navigate(SignUpObject)}
                    )
                }
                composable<SignUpObject> {
                    SignUp(
                        onNavigateToPreviousScreen = { navController.popBackStack()},
                        onNavigateToGetStarted = { navData ->
                            navController.navigate(navData)}
                    )
                }
                composable<GetStartedDataObject> { navEntry ->
                    val navData = navEntry.toRoute<GetStartedDataObject>()
                    GetStarted(
                        onNavigateToLibraryScreen = {
                            navController.navigate(
                                MainScreenDataObject(
                                    navData.uid,
                                    navData.email,
                                    navData.username
                                )
                            )}
                    )
                }
                composable<MainScreenDataObject> { navEntry ->
                    val navData = navEntry.toRoute<MainScreenDataObject>()
                    MainScreen()
                }
            }

            val auth = Firebase.auth
            val currentUser = auth.currentUser

            if(currentUser != null) {
                navController.navigate(MainScreenDataObject(
                    uid = currentUser.uid,
                    email = currentUser.email ?: "",
                    username = currentUser.displayName ?: ""
                ))
            } else {
                navController.navigate(StartScreenObject)
            }
        }
    }
}