package com.example.cueflowsapp.login.data

import kotlinx.serialization.Serializable

@Serializable
data class GetStartedDataObject(
    val uid: String = "",
    val username: String = "",
    val email: String = ""
)
