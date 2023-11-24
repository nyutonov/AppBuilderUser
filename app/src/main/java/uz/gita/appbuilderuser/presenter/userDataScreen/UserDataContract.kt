package uz.gita.appbuilderuser.presenter.userDataScreen

import kotlinx.coroutines.flow.StateFlow
import uz.gita.appbuilderuser.data.model.DrawsData

interface UserDataContract {
    interface UserDataViewModel {
     val uiState:StateFlow<UiState>
     fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val data: List<DrawsData> = listOf()
    )

    interface Intent {
        data class Load(val name: String) : Intent
        data class AddDraws(
            val name: String
        ):Intent
        data class ClickItem(
            val name: String,
            val drawsData: DrawsData
        ):Intent

    }
}