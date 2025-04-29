package com.example.cueflowsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.example.cueflowsapp.login.data.StartScreenObject
import com.example.cueflowsapp.main_screen.MainScreen
import com.example.cueflowsapp.main_screen.data.MainScreenDataObject
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val fs = Firebase.firestore
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val auth = Firebase.auth
            val currentUser = auth.currentUser

            LaunchedEffect(key1 = currentUser) {
                if (currentUser != null) {
                    navController.navigate(MainScreenDataObject(
                        uid = currentUser.uid,
                        email = currentUser.email ?: "",
                        username = currentUser.displayName ?: ""
                    ))
                } else {
                    navController.navigate(StartScreenObject) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
            AppNavGraph(navController = navController)
        }
    }
}