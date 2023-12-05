package uz.gita.appbuilderuser.presenter.userDataScreen

import kotlinx.coroutines.flow.StateFlow
import uz.gita.appbuilderuser.data.model.DrawsData

interface UserDataContract {
    interface UserDataViewModel {
        val uiState: StateFlow<UiState>
        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val data: List<DrawsData> = listOf(),
        val isLoading: Boolean = false,
        val name: String = ""
    )

    interface Intent {
        object AddDraw : Intent

        data class ClickItem(
            val key: String,
            val state: Boolean
        ) : Intent

        data class DeleteItem(
            val key: String
        ) : Intent

        object Logout : Intent
    }

    interface Direction {
        suspend fun moveToAddDraw()
        suspend fun moveToEditDraw(key: String, state: Boolean)
        suspend fun moveToLogin()
    }
}