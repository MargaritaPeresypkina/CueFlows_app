package com.example.cueflowsapp.login

import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.cueflowsapp.ui.theme.Facebook2
import com.example.cueflowsapp.ui.theme.Facebook21
import com.example.cueflowsapp.ui.theme.FacebookDescription2
import com.example.cueflowsapp.ui.theme.FailureContent
import com.example.cueflowsapp.ui.theme.GoogleDescription2
import com.example.cueflowsapp.ui.theme.Grey2
import com.example.cueflowsapp.ui.theme.Grey21
import com.example.cueflowsapp.ui.theme.LightBlack2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SignUp(
    onNavigateToPreviousScreen:() -> Unit,
    onNavigateToGetStarted: (GetStartedDataObject) -> Unit
) {
    val auth = remember {Firebase.auth}

    val errorState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val usernameState = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
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
        ){
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
            ){
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
                onClick = {},
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
                ){
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
                onClick = {},
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
                ){
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
            ){
                usernameState.value = it
            }

            RoundedCornerTextField(
                text = emailState.value,
                label = "Email address"
            ){
                emailState.value = it
            }

            RoundedCornerTextField(
                text = passwordState.value,
                label = "Password",
            ){
                passwordState.value = it
            }
            Spacer(modifier = Modifier.height(2.dp))

            if(errorState.value.isNotEmpty()){
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

//            Row(
//                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ){
//                Row(){
//                    Text(
//                        "I have read the ",
//                        fontFamily = FontFamily(Font(R.font.instrument_sans_medium)),
//                        color = Grey21,
//                        fontSize = 14.sp,
//                        letterSpacing = 0.5.sp,
//                    )
//                    Text(
//                        "Private Policy",
//                        fontFamily = FontFamily(Font(R.font.instrument_sans_medium)),
//                        fontSize = 14.sp,
//                        color = Facebook2,
//                        letterSpacing = 0.5.sp
//                    )
//                }
//                Checkbox(
//                    checked = false,
//                    onCheckedChange = {},
//                    colors = CheckboxDefaults.colors(uncheckedColor = Grey21, checkedColor = Facebook2)
//                )
//            }
            LoginButton(text = "GET STARTED") {
                signUp(
                    auth,
                    emailState.value,
                    passwordState.value,
                    usernameState.value,
                    onSignUpSuccess = { navData ->
                        onNavigateToGetStarted(navData)
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
){
    //Protect from empty fields
    if (email.isBlank() || password.isBlank()){
        onSignUpFailure("Email and password cannot be empty")
        return
    }
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener{ task ->
            if(task.isSuccessful)
                onSignUpSuccess(
                    GetStartedDataObject(
                        //Add checks for failures later
                        task.result.user?.uid!!,
                        task.result.user?.email!!
                    )
                )
        }
        //add username
        .addOnCompleteListener { task ->
            if(task.isSuccessful){
                val user = auth.currentUser

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()

                user?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener {
                        if(it.isSuccessful){
                            Log.d("MyLog", "Display name was updated successfully")
                        }
                    }
                Log.d("MyLog", "Sign Up Successful")
            } else {
                Log.d("MyLog", "Sign Up Failure")
            }
        }
        //Handle Error
        .addOnFailureListener{
            onSignUpFailure(it.message ?: "Sing Up Error")
        }
}