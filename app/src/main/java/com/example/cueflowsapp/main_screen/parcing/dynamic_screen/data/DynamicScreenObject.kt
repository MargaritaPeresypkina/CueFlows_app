package com.example.cueflowsapp.main_screen.parcing.dynamic_screen.data

import kotlinx.serialization.Serializable

@Serializable
data class DynamicScreenObject(
    val screenName: String,
    val title: String,
    val titleColor: Int,
    val image: Int,
    val firstSubtitle: String? = null,
    val firstSubtitleColor: Int? = null,
    val firstButtons: List<ActionButton> = emptyList(),
    val firstDescription: String? = null,
    val firstDescColor: Int? = null,
    val secondSubtitle: String? = null,
    val secondSubtitleColor: Int? = null,
    val secondButtons: List<ActionButton> = emptyList(),
    val secondDescription: String? = null,
    val secondDescColor: Int? = null
)