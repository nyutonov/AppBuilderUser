package uz.gita.appbuilderuser.repositoriya.impl

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
                    it.forEach {
                        if (it.name == userData.name && it.password == userData.password) {
                            trySend(true)
                        }
                    }
                }
                it.onFailure {
                    trySend(false)
                }
            }
            .launchIn(scope)
    }
}