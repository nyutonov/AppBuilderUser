package uz.gita.appbuilderuser.repositoriya.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.appbuilderuser.data.UserData
import uz.gita.appbuilderuser.repositoriya.AppRepositoriya
import uz.gita.appbuilderuser.utilse.getAll
import javax.inject.Inject


class AppRepositoriyaImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : AppRepositoriya {


    private val scope = CoroutineScope(Dispatchers.IO + Job())
    override suspend fun loginUser(userData: UserData): Flow<Boolean> = callbackFlow {
        firestore.collection("users")
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
                        }else{
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
}