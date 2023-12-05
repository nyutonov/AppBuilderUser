package uz.gita.appbuilderuser.presenter.edit_draft

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import uz.gita.appbuilderuser.data.model.DrawsData
import uz.gita.appbuilderuser.data.room.entity.ComponentEntity
import uz.gita.appbuilderuser.domain.repository.AppRepository
import uz.gita.appbuilderuser.presenter.add.AddContract
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val repository: AppRepository,
    private val direction: EditContract.Direction
) : EditContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(EditContract.UIState())

    private fun check(): Boolean {
        uiState.value.components.forEach {
            if (it.isRequired) {
                if (it.text.isEmpty()) return false
            }

            if (it.isMinLengthForTextEnabled) {
                if (it.text.length < it.minLengthForText) return false
            }

            if (it.isMinValueForNumberEnabled) {
                if (it.text.toInt() < it.minValueForNumber) return false
            }
        }

        return true
    }

    init {
        repository.getAllComponentValue()
            .onEach { list -> uiState.update { it.copy(inputList = list) } }
            .launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: EditContract.Intent) {
        when (intent) {
            is EditContract.Intent.Load -> {
                repository.getAllDraftComponent(repository.getUserName(), intent.key)
                    .onStart { uiState.update { it.copy(loader = true) } }
                    .onEach { data ->
                        uiState.update {
                            it.copy(
                                state = intent.state,
                                key = intent.key,
                                components = data.sortedBy { it.componentId },
                                loader = false
                            )
                        }

                        repository.deleteAllComponent()

                        data.forEach {
                            if (it.componentsName == "Input" || it.componentsName == "Selector" || it.componentsName == "Multi Selector") {
                                repository.addComponentValue(ComponentEntity(it.id, ""))
                            }
                        }
                    }
                    .launchIn(viewModelScope)
            }

            EditContract.Intent.Draft -> {
                repository
                    .updateDraw(
                        DrawsData(0, uiState.value.key, false, uiState.value.components),
                        repository.getUserName()
                    )
                    .onEach { direction.back() }
                    .launchIn(viewModelScope)
            }

            EditContract.Intent.Submit -> {
                if (check()) {
                    repository
                        .updateDraw(
                            DrawsData(0, uiState.value.key, true, uiState.value.components),
                            repository.getUserName()
                        )
                        .onEach { direction.back() }
                        .launchIn(viewModelScope)
                } else {
                    uiState.update { it.copy(isCheck = true) }
                }
            }

            is EditContract.Intent.OnChangeInputValue -> {
                repository.updateComponentValue(
                    ComponentEntity(
                        intent.id,
                        intent.value
                    )
                )
            }

            is EditContract.Intent.Check -> {
                uiState.update { it.copy(isCheck = intent.check) }
            }

            is EditContract.Intent.ChangeInputValue -> {
                uiState.value.components[intent.index].text = intent.value
            }

            is EditContract.Intent.ChangeSelectorValue -> {
                uiState.value.components[intent.index].preselected = intent.value
            }

            is EditContract.Intent.ChangeDataPicker -> {
                uiState.value.components[intent.index].datePicker = intent.value
            }

            is EditContract.Intent.ChangeMultiSelectorValue -> {
                uiState.value.components[intent.index].preselectedMulti = intent.selected
            }
        }
    }
}