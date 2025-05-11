package com.example.cueflowsapp.main_screen.data

import android.util.Log
import androidx.compose.runtime.Immutable
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.DocumentFormat
import kotlinx.serialization.Serializable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@Serializable
@Immutable
data class DocumentModel(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val format: DocumentFormat = DocumentFormat.TXT, // Укажите значение по умолчанию
    val fileUri: String = "",
    val backgroundColor: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
) {
    // Добавьте пустой конструктор
    constructor() : this("", "", "", DocumentFormat.TXT, "", 0, 0L)
}



class DocumentRepository {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    private fun currentUserId(): String {
        return auth.currentUser?.uid ?: throw AuthRequiredException()
    }

    suspend fun saveDocument(document: DocumentModel): String {
        try {
            val userId = currentUserId()
            val docRef = db.collection("users")
                .document(userId)
                .collection("documents")
                .document()

            val docWithId = document.copy(id = docRef.id)
            docRef.set(docWithId).await()
            return docRef.id
        } catch (e: Exception) {
            throw RepositoryException("Failed to save document", e)
        }
    }

    fun getUserDocuments(): Flow<List<DocumentModel>> {
        return try {
            val userId = currentUserId()
            db.collection("users")
                .document(userId)
                .collection("documents")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .snapshots()
                .map { snapshot ->
                    snapshot.documents.mapNotNull { doc ->
                        try {
                            doc.toObject(DocumentModel::class.java)
                        } catch (e: Exception) {
                            null // Пропускаем некорректные документы
                        }
                    }
                }
        } catch (e: AuthRequiredException) {
            flow { emit(emptyList()) } // Возвращаем пустой список если не авторизованы
        }
    }

    suspend fun getDocumentById(id: String): DocumentModel? {
        return try {
            val userId = currentUserId()
            db.collection("users")
                .document(userId)
                .collection("documents")
                .document(id)
                .get()
                .await()
                .toObject(DocumentModel::class.java)
        } catch (e: Exception) {
            null
        }
    }
}

class AuthRequiredException : Exception("Authentication required")
class RepositoryException(message: String, cause: Throwable?) : Exception(message, cause)

class DocumentListViewModel(
    private val repository: DocumentRepository = DocumentRepository()
) : ViewModel() {
    private val _documents = MutableStateFlow<List<DocumentModel>>(emptyList())
    val documents: StateFlow<List<DocumentModel>> = _documents

    private var documentsJob: Job? = null

    init {
        loadDocuments()
    }

    private fun loadDocuments() {
        documentsJob?.cancel()
        documentsJob = viewModelScope.launch {
            repository.getUserDocuments()
                .catch { e ->
                    Log.e("DocumentListVM", "Error loading documents", e)
                    _documents.value = emptyList()
                }
                .collect { docs ->
                    _documents.value = docs
                }
        }
    }

    fun saveDocument(document: DocumentModel) {
        viewModelScope.launch {
            try {
                repository.saveDocument(document)
            } catch (e: Exception) {
                Log.e("DocumentListVM", "Error saving document", e)
            }
        }
    }

    fun clear() {
        _documents.value = emptyList()
    }
}