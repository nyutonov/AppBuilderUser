package uz.gita.appbuilderuser.repositoriya

import uz.gita.appbuilderuser.data.UserData

interface AppRepositoriya {

    suspend fun loginUser(userData: UserData):kotlinx.coroutines.flow.Flow<Boolean>



}