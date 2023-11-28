package uz.gita.appbuilderuser.presenter.edit_draft

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.appbuilderuser.data.model.DrawsData
import uz.gita.appbuilderuser.domain.repository.AppRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditDraftViewModel @Inject constructor(
    private val repository: AppRepository,
    private val direction: EditDraftContract.Direction
) : EditDraftContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(EditDraftContract.UIState())

    private fun check() : Boolean {
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

    override fun onEventDispatcher(intent: EditDraftContract.Intent) {
        when (intent) {
            is EditDraftContract.Intent.LoadData -> {
                repository.getAllDraftComponent(repository.getUserName(), intent.key)
                    .onEach { components ->
                        uiState.update { it.copy(state = intent.state, key = intent.key, components = components.sortedBy { it.componentId }) }
                    }
                    .launchIn(viewModelScope)
            }

            EditDraftContract.Intent.Draft -> {
                repository
                    .updateDraw(
                        DrawsData(0, uiState.value.key, false, uiState.value.components),
                        repository.getUserName()
                    )
                    .onEach { direction.back() }
                    .launchIn(viewModelScope)
            }

            EditDraftContract.Intent.Submit -> {
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

            is EditDraftContract.Intent.Check -> {
                uiState.update { it.copy(isCheck = intent.check) }
            }

            is EditDraftContract.Intent.ChangeInputValue -> {
                uiState.value.components[intent.index].text = intent.value
            }

            is EditDraftContract.Intent.ChangeSelectorValue -> {
                uiState.value.components[intent.index].preselected = intent.value
            }

            is EditDraftContract.Intent.ChangeDataPicker -> {
                uiState.value.components[intent.index].datePicker = intent.value
            }
        }
    }
}