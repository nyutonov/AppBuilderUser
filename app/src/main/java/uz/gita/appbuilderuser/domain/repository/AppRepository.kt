package uz.gita.appbuilderuser.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.appbuilderuser.data.model.UserData
import uz.gita.appbuilderuser.data.model.ComponentsModel

interface AppRepository {

    suspend fun loginUser(userData: UserData): Flow<Boolean>
    fun getAllData(name: String): Flow<List<ComponentsModel>>
    fun isLogin(): Boolean
    fun setLogin(login: Boolean)
    fun setUserName(name: String)
    fun getUserName(): String
}