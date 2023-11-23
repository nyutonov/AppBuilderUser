package uz.gita.appbuilderuser.presenter.userDataScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import uz.gita.appbuilderuser.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class UserDataViewModelImpl @Inject constructor(val repository: AppRepository):ViewModel(),UserDataContract.UserDataViewModel {
    override val uiState = MutableStateFlow(UserDataContract.UiState())

    override fun onEventDispatcher(intent: UserDataContract.Intent) {
       when(intent){
          is UserDataContract.Intent.Load->{
               repository.getDrawsData(intent.name).onEach {list->
                   uiState.update { it.copy(data = list ) }
               }.launchIn(viewModelScope)
           }
           is UserDataContract.Intent.MoveToUserUiScreen->{

           }
       }
    }
}