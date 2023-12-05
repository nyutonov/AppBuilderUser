package uz.gita.appbuilderuser.presenter.add

import kotlinx.coroutines.flow.StateFlow
import uz.gita.appbuilderuser.data.model.ComponentsModel
import uz.gita.appbuilderuser.data.room.entity.ComponentEntity

interface AddContract {
    interface MainViewModel {
        val uiState: StateFlow<UiState>
        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val components: List<ComponentsModel> = listOf(),
        val loader: Boolean = false ,
        val inputList : List<ComponentEntity> = listOf() ,
        val visibility : Boolean = false ,
        val isCheck: Boolean = false
    )

    interface Intent {
        object Load : Intent

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

    interface Direction {
        suspend fun back()
    }
}