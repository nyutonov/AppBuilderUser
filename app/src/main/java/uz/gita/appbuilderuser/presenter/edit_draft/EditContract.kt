package uz.gita.appbuilderuser.presenter.edit_draft

import kotlinx.coroutines.flow.StateFlow
import uz.gita.appbuilderuser.data.model.ComponentsModel
import uz.gita.appbuilderuser.data.room.entity.ComponentEntity
import uz.gita.appbuilderuser.presenter.add.AddContract

interface EditContract {
    interface ViewModel {
        val uiState: StateFlow<UIState>

        fun onEventDispatcher(intent: Intent)
    }

    interface Intent {
        data class Load(
            val key: String,
            val state: Boolean
        ) : Intent

        data class OnChangeInputValue(
            val id : String ,
            val value : String
        ) : Intent

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
        val state: Boolean = false,
        val key: String = "",
        val isCheck: Boolean = false,
        val loader: Boolean = false,
        val inputList : List<ComponentEntity> = listOf(),
        val visibility : Boolean = false,
    )

    interface Direction {
        suspend fun back()
    }
}