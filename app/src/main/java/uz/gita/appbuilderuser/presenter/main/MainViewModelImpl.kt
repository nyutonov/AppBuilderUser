package uz.gita.appbuilderuser.presenter.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.appbuilderuser.data.model.InputModel
import uz.gita.appbuilderuser.data.room.entity.ComponentEntity
import uz.gita.appbuilderuser.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val repository: AppRepository,
    private val direction: MainContract.Direction
) : ViewModel(),
    MainContract.MainViewModel {
    override val uiState = MutableStateFlow(MainContract.UiState())
    private var name = ""
    private val list = arrayListOf<InputModel>()
    private var count = 0

    private fun reduce(block: (MainContract.UiState) -> MainContract.UiState) {
        val oldValue = uiState.value
        uiState.value = block(oldValue)
    }

    init {
        repository.getAllComponentValue()
            .onEach { list ->
                reduce {
                    it.copy(
                        inputList = list
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: MainContract.Intent) {
        when (intent) {
            is MainContract.Intent.ClickDrawButton -> {
                viewModelScope.launch {
                    direction.moveToDraw(name)
                }
            }

            is MainContract.Intent.OnChangeInputValue -> {
                repository.updateComponentValue(
                    ComponentEntity(
                        intent.id,
                        intent.value
                    )
                )
            }

            is MainContract.Intent.Load -> {
                name = intent.name
                repository.getAllData(intent.name)
                    .onStart { uiState.update { it.copy(loader = true) } }
                    .onEach { data ->
                        uiState.update { it.copy(components = data.sortedBy { it.componentId }, loader = false) }
                        var count = 0
                        repository.deleteAllComponent()
                        data.forEach {
                            if (it.componentsName == "Input" || it.componentsName == "Selector" || it.componentsName == "Multi Selector") {
                                repository.addComponentValue(ComponentEntity(it.id, ""))
                            }
                        }
                    }.launchIn(viewModelScope)
            }

            MainContract.Intent.Logout -> {
                repository.setLogin(false)
                repository.setUserName("")

                viewModelScope.launch { direction.back() }
            }
        }
    }
}