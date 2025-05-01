package com.example.cueflowsapp.main_screen.parcing.dynamic_screen.data

import kotlinx.serialization.Serializable

@Serializable
data class ActionButton
(
    val text: String,
    val destination: String,
    val backColor: Int,
    val informationAbout: String
)