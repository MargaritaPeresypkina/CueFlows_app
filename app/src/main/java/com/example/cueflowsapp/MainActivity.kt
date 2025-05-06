package com.example.cueflowsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.cueflowsapp.login.data.StartScreenObject
import com.example.cueflowsapp.main_screen.data.MainScreenDataObject
import com.example.cueflowsapp.splash_screen.SplashScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val auth = Firebase.auth
            var isLoading by remember { mutableStateOf(true) }

            // Показываем SplashScreen пока проверяем авторизацию
            if (isLoading) {
                SplashScreen()
            }

            LaunchedEffect(key1 = Unit) {
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    navController.navigate(
                        MainScreenDataObject(
                            uid = currentUser.uid,
                            email = currentUser.email ?: "",
                            username = currentUser.displayName ?: ""
                        )
                    ) {
                        popUpTo(0) { inclusive = true }
                    }
                } else {
                    navController.navigate(StartScreenObject) {
                        popUpTo(0) { inclusive = true }
                    }
                }
                isLoading = false
            }

            AppNavGraph(navController = navController)
        }
    }
}