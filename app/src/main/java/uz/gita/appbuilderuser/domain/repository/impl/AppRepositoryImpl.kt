package uz.gita.appbuilderuser.domain.repository.impl

import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.appbuilderuser.app.App
import uz.gita.appbuilderuser.data.model.ComponentsModel
import uz.gita.appbuilderuser.data.model.DrawsData
import uz.gita.appbuilderuser.data.model.UserData
import uz.gita.appbuilderuser.data.room.dao.ComponentDao
import uz.gita.appbuilderuser.data.room.entity.ComponentEntity
import uz.gita.appbuilderuser.domain.repository.AppRepository
import uz.gita.appbuilderuser.utils.getAll
import uz.gita.appbuilderuser.utils.toDrawsData
import uz.gita.appbuilderuser.utils.toUserData
import java.util.UUID
import javax.inject.Inject


class AppRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val realtimeDB: FirebaseDatabase,
    private val componentDao: ComponentDao
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
                    trySend(snapshot.children.map {
                        it.toUserData() })
                }

                override fun onCancelled(error: DatabaseError) {
                    //...
                }

            })
        awaitClose()
    }

    override fun getDrawsData(userName: String): Flow<List<DrawsData>> = callbackFlow {
        realtimeDB.getReference("users").child(userName).child("draws")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(snapshot.children.map { it.toDrawsData() })
                }

                override fun onCancelled(error: DatabaseError) {

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
            .child(drawsData.key)
            .run {
                this.child("key").setValue(drawsData.key)
                this.child("state").setValue(drawsData.key)
                this.child("components").run {
                    drawsData.components.forEach {
                        this.child(UUID.randomUUID().toString()).run {
                            this.child("componentsName").setValue(it.componentsName)

                            this.child("input").setValue(it.input)
                            this.child("type").setValue(it.type)
                            this.child("placeHolder").setValue(it.placeHolder)

                            this.child("text").setValue(it.text)
                            this.child("color").setValue(it.color)

                            this.child("selectorDataQuestion").setValue(it.selectorDataQuestion)
                            this.child("selectorDataAnswers")
                                .setValue(it.selectorDataAnswers.joinToString(":"))

                            this.child("multiSelectDataQuestion")
                                .setValue(it.multiSelectDataQuestion)
                            this.child("multiSelectorDataAnswers")
                                .setValue(it.multiSelectorDataAnswers.joinToString(":"))

                            this.child("datePicker").setValue(it.datePicker)
                            this.child("id").setValue(it.id)
                        }
                    }
                }
            }
        awaitClose()
    }

    override fun addComponentValue(componentEntity: ComponentEntity) {
        scope.launch {
            componentDao.addComponent(componentEntity)
        }
    }

    override fun updateComponentValue(componentEntity: ComponentEntity) {
        scope.launch {
            componentDao.addComponent(componentEntity)
        }
    }

    override fun getAllComponentValue(): Flow<List<ComponentEntity>> = componentDao.getAllComponents()
    override fun deleteAllComponent() {
        scope.launch {
            componentDao.deleteAllComponents()
        }
    }
}