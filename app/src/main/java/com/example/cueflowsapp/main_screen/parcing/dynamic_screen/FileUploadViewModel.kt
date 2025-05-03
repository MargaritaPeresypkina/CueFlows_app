package com.example.cueflowsapp.main_screen.parcing.dynamic_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cueflowsapp.main_screen.parcing.dynamic_destinations.DocumentFormat

class FileUploadViewModel: ViewModel() {
    private val _uploadState = MutableLiveData(UploadState())
    val uploadState: LiveData<UploadState> = _uploadState

    fun updateUploadState(
        isUploaded: Boolean,
        fileName: String?,
        fileUri: String? = null,
        backgroundColor: Int? = null,
        formatType: DocumentFormat? = null
    ) {
        _uploadState.value = UploadState(
            isUploaded = isUploaded,
            fileName = fileName,
            fileUri = fileUri,
            backgroundColor = backgroundColor,
            formatType = formatType
        )
    }

    fun updateUploadState(isUploaded: Boolean, fileName: String?, fileUri: String? = null){
        _uploadState.value = UploadState(isUploaded, fileName, fileUri?.toString())
    }

    fun resetUploadState() {
        _uploadState.value = UploadState()
    }
}

data class UploadState(
    val isUploaded: Boolean = false,
    val fileName: String? = null,
    val fileUri: String? = null,
    val backgroundColor: Int? = null,
    val formatType: DocumentFormat? = null
)