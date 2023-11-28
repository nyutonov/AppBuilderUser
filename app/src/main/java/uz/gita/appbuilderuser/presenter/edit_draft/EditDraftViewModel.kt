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

    override fun onEventDispatcher(intent: EditDraftContract.Intent) {
        when (intent) {
            is EditDraftContract.Intent.LoadData -> {
                repository.getAllDraftComponent(repository.getUserName(), intent.key)
                    .onEach { components ->
                        uiState.update { it.copy(state = intent.state, components = components.sortedBy { it.componentId }) }
                    }
                    .launchIn(viewModelScope)
            }

            EditDraftContract.Intent.Draft -> {
                viewModelScope.launch {
                    repository.draw(
                        DrawsData(0, UUID.randomUUID().toString(), false, uiState.value.components),
                        repository.getUserName()
                    )

                    direction.back()
                }
            }

            EditDraftContract.Intent.Submit -> {
                viewModelScope.launch {
                    repository.draw(
                        DrawsData(0, UUID.randomUUID().toString(), true, uiState.value.components),
                        repository.getUserName()
                    )

                    direction.back()
                }
            }

            is EditDraftContract.Intent.ChangeInputValue -> {
                uiState.value.components[intent.index].value = intent.value
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