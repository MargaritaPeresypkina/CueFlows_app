package com.example.cueflowsapp.main_screen.data

import kotlinx.serialization.Serializable

@Serializable
data class MainScreenDataObject(
    val uid: String = "",
    val email: String = "",
    val username: String = ""
)
