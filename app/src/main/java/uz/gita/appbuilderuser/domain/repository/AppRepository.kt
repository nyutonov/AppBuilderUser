package uz.gita.appbuilderuser.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.appbuilderuser.data.model.UserData
import uz.gita.appbuilderuser.data.model.ComponentsModel
import uz.gita.appbuilderuser.data.model.DrawsData
import uz.gita.appbuilderuser.data.room.entity.ComponentEntity

interface AppRepository {

    suspend fun loginUser(userData: UserData): Flow<Boolean>
    fun getAllData(name: String): Flow<List<ComponentsModel>>
    fun isLogin(): Boolean
    fun setLogin(login: Boolean)
    fun setUserName(name: String)
    fun getUserName(): String

    fun getDrawsData(userName: String): Flow<List<DrawsData>>
    fun draw(drawsData: DrawsData, name: String): Flow<Boolean>
    fun updateDraw(drawsData: DrawsData, name: String): Flow<Boolean>
    fun getAllDraftComponent(userName: String, key: String): Flow<List<ComponentsModel>>
    fun deleteDraft(key: String): Flow<Boolean>

    fun addComponentValue(componentEntity: ComponentEntity)
    fun updateComponentValue(componentEntity: ComponentEntity)
    fun getAllComponentValue(): Flow<List<ComponentEntity>>
    fun deleteAllComponent()
    fun deleteById(id : String)
}