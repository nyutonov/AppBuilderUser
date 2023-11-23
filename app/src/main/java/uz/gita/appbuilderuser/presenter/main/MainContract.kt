package uz.gita.appbuilderuser.presenter.main

import kotlinx.coroutines.flow.StateFlow
import uz.gita.appbuilderuser.data.model.ComponentsModel

interface MainContract {
    interface MainViewModel {
        val uiState: StateFlow<UiState>
        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val components: List<ComponentsModel> = listOf()
    )

    interface Intent {
        data class Load(val name: String) : Intent

    }
}