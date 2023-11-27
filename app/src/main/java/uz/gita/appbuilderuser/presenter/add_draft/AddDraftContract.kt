package uz.gita.appbuilderuser.presenter.add_draft

import kotlinx.coroutines.flow.StateFlow
import uz.gita.appbuilderuser.data.model.ComponentsModel

interface AddDraftContract {
    interface ViewModel {
        val uiState: StateFlow<UIState>

        fun onEventDispatcher(intent: Intent)
    }

    interface Intent {
        object Submit : Intent

        object Draft : Intent

        data class ChangeInputValue(
            val value: String,
            val index: Int
        ) : Intent

        data class ChangeSelectorValue(
            val value: String,
            val index: Int
        ) : Intent

        data class ChangeDataPicker(
            val value: String,
            val index: Int
        ) : Intent

        data class ChangeMultiSelectorValue(
            val selected: List<String>,
            val index: Int
        ) : Intent
    }

    data class UIState(
        val components: List<ComponentsModel> = listOf()
    )

    interface Direction {
        suspend fun back()
    }
}