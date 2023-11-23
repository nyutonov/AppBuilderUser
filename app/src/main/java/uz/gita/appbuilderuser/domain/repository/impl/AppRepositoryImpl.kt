package uz.gita.appbuilderuser.domain.repository.impl

import android.content.Context.MODE_PRIVATE
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
import kotlinx.coroutines.withContext
import uz.gita.appbuilderuser.app.App
import uz.gita.appbuilderuser.data.model.ComponentsModel
import uz.gita.appbuilderuser.data.model.DrawsData
import uz.gita.appbuilderuser.data.model.UserData
import uz.gita.appbuilderuser.domain.repository.AppRepository
import uz.gita.appbuilderuser.utils.getAll
import uz.gita.appbuilderuser.utils.toUserData
import java.util.UUID
import javax.inject.Inject


class AppRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val realtimeDB: FirebaseDatabase,
) : AppRepository {

    fun setScreenON(block: (Boolean) -> Unit) {
        changeStateListener = block
    }

    private var changeStateListener: ((Boolean) -> Unit)? = null

    private val sharedPref = App.instent.getSharedPreferences("MySharedPref", MODE_PRIVATE)
    private var isLogin: Boolean = false

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
                            isLogin = true
                            changeStateListener?.invoke(isLogin)
                            return@forEach
                        } else {
                            isLogin = false
                            changeStateListener?.invoke(false)
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
        realtimeDB.getReference("users").child(name).child("components")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(snapshot.children.map { it.toUserData() })
                }

                override fun onCancelled(error: DatabaseError) {
                    //...
                }

            })
        awaitClose()
    }

    override fun isLogin(): Boolean = sharedPref.getBoolean("isLogin", false)

    override fun setLogin(login: Boolean) {
        sharedPref.edit().putBoolean("isLogin", login).apply()
    }

    override fun setUserName(name: String) {
        sharedPref.edit().putString("name", name).apply()
    }

    override fun getUserName(): String = sharedPref.getString("name", "")!!

    override suspend fun draw(drawsData: DrawsData, name: String): Flow<Boolean> = callbackFlow {
        realtimeDB
            .getReference("users")
            .child(name)
            .child("draws")
            .child(UUID.randomUUID().toString())
            .setValue(drawsData.value)
            .addOnSuccessListener {
                trySend(true)
            }
            .addOnFailureListener {
                trySend(false)
            }
        awaitClose()
    }
}