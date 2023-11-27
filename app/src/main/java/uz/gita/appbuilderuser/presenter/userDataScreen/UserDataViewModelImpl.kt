package uz.gita.appbuilderuser.presenter.userDataScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    override fun onEventDispatcher(intent: UserDataContract.Intent) {
        when (intent) {
            is UserDataContract.Intent.Load -> {
                repository.getDrawsData(intent.name)
                    .onEach { list ->
                        uiState.update {
                            it.copy(data = list.filter { it.key != "draftId" }.sortedBy { it.id })
                        }
                    }
                    .launchIn(viewModelScope)
            }

            is UserDataContract.Intent.AddDraws -> {
                viewModelScope.launch { direction.moveToAddDraw() }
            }

            is UserDataContract.Intent.ClickItem -> {
                viewModelScope.launch { direction.moveToEditDraw(intent.key) }
            }
        }
    }
}