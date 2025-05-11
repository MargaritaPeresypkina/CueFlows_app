package com.example.cueflowsapp.main_screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.cueflowsapp.R
import com.example.cueflowsapp.login.data.SignInObject
import com.example.cueflowsapp.login.data.SignUpObject
import com.example.cueflowsapp.main_screen.data.DocumentListViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavHostController,
    documentListViewModel: DocumentListViewModel = viewModel(),
) {
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val showDialog = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Profile image state
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    val storage = Firebase.storage
    val storageRef = storage.reference
    val profileImageRef = currentUser?.uid?.let { storageRef.child("profile_images/$it.jpg") }

    // Image picker launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            profileImageUri = it
            // Upload the image to Firebase Storage
            coroutineScope.launch {
                try {
                    profileImageRef?.putFile(it)?.await()
                    Log.d("AccountScreen", "Profile image uploaded successfully")
                } catch (e: Exception) {
                    Log.e("AccountScreen", "Error uploading profile image", e)
                }
            }
        }
    }

    // Load profile image when screen appears
    LaunchedEffect(Unit) {
        try {
            val downloadUrl = profileImageRef?.downloadUrl?.await()
            downloadUrl?.let {
                profileImageUri = it
            }
        } catch (e: Exception) {
            Log.e("AccountScreen", "Error loading profile image", e)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Account") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            // Profile section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.LightGray, CircleShape)
                        .clickable { launcher.launch("image/*") }
                ) {
                    if (profileImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(profileImageUri)
                                    .build()
                            ),
                            contentDescription = "Profile image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Default profile image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = currentUser?.displayName ?: "User",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = currentUser?.email ?: "",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Account options
            AccountOptionItem(
                icon = Icons.Default.Settings,
                title = "Account settings",
                onClick = { /* Handle account settings click */ }
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            AccountOptionItem(
                icon = Icons.Default.Call,
                title = "Languages",
                onClick = { /* Handle languages click */ }
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            AccountOptionItem(
                icon = Icons.Default.Info,
                title = "App information",
                onClick = { /* Handle app info click */ }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Action buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                documentListViewModel.clear()
                                signOut(auth)
                                navController.navigate(SignInObject) {
                                    popUpTo(0)
                                }
                            } catch (e: Exception) {
                                errorMessage.value = "Sign out failed: ${e.localizedMessage}"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text("Sign Out")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { showDialog.value = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete account")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            errorMessage.value?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Delete account dialog
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Confirm Deletion") },
                    text = {
                        Column {
                            Text("Enter your email and password to delete account")
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = emailState.value,
                                onValueChange = { emailState.value = it },
                                label = { Text("Email") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = passwordState.value,
                                onValueChange = { passwordState.value = it },
                                label = { Text("Password") },
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    try {
                                        deleteAccount(
                                            auth = auth,
                                            email = emailState.value,
                                            password = passwordState.value,
                                            onSuccess = {
                                                documentListViewModel.clear()
                                                navController.navigate(SignUpObject) {
                                                    popUpTo(0)
                                                }
                                            },
                                            onError = { message ->
                                                errorMessage.value = message
                                            }
                                        )
                                        showDialog.value = false
                                    } catch (e: Exception) {
                                        errorMessage.value = "Error: ${e.localizedMessage}"
                                    }
                                }
                            },
                            enabled = emailState.value.isNotBlank() && passwordState.value.isNotBlank()
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDialog.value = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AccountOptionItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            fontSize = 18.sp
        )
    }
}

private suspend fun deleteAccount(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    try {
        // 1. Реаутентификация
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser?.reauthenticate(credential)?.await()

        // 2. Удаление аккаунта
        auth.currentUser?.delete()?.await()

        // 3. Успешное завершение
        onSuccess()
    } catch (e: Exception) {
        val errorMsg = when (e) {
            is FirebaseAuthInvalidCredentialsException -> "Invalid email or password"
            is FirebaseAuthRecentLoginRequiredException -> "Recent login required. Please sign in again."
            else -> "Failed to delete account: ${e.localizedMessage}"
        }
        onError(errorMsg)
        throw e
    }
}

private suspend fun signOut(auth: FirebaseAuth) {
    withContext(Dispatchers.IO) {
        auth.signOut()
    }
}



//package com.example.cueflowsapp.main_screen
//
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.example.cueflowsapp.login.data.SignInObject
//import com.example.cueflowsapp.login.data.SignUpObject
//import com.example.cueflowsapp.main_screen.data.DocumentListViewModel
//import com.google.firebase.auth.EmailAuthProvider
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
//import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//import kotlinx.coroutines.withContext
//
//@Composable
//fun AccountScreen(
//    navController: NavHostController,
//    documentListViewModel: DocumentListViewModel = viewModel()
//) {
//    val auth = Firebase.auth
//    val emailState = remember { mutableStateOf("") }
//    val passwordState = remember { mutableStateOf("") }
//    val coroutineScope = rememberCoroutineScope()
//    val showDialog = remember { mutableStateOf(false) }
//    val errorMessage = remember { mutableStateOf<String?>(null) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(color = Color.White)
//            .padding(top = 50.dp, bottom = 0.dp, start = 20.dp, end = 20.dp)
//    ) {
//        Text("Hello, ${auth.currentUser?.displayName}")
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Button(
//            onClick = {
//                coroutineScope.launch {
//                    try {
//                        documentListViewModel.clear()
//                        signOut(auth)
//                        navController.navigate(SignInObject) {
//                            popUpTo(0)
//                        }
//                    } catch (e: Exception) {
//                        errorMessage.value = "Sign out failed: ${e.localizedMessage}"
//                    }
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Sign out")
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Button(
//            onClick = { showDialog.value = true },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Delete account")
//        }
//
//        if (showDialog.value) {
//            AlertDialog(
//                onDismissRequest = { showDialog.value = false },
//                title = { Text("Confirm Deletion") },
//                text = {
//                    Column {
//                        Text("Enter your email and password to delete account")
//                        Spacer(modifier = Modifier.height(8.dp))
//                        OutlinedTextField(
//                            value = emailState.value,
//                            onValueChange = { emailState.value = it },
//                            label = { Text("Email") },
//                            modifier = Modifier.fillMaxWidth()
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        OutlinedTextField(
//                            value = passwordState.value,
//                            onValueChange = { passwordState.value = it },
//                            label = { Text("Password") },
//                            visualTransformation = PasswordVisualTransformation(),
//                            modifier = Modifier.fillMaxWidth()
//                        )
//                    }
//                },
//                confirmButton = {
//                    Button(
//                        onClick = {
//                            coroutineScope.launch {
//                                try {
//                                    deleteAccount(
//                                        auth = auth,
//                                        email = emailState.value,
//                                        password = passwordState.value,
//                                        onSuccess = {
//                                            documentListViewModel.clear()
//                                            navController.navigate(SignUpObject) {
//                                                popUpTo(0)
//                                            }
//                                        },
//                                        onError = { message ->
//                                            errorMessage.value = message
//                                        }
//                                    )
//                                    showDialog.value = false
//                                } catch (e: Exception) {
//                                    errorMessage.value = "Error: ${e.localizedMessage}"
//                                }
//                            }
//                        },
//                        enabled = emailState.value.isNotBlank() && passwordState.value.isNotBlank()
//                    ) {
//                        Text("Delete")
//                    }
//                },
//                dismissButton = {
//                    TextButton(
//                        onClick = { showDialog.value = false }
//                    ) {
//                        Text("Cancel")
//                    }
//                }
//            )
//        }
//
//        errorMessage.value?.let { message ->
//            Text(
//                text = message,
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//        }
//    }
//}
//
//private suspend fun deleteAccount(
//    auth: FirebaseAuth,
//    email: String,
//    password: String,
//    onSuccess: () -> Unit,
//    onError: (String) -> Unit
//) {
//    try {
//        // 1. Реаутентификация
//        val credential = EmailAuthProvider.getCredential(email, password)
//        auth.currentUser?.reauthenticate(credential)?.await()
//
//        // 2. Удаление аккаунта
//        auth.currentUser?.delete()?.await()
//
//        // 3. Успешное завершение
//        onSuccess()
//    } catch (e: Exception) {
//        val errorMsg = when (e) {
//            is FirebaseAuthInvalidCredentialsException -> "Invalid email or password"
//            is FirebaseAuthRecentLoginRequiredException -> "Recent login required. Please sign in again."
//            else -> "Failed to delete account: ${e.localizedMessage}"
//        }
//        onError(errorMsg)
//        throw e
//    }
//}
//
//private suspend fun signOut(auth: FirebaseAuth) {
//    withContext(Dispatchers.IO) {
//        auth.signOut()
//    }
//}
////private suspend fun signOut(auth: FirebaseAuth) {
////    auth.signOut()
////    delay(100) // Даем время на завершение операций
////}
//
////private fun deleteAccount(auth: FirebaseAuth, email: String, password: String){
////    val credential = EmailAuthProvider.getCredential(email, password)
////    auth.currentUser?.reauthenticate(credential)?.addOnCompleteListener {
////        if(it.isSuccessful){
////            auth.currentUser?.delete()?.addOnCompleteListener {
////                if(it.isSuccessful){
////                    Log.d("MyLog", "Account was deleted")
////                } else {
////                    Log.d("MyLog", "Account was not deleted, failure")
////                }
////            }
////        }
////    }
////}