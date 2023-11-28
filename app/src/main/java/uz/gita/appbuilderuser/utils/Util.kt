package uz.gita.appbuilderuser.utils

import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import uz.gita.appbuilderuser.app.App


suspend fun <T> Task<QuerySnapshot>.getAllSync(mapper: (DocumentSnapshot) -> T): Result<List<T>> {
    val deferred = CompletableDeferred<Result<List<T>>>()
    addOnSuccessListener {
        val ls = it.documents.map { mapper(it) }
        deferred.complete(Result.success(ls))
    }
        .addOnFailureListener { deferred.complete(Result.failure(it)) }
    return deferred.await()
}

fun <T> Task<QuerySnapshot>.getAll(mapper: (DocumentSnapshot) -> T): Flow<Result<List<T>>> = flow {
    emit(getAllSync(mapper))
}

fun <T> Task<QuerySnapshot>.getAllLive(
    ref: CollectionReference,
    mapper: (DocumentSnapshot) -> T
): Flow<Result<List<T>>> = channelFlow {
    ref.addSnapshotListener { value, error ->
        trySend(runBlocking { ref.get().getAllSync(mapper) })
    }
    awaitClose { }
}

fun myToast(message: String) {
    Toast.makeText(App.instent, message, Toast.LENGTH_LONG).show()
}