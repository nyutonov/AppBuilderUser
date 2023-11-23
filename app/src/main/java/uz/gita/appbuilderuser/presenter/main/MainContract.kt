package uz.gita.appbuilderuser.presenter.main

import kotlinx.coroutines.flow.StateFlow
import uz.gita.appbuilderuser.data.model.ComponentsModel
import uz.gita.appbuilderuser.data.model.DrawsData

interface MainContract {
    interface MainViewModel {
        val uiState: StateFlow<UiState>
        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val components: List<ComponentsModel> = listOf(),
        val loader: Boolean = false,

        val value: String
    )

    interface Intent {
        data class Load(val name: String) : Intent
        data class ClickDrawButton(val drawsData: DrawsData) : Intent
        object Logout : Intent

        data class SetValue(val value: String) : Intent
    }

    interface Direction {
        suspend fun back()
    }
}