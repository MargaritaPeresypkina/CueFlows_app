package com.example.cueflowsapp.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cueflowsapp.main_screen.button_menu.BottomMenu
import com.example.cueflowsapp.main_screen.data.BottomMenuPage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun MainScreen() {
    val auth = Firebase.auth
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val currentPage = remember {mutableStateOf<BottomMenuPage>(BottomMenuPage.Library)}

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomMenu(
                onLibrary = {currentPage.value = BottomMenuPage.Library},
                onList = {currentPage.value = BottomMenuPage.List},
                onAI = {currentPage.value = BottomMenuPage.AI},
                onAccount = {currentPage.value = BottomMenuPage.Account}
            )
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when(currentPage.value){
                is BottomMenuPage.Library -> LibraryScreen()
                is BottomMenuPage.List -> ListScreen()
                is BottomMenuPage.Account -> AccountScreen()
                is BottomMenuPage.AI -> AIScreen()
            }
        }
    }
}