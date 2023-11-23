package uz.gita.appbuilderuser.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.appbuilderuser.domain.param.UserParam

interface Repository {
    fun getAllUsers() : Flow<List<UserParam>>
}