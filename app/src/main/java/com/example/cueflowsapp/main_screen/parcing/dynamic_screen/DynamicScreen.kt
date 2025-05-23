package com.example.cueflowsapp.main_screen.parcing.dynamic_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cueflowsapp.R
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.FormatMapper.toDocumentFormat
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.NavRoutes
import com.example.cueflowsapp.main_screen.parcing.dynamic_screen.components.ButtonsBlock
import com.example.cueflowsapp.main_screen.parcing.dynamic_screen.data.ActionButton
import com.example.cueflowsapp.main_screen.parcing.dynamic_screen.data.DynamicScreenObject
import com.example.cueflowsapp.main_screen.parcing.dynamic_screen.data.FileFormats
import com.example.cueflowsapp.ui.theme.TextDocsGray
import com.example.cueflowsapp.ui.theme.WhiteReturnButton

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun DynamicScreen(
    content: DynamicScreenObject,
    onNavigateToPreviousScreen: () -> Unit,
    onNavigateToDocumentViewer: (NavRoutes.DocumentViewer) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val horizontalSpacing = 37.dp
    val spaceBetweenButtons = 9.dp
    val buttonWidth = (screenWidthDp - (horizontalSpacing * 2 + spaceBetweenButtons)) / 2

    Box(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        var currentButton by remember { mutableStateOf<ActionButton?>(null) }
        var showUploadDialog = remember { mutableStateOf(false) }
        val viewModel: FileUploadViewModel = viewModel()
        val uploadState = viewModel.uploadState.observeAsState(UploadState())

        var isLoading by remember { mutableStateOf(false) }
        var isNavigating by remember { mutableStateOf(false) } // New state for navigation loading
        var errorMessage by remember { mutableStateOf<String?>(null) }

        val fileUploadViewModel: FileUploadViewModel = viewModel()
        LaunchedEffect(errorMessage) {
            if (errorMessage != null) {
                fileUploadViewModel.updateUploadState(
                    isUploaded = false,
                    fileName = null,
                    errorMessage = errorMessage
                )
            }
        }
        val filePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                if (uri == null) {
                    errorMessage = "File selection cancelled"
                    return@rememberLauncherForActivityResult
                }
                isLoading = true
                errorMessage = null

                try {
                    val fileNameFromUri = getFileNameFromUri(context, uri)
                    val fileExtension = fileNameFromUri.substringAfterLast(".", "").lowercase()
                    Log.d("fileNameFromUri", fileNameFromUri)
                    Log.d("fileExtension", fileExtension)
                    val formatType = fileExtension.toDocumentFormat()
                    Log.d("formatType", "$formatType")

                    if (currentButton?.destination?.lowercase() == fileExtension && formatType != null) {
                        viewModel.updateUploadState(
                            isUploaded = true,
                            fileName = fileNameFromUri,
                            fileUri = uri.toString(),
                            backgroundColor = currentButton!!.backColor,
                            formatType = formatType
                        )
                    } else {
                        errorMessage = "Invalid file format. Please upload ${currentButton?.destination} file."
                    }
                } catch (e: Exception) {
                    errorMessage = "Error uploading file: ${e.localizedMessage}"
                    Log.e("FileUpload", "Error reading file", e)
                } finally {
                    isLoading = false
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = content.image),
                    contentDescription = "header_${content.screenName}",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .padding(top = 43.dp, start = 29.dp)
                        .background(WhiteReturnButton, shape = CircleShape)
                        .size(width = 55.dp, height = 55.dp)
                        .border(
                            width = 1.dp,
                            shape = CircleShape,
                            color = Color.Black
                        )
                        .clickable { onNavigateToPreviousScreen() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = "arrow",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.background_sign),
                    contentDescription = "background_sign",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                Image(
                    painter = painterResource(id = R.drawable.back_sign_sacond),
                    contentDescription = "background_sign",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (230).dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 37.dp, end = 37.dp, top = 28.dp)
                ) {
                    Text(
                        content.title,
                        color = Color(content.titleColor),
                        fontFamily = FontFamily(Font(R.font.inter_bold)),
                        fontSize = 30.sp
                    )
                    Spacer(Modifier.height(3.dp))
                    Text(
                        "Please, choose the format you need",
                        color = TextDocsGray,
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(9.dp))
                    content.firstSubtitle?.let { subtitle ->
                        content.firstSubtitleColor?.let { subtitleColor ->
                            Text(
                                subtitle,
                                color = Color(subtitleColor),
                                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                                fontSize = 22.sp
                            )
                        }
                    }
                    Spacer(Modifier.height(11.dp))
                    ButtonsBlock(
                        buttons = content.firstButtons,
                        buttonWidth = buttonWidth,
                        spaceBetweenButtons = spaceBetweenButtons,
                        onButtonClick = { button: ActionButton ->
                            currentButton = button
                            showUploadDialog.value = true
                        }
                    )
                    content.firstDescription?.let { firstDesc ->
                        content.firstDescColor?.let { firstDescColor ->
                            Spacer(Modifier.height(9.dp))
                            Text(
                                firstDesc,
                                color = Color(firstDescColor),
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.inter_regular))
                            )
                        }
                    }
                    content.secondSubtitle?.let { secondSubtitle ->
                        content.secondSubtitleColor?.let { secondSubColor ->
                            Spacer(Modifier.height(19.dp))
                            Text(
                                secondSubtitle,
                                color = Color(secondSubColor),
                                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                                fontSize = 22.sp
                            )
                        }
                    }
                    Spacer(Modifier.height(11.dp))
                    ButtonsBlock(
                        buttons = content.secondButtons,
                        buttonWidth = buttonWidth,
                        spaceBetweenButtons = spaceBetweenButtons,
                        onButtonClick = { button: ActionButton ->
                            currentButton = button
                            showUploadDialog.value = true
                        }
                    )
                    content.secondDescription?.let { secondDesc ->
                        content.secondDescColor?.let { secondDescColor ->
                            Spacer(Modifier.height(9.dp))
                            Text(
                                secondDesc,
                                color = Color(secondDescColor),
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.inter_regular))
                            )
                        }
                    }
                }
            }
        }
        if (showUploadDialog.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .clickable(enabled = false) {}
            )
            FileUploadDialog(
                isFileUploaded = uploadState.value.isUploaded,
                fileName = uploadState.value.fileName,
                dialogName = currentButton?.text ?: "file",
                onUploadClick = {
                    currentButton?.let { button ->
                        val mimeType = FileFormats.getMimeType(button.destination)
                        filePickerLauncher.launch(mimeType)
                    }
                },
                onNextStepClick = {
                    isNavigating = true
                    uploadState.value.let { state ->
                        if (state.isUploaded && state.fileUri != null && state.backgroundColor != null && state.formatType != null) {
                            onNavigateToDocumentViewer(
                                NavRoutes.DocumentViewer(
                                    documentId = null,
                                    fileUri = state.fileUri,
                                    fileName = state.fileName ?: "Document",
                                    backgroundColor = state.backgroundColor,
                                    formatType = state.formatType
                                )
                            )
                        }
                    }
                    showUploadDialog.value = false
                    viewModel.resetUploadState()
                    errorMessage = null
                    isNavigating = false
                },
                onDismiss = {
                    showUploadDialog.value = false
                    viewModel.resetUploadState()
                    errorMessage = null
                },
                isLoading = isLoading,
                errorMessage = errorMessage
            )
        }
        if (content.screenName != "Text Docs") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)).padding(horizontal = 30.dp, vertical = 55.dp)
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.White)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow),
                            contentDescription = "arrow",
                            modifier = Modifier.size(20.dp).clickable { onNavigateToPreviousScreen() }
                        )
                        Text(
                            "Oops...",
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.inter_semibold)),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Text(
                        text = "The functionality for ${content.title} is not yet available.\n Stay tuned for more news in the near future.",
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        if (isNavigating) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.Black,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}