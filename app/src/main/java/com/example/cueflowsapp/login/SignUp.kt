import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cueflowsapp.R
import com.example.cueflowsapp.login.data.GetStartedDataObject
import com.example.cueflowsapp.login.data.SignInObject
import com.example.cueflowsapp.login.data.SignUpObject
import com.example.cueflowsapp.main_screen.data.DocumentRepository
import com.example.cueflowsapp.main_screen.data.UserModel
import com.example.cueflowsapp.ui.theme.Blue1
import com.example.cueflowsapp.ui.theme.Facebook2
import com.example.cueflowsapp.ui.theme.Facebook21
import com.example.cueflowsapp.ui.theme.FacebookDescription2
import com.example.cueflowsapp.ui.theme.FailureContent
import com.example.cueflowsapp.ui.theme.GoogleDescription2
import com.example.cueflowsapp.ui.theme.Grey1
import com.example.cueflowsapp.ui.theme.Grey2
import com.example.cueflowsapp.ui.theme.Grey21
import com.example.cueflowsapp.ui.theme.LightBlack2
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.cueflowsapp.MainActivity
import com.example.cueflowsapp.login.LoginButton
import com.example.cueflowsapp.login.RoundedCornerTextField
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SignUp(
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToGetStarted: (GetStartedDataObject) -> Unit,
    onNavigateToSignIn: (SignInObject) -> Unit,
    callbackManager: CallbackManager,
    facebookLoginLauncher: ActivityResultLauncher<Intent>,
    googleLoginLauncher: ActivityResultLauncher<Intent>,
    googleLoginResult: MutableState<Intent?>
) {
    val auth = remember { Firebase.auth }
    val errorState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val usernameState = remember { mutableStateOf("") }
    val activity = LocalActivity.current as MainActivity
    val repository = DocumentRepository()
    val scope = rememberCoroutineScope()

    LaunchedEffect(googleLoginResult.value) {
        googleLoginResult.value?.let { result ->
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result)
                val account = task.getResult(ApiException::class.java)
                Log.d("GoogleLogin", "Google Sign-In Success: Email=${account.email}, DisplayName=${account.displayName}, IdToken=${account.idToken}")
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null) {
                                Log.d("GoogleLogin", "Firebase Auth Success: UID=${user.uid}, Email=${user.email}, DisplayName=${user.displayName}")
                                val userData = UserModel(
                                    uid = user.uid,
                                    email = user.email ?: "",
                                    username = user.displayName ?: ""
                                )
                                scope.launch {
                                    try {
                                        repository.saveUser(userData)
                                        Log.d("GoogleLogin", "Navigating to GetStarted: UID=${user.uid}")
                                        onNavigateToGetStarted(
                                            GetStartedDataObject(
                                                uid = user.uid,
                                                email = user.email ?: "",
                                                username = user.displayName ?: ""
                                            )
                                        )
                                    } catch (e: Exception) {
                                        errorState.value = "Failed to save user data: ${e.message}"
                                        Log.e("GoogleLogin", "Error saving user data: ${e.message}")
                                    }
                                }
                            } else {
                                errorState.value = "No user data after successful login"
                                Log.e("GoogleLogin", "No user data after successful login")
                            }
                        } else {
                            errorState.value = authTask.exception?.message ?: "Google authentication failed"
                            Log.e("GoogleLogin", "Firebase Auth Failed: ${authTask.exception?.message}")
                        }
                    }
                    .addOnFailureListener { exception ->
                        errorState.value = exception.message ?: "Google sign-up failed"
                        Log.e("GoogleLogin", "Google sign-up failed: ${exception.message}")
                    }
                googleLoginResult.value = null
            } catch (e: ApiException) {
                errorState.value = "Google Sign-In failed: ${e.statusCode}: ${e.message}"
                Log.e("GoogleLogin", "Google Sign-In failed: StatusCode=${e.statusCode}, Message=${e.message}, Cause=${e.cause}")
                googleLoginResult.value = null
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_sign),
            contentDescription = "back",
            modifier = Modifier.fillMaxHeight(0.4f).fillMaxWidth(),
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 40.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Transparent, shape = CircleShape)
                    .size(width = 55.dp, height = 55.dp)
                    .border(
                        width = 1.dp,
                        shape = CircleShape,
                        brush = Brush.linearGradient(listOf(Grey2, Grey2))
                    )
                    .clickable {
                        onNavigateToPreviousScreen()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "arrow",
                    modifier = Modifier.size(20.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 15.dp, bottom = 30.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Create your account!",
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    fontSize = 28.sp,
                    color = LightBlack2
                )
            }
            Button(
                onClick = {
                    val loginManager = LoginManager.getInstance()
                    Log.d("FacebookLogin", "Initiating Facebook login")
                    loginManager.logInWithReadPermissions(
                        activity,
                        listOf("email", "public_profile")
                    )
                    loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                        override fun onSuccess(loginResult: LoginResult) {
                            Log.d("FacebookLogin", "Success: AccessToken=${loginResult.accessToken.token}")
                            val accessToken = loginResult.accessToken
                            val credential = FacebookAuthProvider.getCredential(accessToken.token)
                            auth.signInWithCredential(credential)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val user = auth.currentUser
                                        if (user != null) {
                                            Log.d("FacebookLogin", "Firebase Auth Success: UID=${user.uid}, Email=${user.email}, DisplayName=${user.displayName}")
                                            val userData = UserModel(
                                                uid = user.uid,
                                                email = user.email ?: "",
                                                username = user.displayName ?: ""
                                            )
                                            scope.launch {
                                                try {
                                                    repository.saveUser(userData)
                                                    Log.d("FacebookLogin", "Navigating to GetStarted: UID=${user.uid}")
                                                    onNavigateToGetStarted(
                                                        GetStartedDataObject(
                                                            uid = user.uid,
                                                            email = user.email ?: "",
                                                            username = user.displayName ?: ""
                                                        )
                                                    )
                                                } catch (e: Exception) {
                                                    errorState.value = "Failed to save user data: ${e.message}"
                                                    Log.e("FacebookLogin", "Error saving user data: ${e.message}")
                                                }
                                            }
                                        } else {
                                            errorState.value = "No user data after successful login"
                                            Log.e("FacebookLogin", "No user data after successful login")
                                        }
                                    } else {
                                        errorState.value = task.exception?.message ?: "Authentication failed"
                                        Log.e("FacebookLogin", "Firebase Auth Failed: ${task.exception?.message}")
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    errorState.value = exception.message ?: "Sign-up failed"
                                    Log.e("FacebookLogin", "Sign-up failed: ${exception.message}")
                                }
                        }

                        override fun onCancel() {
                            errorState.value = "Sign-up cancelled by user"
                            Log.d("FacebookLogin", "Sign-up cancelled by user")
                        }

                        override fun onError(exception: FacebookException) {
                            errorState.value = exception.message ?: "Facebook sign-up error"
                            Log.e("FacebookLogin", "Facebook sign-up error: ${exception.message}")
                        }
                    })
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 17.dp).height(63.dp),
                colors = ButtonColors(
                    containerColor = Facebook2,
                    contentColor = FacebookDescription2,
                    disabledContainerColor = Facebook21,
                    disabledContentColor = FacebookDescription2
                ),
                contentPadding = PaddingValues(vertical = 20.dp, horizontal = 35.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = "letterOfFacebook",
                        alignment = Alignment.CenterStart,
                        modifier = Modifier.padding(end = 45.dp)
                    )
                    Text(
                        "CONTINUE WITH FACEBOOK",
                        fontFamily = FontFamily(Font(R.font.inter_medium)),
                        fontSize = 14.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Button(
                onClick = {
                    Log.d("GoogleLogin", "Initiating Google Sign-In")
                    scope.launch {
                        try {
                            activity.getGoogleSignInClient().signOut().await()
                            Log.d("GoogleLogin", "Signed out from Google to force account picker")
                        } catch (e: Exception) {
                            Log.e("GoogleLogin", "Error signing out: ${e.message}")
                        }
                        val signInIntent = activity.getGoogleSignInClient().signInIntent
                        googleLoginLauncher.launch(signInIntent)
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp).height(63.dp),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = GoogleDescription2,
                    disabledContainerColor = Color.White,
                    disabledContentColor = GoogleDescription2
                ),
                contentPadding = PaddingValues(vertical = 20.dp, horizontal = 30.dp),
                border = BorderStroke(width = 1.dp, color = Grey2)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "letterOfGoogle",
                        alignment = Alignment.CenterStart,
                        modifier = Modifier.padding(end = 40.dp)
                    )
                    Text(
                        "CONTINUE WITH GOOGLE",
                        fontFamily = FontFamily(Font(R.font.inter_medium)),
                        fontSize = 14.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Text(
                "OR SIGN UP WITH EMAIL",
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                color = Grey21
            )
            RoundedCornerTextField(
                text = usernameState.value,
                label = "Username"
            ) {
                usernameState.value = it
            }

            RoundedCornerTextField(
                text = emailState.value,
                label = "Email address"
            ) {
                emailState.value = it
            }

            RoundedCornerTextField(
                text = passwordState.value,
                label = "Password"
            ) {
                passwordState.value = it
            }
            Spacer(modifier = Modifier.height(2.dp))

            if (errorState.value.isNotEmpty()) {
                Text(
                    text = errorState.value,
                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                    fontSize = 12.sp,
                    color = FailureContent,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.padding(bottom = 17.dp),
                    textAlign = TextAlign.Center
                )
            }

            LoginButton(text = "GET STARTED") {
                signUp(
                    auth,
                    emailState.value,
                    passwordState.value,
                    usernameState.value,
                    onSignUpSuccess = { navData ->
                        scope.launch {
                            try {
                                repository.saveUser(
                                    UserModel(
                                        uid = navData.uid,
                                        email = navData.email,
                                        username = navData.username
                                    )
                                )
                                onNavigateToGetStarted(navData)
                            } catch (e: Exception) {
                                errorState.value = "Failed to save user data: ${e.message}"
                            }
                        }
                    },
                    onSignUpFailure = { error ->
                        errorState.value = error
                    }
                )
                emailState.value = ""
                passwordState.value = ""
                usernameState.value = ""
            }
            Text(
                "Forgot Password?",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                fontSize = 14.sp,
                letterSpacing = 0.5.sp,
                color = GoogleDescription2
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "HAVE AN ACCOUNT?",
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    color = Grey1,
                    modifier = Modifier.padding(end = 5.dp),
                    fontSize = 14.sp
                )
                Text(
                    text = "SIGN IN",
                    fontFamily = FontFamily(Font(R.font.inter_semibold)),
                    color = Blue1,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        onNavigateToSignIn(SignInObject)
                    }
                )
            }
        }
    }
}

private fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    username: String,
    onSignUpSuccess: (GetStartedDataObject) -> Unit,
    onSignUpFailure: (String) -> Unit
) {
    if (email.isBlank() || password.isBlank()) {
        onSignUpFailure("Email and password cannot be empty")
        return
    }
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result.user
                if (user != null) {
                    onSignUpSuccess(
                        GetStartedDataObject(
                            uid = user.uid,
                            email = user.email ?: "",
                            username = username
                        )
                    )
                } else {
                    onSignUpFailure("User data not found")
                }
            }
        }
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()
                user?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d("MyLog", "Display name was updated successfully")
                        }
                    }
                Log.d("MyLog", "Sign Up Successful")
            } else {
                Log.d("MyLog", "Sign Up Failure")
            }
        }
        .addOnFailureListener {
            onSignUpFailure(it.message ?: "Sign Up Error")
        }
}