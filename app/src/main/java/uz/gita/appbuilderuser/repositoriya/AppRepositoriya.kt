package uz.gita.appbuilderuser.repositoriya

import kotlinx.coroutines.flow.Flow
import uz.gita.appbuilderuser.data.UserData
import uz.gita.appbuilderuser.data.model.ComponentsModel

interface AppRepositoriya {

    suspend fun loginUser(userData: UserData): kotlinx.coroutines.flow.Flow<Boolean>

    fun getAllData(name: String): Flow<List<ComponentsModel>>

}