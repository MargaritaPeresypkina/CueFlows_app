package com.example.cueflowsapp.main_screen.data

import android.util.Base64
import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cueflowsapp.main_screen.parcing.formats_handling.data.DocumentFormat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Serializable
@Immutable
data class DocumentModel(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val images: List<ImageData> = emptyList(),
    val tables: List<String> = emptyList(),
    val format: DocumentFormat = DocumentFormat.TXT,
    val fileUri: String = "",
    val backgroundColor: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
) {
    constructor() : this("", "", "", emptyList(), emptyList(), DocumentFormat.TXT, "", 0, 0L)

    fun serializeTables(tables: List<List<List<String>>>): List<String> {
        return tables.map { table ->
            Json.encodeToString(table)
        }
    }

    fun deserializeTables(): List<List<List<String>>> {
        return tables.mapNotNull { tableJson ->
            try {
                Json.decodeFromString<List<List<String>>>(tableJson)
            } catch (e: Exception) {
                Log.e("DocumentModel", "Error deserializing table", e)
                null
            }
        }
    }

    @Serializable
    data class ImageData(
        val data: String = "",
        val width: Int = 0,
        val height: Int = 0
    ) {

        constructor(byteArray: ByteArray, width: Int, height: Int) : this(
            Base64.encodeToString(byteArray, Base64.DEFAULT),
            width,
            height
        )

        fun toByteArray(): ByteArray = Base64.decode(data, Base64.DEFAULT)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is ImageData) return false
            return data == other.data && width == other.width && height == other.height
        }

        override fun hashCode(): Int {
            var result = data.hashCode()
            result = 31 * result + width
            result = 31 * result + height
            return result
        }
    }
}

@Serializable
@Immutable
data class UserModel(
    val uid: String = "",
    val email: String = "",
    val username: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

class DocumentRepository {
    private val db: FirebaseFirestore = Firebase.firestore
    private val auth = Firebase.auth

    private fun currentUserId(): String {
        return auth.currentUser?.uid ?: throw AuthRequiredException()
    }

    suspend fun saveUser(user: UserModel) {
        try {
            val userId = user.uid
            val docRef = db.collection("users").document(userId)
            docRef.set(user).await()
        } catch (e: Exception) {
            Log.e("DocumentRepository", "Failed to save user: ${e.message}", e)
            if (e is com.google.firebase.firestore.FirebaseFirestoreException && e.code == com.google.firebase.firestore.FirebaseFirestoreException.Code.PERMISSION_DENIED) {
                throw RepositoryException("Insufficient permissions to save user data. Please check Firestore rules.", e)
            }
            throw RepositoryException("Failed to save user", e)
        }
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
            Log.e("DocumentRepository", "Failed to save document", e)
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
                            Log.e("DocumentRepository", "Error parsing document ${doc.id}", e)
                            null
                        }
                    }
                }
                .catch { e ->
                    Log.e("DocumentRepository", "Error fetching documents", e)
                    emit(emptyList())
                }
        } catch (e: AuthRequiredException) {
            flow { emit(emptyList()) }
        }
    }

    suspend fun getDocumentById(id: String): DocumentModel? {
        return try {
            val userId = currentUserId()
            val doc = db.collection("users")
                .document(userId)
                .collection("documents")
                .document(id)
                .get()
                .await()
                .toObject(DocumentModel::class.java)
            doc
        } catch (e: Exception) {
            Log.e("DocumentRepository", "Error fetching document $id", e)
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

    fun saveDocument(document: DocumentModel): String {
        var documentId = ""
        viewModelScope.launch {
            try {
                documentId = repository.saveDocument(document)
            } catch (e: Exception) {
                Log.e("DocumentListVM", "Error saving document", e)
            }
        }
        return documentId
    }

    suspend fun getDocumentById(id: String): DocumentModel? {
        return repository.getDocumentById(id)
    }

    fun clear() {
        _documents.value = emptyList()
    }
}