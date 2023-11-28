package uz.gita.appbuilderuser.presenter.add_draft

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.appbuilderuser.data.model.DrawsData
import uz.gita.appbuilderuser.domain.repository.AppRepository
import uz.gita.appbuilderuser.presenter.edit_draft.EditDraftContract
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddDraftViewModel @Inject constructor(
    private val repository: AppRepository,
    private val direction: AddDraftContract.Direction
) : AddDraftContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(AddDraftContract.UIState())

    init {
        repository.getAllData(repository.getUserName())
            .onEach { components -> uiState.update { it.copy(components = components.sortedBy { it.componentId }) } }
            .launchIn(viewModelScope)
    }

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

    override fun onEventDispatcher(intent: AddDraftContract.Intent) {
        when (intent) {
            AddDraftContract.Intent.Draft -> {
                repository
                    .draw(
                        DrawsData(0, UUID.randomUUID().toString(), false, uiState.value.components),
                        repository.getUserName()
                    )
                    .onEach { direction.back() }
                    .launchIn(viewModelScope)
            }

            AddDraftContract.Intent.Submit -> {
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

            is AddDraftContract.Intent.Check -> {
                uiState.update { it.copy(isCheck = intent.check) }
            }

            is AddDraftContract.Intent.ChangeInputValue -> {
                uiState.value.components[intent.index].text = intent.value
            }

            is AddDraftContract.Intent.ChangeSelectorValue -> {
                uiState.value.components[intent.index].preselected = intent.value
            }

            is AddDraftContract.Intent.ChangeDataPicker -> {
                uiState.value.components[intent.index].datePicker = intent.value
            }

            is AddDraftContract.Intent.ChangeMultiSelectorValue -> {
                uiState.value.components[intent.index].preselectedMulti = intent.selected
            }
        }
    }
}