package uz.gita.appbuilderuser.presenter.main

import kotlinx.coroutines.flow.StateFlow
import uz.gita.appbuilderuser.data.model.ComponentsModel
import uz.gita.appbuilderuser.data.model.DrawsData
import uz.gita.appbuilderuser.data.model.InputModel
import uz.gita.appbuilderuser.data.room.entity.ComponentEntity

interface MainContract {
    interface MainViewModel {
        val uiState: StateFlow<UiState>
        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val components: List<ComponentsModel> = listOf(),
        val loader: Boolean = false ,
        val inputList : List<ComponentEntity> = listOf() ,
        val visibility : Boolean = false
    )

    interface Intent {
        data class Load(val name: String) : Intent
        object ClickDrawButton : Intent
        object Logout : Intent

        data class OnChangeInputValue(
            val id : String ,
            val value : String
        ) : Intent

        data class SetValue(val id : String , val value : String) : Intent


    }

    interface Direction {
        suspend fun back()

        suspend fun moveToDraw(name: String)
    }
}