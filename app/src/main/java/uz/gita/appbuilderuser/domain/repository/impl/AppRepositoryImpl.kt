package uz.gita.appbuilderuser.domain.repository.impl

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.appbuilderuser.data.model.UserData
import uz.gita.appbuilderuser.data.model.ComponentsModel
import uz.gita.appbuilderuser.domain.repository.AppRepository
import uz.gita.appbuilderuser.utils.toUserData
import uz.gita.appbuilderuser.utils.getAll
import javax.inject.Inject


class AppRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val realtimeDB: FirebaseDatabase,
) : AppRepository {

    private val scope = CoroutineScope(Dispatchers.IO + Job())
    override suspend fun loginUser(userData: UserData): Flow<Boolean> = callbackFlow {
        firebaseFirestore.collection("users")
            .get()
            .getAll {
                return@getAll UserData(
                    it.data?.getOrDefault("name", "") as String,
                    it.data?.getOrDefault("password", "") as String
                )
            }
            .onEach {
                it.onSuccess {
                    Log.d("TTT", "Succses")
                    Log.d("TTT", "loginUser: ${it.size}")
                    it.forEach {
                        Log.d("TTT", "loginUser: ${it.name}")
                        if (it.name.equals(userData.name) && it.password.equals(userData.password)) {
                            Log.d("TTT", "loginUser: ${it.name}")
                            trySend(true)
                            return@forEach
                        } else {
                            trySend(false)
                        }
                    }
                }
                it.onFailure {
                    Log.d("TTT", "Failer")
                    trySend(false)
                }
            }
            .launchIn(scope)

        awaitClose()
    }

    override fun getAllData(name: String): Flow<List<ComponentsModel>> = callbackFlow {
        realtimeDB.getReference("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot.children.map { it.toUserData() })
            }

            override fun onCancelled(error: DatabaseError) {
                //...
            }

        })
    }
}