package com.example.cueflowsapp.main_screen.parcing.dynamic_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FileUploadViewModel: ViewModel() {
    private val _uploadState = MutableLiveData(UploadState())
    val uploadState: LiveData<UploadState> = _uploadState

    fun updateUploadState(isUploaded: Boolean, fileName: String?){
        _uploadState.value = UploadState(isUploaded, fileName)
    }
}

data class UploadState(
    val isUploaded: Boolean = false,
    val fileName: String? = null
)