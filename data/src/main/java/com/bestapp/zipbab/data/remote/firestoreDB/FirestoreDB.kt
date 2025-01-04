package com.bestapp.zipbab.data.remote.firestoreDB

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Transaction
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreDB @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) {
    suspend fun getImageUrl(path: String): String {
        val storageRef = firebaseStorage.reference.child(path)
        return storageRef.downloadUrl.await().toString()
    }

    fun getStorageRef(path: String): StorageReference {
        return firebaseStorage.reference.child(path)
    }

    fun getTermsDB(): CollectionReference {
        return firebaseFirestore.collection("terms")
    }

    fun getCategoryDB(): CollectionReference {
        return firebaseFirestore.collection("category")
    }

    fun getUserDB(): CollectionReference {
        return firebaseFirestore.collection("users")
    }

    fun getUserPrivateDB(): CollectionReference {
        return firebaseFirestore.collection("userPrivate")
    }

    fun getFlashMeetingDB(): CollectionReference {
        return firebaseFirestore.collection("flashMeeting")
    }

    fun getImagesDB(): StorageReference {
        return firebaseStorage.reference.child("images/")
    }

    fun getPostDB(): CollectionReference {
        return firebaseFirestore.collection("posts")
    }

    fun getReportDB(): CollectionReference {
        return firebaseFirestore.collection("report")
    }

    fun getAccessDB(): CollectionReference {
        return firebaseFirestore.collection("token")
    }

    fun getPolicyDB(): CollectionReference {
        return firebaseFirestore.collection("policy")
    }

    fun getNoticeDB(): CollectionReference {
        return firebaseFirestore.collection("notice")
    }

    fun <T> runTransaction(transaction: (transaction: Transaction) -> T): Task<T> {
        return firebaseFirestore.runTransaction {
            transaction(it)
        }
    }

}
