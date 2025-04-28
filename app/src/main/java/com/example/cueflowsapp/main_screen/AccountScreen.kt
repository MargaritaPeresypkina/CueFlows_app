package com.example.cueflowsapp.main_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun AccountScreen() {
    val auth = Firebase.auth
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(top = 50.dp, bottom = 0.dp,  start = 20.dp, end = 20.dp)
    ){

        Text("Hello, ${auth.currentUser?.displayName}")
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick= {
                signOut(auth)
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("sign out")

        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick= {
                deleteAccount(auth, emailState.value, passwordState.value)
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("delete account")
        }

    }

}

private fun signOut(auth: FirebaseAuth){
    auth.signOut()
}

private fun deleteAccount(auth: FirebaseAuth, email: String, password: String){
    val credential = EmailAuthProvider.getCredential(email, password)
    auth.currentUser?.reauthenticate(credential)?.addOnCompleteListener {
        if(it.isSuccessful){
            auth.currentUser?.delete()?.addOnCompleteListener {
                if(it.isSuccessful){
                    Log.d("MyLog", "Account was deleted")
                } else {
                    Log.d("MyLog", "Account was not deleted, failure")
                }
            }
        }
    }
}