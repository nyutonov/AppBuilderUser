package uz.gita.appbuilderuser.presenter.userDataScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.appbuilderuser.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class UserDataViewModelImpl @Inject constructor(
    private val repository: AppRepository,
    private val direction: UserDataContract.Direction
) : ViewModel(),
    UserDataContract.UserDataViewModel {
    override val uiState = MutableStateFlow(UserDataContract.UiState())

    init {
        uiState.update { it.copy(name = repository.getUserName()) }

        repository.getDrawsData(repository.getUserName())
            .onStart { uiState.update { it.copy(isLoading = true) } }
            .onEach { list ->
                uiState.update { it.copy(data = list.filter { it.key != "draftId" }.sortedBy { it.id }, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: UserDataContract.Intent) {
        when (intent) {
            is UserDataContract.Intent.AddDraw -> {
                viewModelScope.launch { direction.moveToAddDraw() }
            }

            is UserDataContract.Intent.ClickItem -> {
                viewModelScope.launch { direction.moveToEditDraw(intent.key, intent.state) }
            }

            is UserDataContract.Intent.DeleteItem -> {
                repository.deleteDraft(intent.key)
                    .onEach {  }
                    .launchIn(viewModelScope)
            }

            UserDataContract.Intent.Logout -> {
                repository.setLogin(false)
                repository.setUserName("")

                viewModelScope.launch { direction.moveToLogin() }
            }
        }
    }
}