package com.example.cueflowsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.cueflowsapp.login.data.StartScreenObject
import com.example.cueflowsapp.main_screen.data.MainScreenDataObject
import com.example.cueflowsapp.splash_screen.SplashScreen
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var callbackManager: CallbackManager
    private lateinit var googleSignInClient: GoogleSignInClient
    private val googleLoginResult = mutableStateOf<Intent?>(null)

    private val facebookLoginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        callbackManager.onActivityResult(result.resultCode, result.resultCode, result.data)
        Log.d("MainActivity", "Facebook Login Result: resultCode=${result.resultCode}")
    }

    private val googleLoginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("MainActivity", "Google Login Result: resultCode=${result.resultCode}")
        googleLoginResult.value = result.data
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        // Initialize Facebook SDK
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id))
        FacebookSdk.setClientToken(getString(R.string.facebook_client_token))
        FacebookSdk.setAdvertiserIDCollectionEnabled(true)
        FacebookSdk.setAutoLogAppEventsEnabled(true)
        AppEventsLogger.activateApp(application)
        callbackManager = CallbackManager.Factory.create()

        // Initialize Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val auth = Firebase.auth
            var isLoading by remember { mutableStateOf(true) }

            if (isLoading) {
                SplashScreen()
            }

            LaunchedEffect(key1 = Unit) {
                val currentUser = auth.currentUser
                Log.d("MainActivity", "Current user: ${currentUser?.uid}, Email: ${currentUser?.email}, DisplayName: ${currentUser?.displayName}, IsEmailVerified: ${currentUser?.isEmailVerified}")
                if (currentUser != null) {
                    navController.navigate(
                        MainScreenDataObject(
                            uid = currentUser.uid,
                            email = currentUser.email ?: "",
                            username = currentUser.displayName ?: ""
                        )
                    ) { popUpTo(0) { inclusive = true } }
                } else {
                    navController.navigate(StartScreenObject) { popUpTo(0) { inclusive = true } }
                }
                isLoading = false
            }

            AppNavGraph(
                navController = navController,
                callbackManager = callbackManager,
                facebookLoginLauncher = facebookLoginLauncher,
                googleLoginLauncher = googleLoginLauncher,
                googleLoginResult = googleLoginResult
            )
        }
    }

//    fun getCallbackManager(): CallbackManager {
//        return callbackManager
//    }
//
//    fun getFacebookLoginLauncher() = facebookLoginLauncher
//
//    fun getGoogleLoginLauncher() = googleLoginLauncher

    fun getGoogleSignInClient(): GoogleSignInClient {
        return googleSignInClient
    }

//    fun signOutFromFacebook() {
//        LoginManager.getInstance().logOut()
//    }
//
//    fun signOutFromGoogle() {
//        googleSignInClient.signOut()
//    }
}