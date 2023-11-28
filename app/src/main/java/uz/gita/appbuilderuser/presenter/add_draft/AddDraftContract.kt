package uz.gita.appbuilderuser.presenter.add_draft

import kotlinx.coroutines.flow.StateFlow
import uz.gita.appbuilderuser.data.model.ComponentsModel
import uz.gita.appbuilderuser.presenter.edit_draft.EditDraftContract

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

        data class Check(
            val check: Boolean
        ) : Intent
    }

    data class UIState(
        val components: List<ComponentsModel> = listOf(),
        val isCheck: Boolean = false
    )

    interface Direction {
        suspend fun back()
    }
}