package uz.gita.appbuilderuser.presenter.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.appbuilderuser.data.model.DrawsData
import uz.gita.appbuilderuser.data.model.InputModel
import uz.gita.appbuilderuser.data.room.entity.ComponentEntity
import uz.gita.appbuilderuser.domain.repository.AppRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddViewModelImpl @Inject constructor(
    private val repository: AppRepository,
    private val direction: AddContract.Direction
) : ViewModel(),
    AddContract.MainViewModel {
    override val uiState = MutableStateFlow(AddContract.UiState())
    private var name = ""

    private fun reduce(block: (AddContract.UiState) -> AddContract.UiState) {
        val oldValue = uiState.value
        uiState.value = block(oldValue)
    }

    init {
        repository.getAllComponentValue()
            .onEach { list -> reduce { it.copy(inputList = list) } }
            .launchIn(viewModelScope)
    }

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


    override fun onEventDispatcher(intent: AddContract.Intent) {
        when (intent) {
            AddContract.Intent.Draft -> {
                repository
                    .draw(
                        DrawsData(0, UUID.randomUUID().toString(), false, uiState.value.components),
                        repository.getUserName()
                    )
                    .onEach { direction.back() }
                    .launchIn(viewModelScope)
            }

            AddContract.Intent.Submit -> {
                if (check()) {
                    repository
                        .draw(
                            DrawsData(0, UUID.randomUUID().toString(), true, uiState.value.components),
                            repository.getUserName()
                        )
                        .onEach { direction.back() }
                        .launchIn(viewModelScope)
                } else {
                    uiState.update { it.copy(isCheck = true) }
                }
            }

            is AddContract.Intent.Check -> {
                uiState.update { it.copy(isCheck = intent.check) }
            }

            is AddContract.Intent.ChangeInputValue -> {
                uiState.value.components[intent.index].text = intent.value
            }

            is AddContract.Intent.ChangeSelectorValue -> {
                uiState.value.components[intent.index].preselected = intent.value
            }

            is AddContract.Intent.ChangeDataPicker -> {
                uiState.value.components[intent.index].datePicker = intent.value
            }

            is AddContract.Intent.ChangeMultiSelectorValue -> {
                uiState.value.components[intent.index].preselectedMulti = intent.selected
            }

            is AddContract.Intent.OnChangeInputValue -> {
                repository.updateComponentValue(
                    ComponentEntity(
                        intent.id,
                        intent.value
                    )
                )
            }

            is AddContract.Intent.Load -> {
                repository.getAllData(repository.getUserName())
                    .onStart { uiState.update { it.copy(loader = true) } }
                    .onEach { data ->
                        uiState.update {
                            it.copy(
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
                    }.launchIn(viewModelScope)
            }
        }
    }
}