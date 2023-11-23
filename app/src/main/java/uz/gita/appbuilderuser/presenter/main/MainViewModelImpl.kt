package uz.gita.appbuilderuser.presenter.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import uz.gita.appbuilderuser.domain.repository.AppRepository
import javax.inject.Inject

class MainViewModelImpl @Inject constructor(private val  repository: AppRepository):ViewModel(),MainContract.MainViewModel {
    override val uiState = MutableStateFlow(MainContract.UiState())

    override fun onEventDispatcher(intent: MainContract.Intent) {
        when(intent){
            is MainContract.Intent.Load->{
               repository.getAllData(intent.name).onEach {data->
                   uiState.update { it.copy(components = data) }
               }
            }
        }

    }
}