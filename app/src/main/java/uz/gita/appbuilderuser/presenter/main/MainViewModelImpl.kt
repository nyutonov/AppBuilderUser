package uz.gita.appbuilderuser.presenter.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import uz.gita.appbuilderuser.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(private val repository: AppRepository) : ViewModel(),
    MainContract.MainViewModel {
    override val uiState = MutableStateFlow(MainContract.UiState())

    override fun onEventDispatcher(intent: MainContract.Intent) {
        when (intent) {
            is MainContract.Intent.Load -> {
                repository.getAllData(intent.name)
                    .onStart { uiState.update { it.copy(loader = true) } }
                    .onCompletion { uiState.update { it.copy(loader = false) } }
                    .onEach { data ->
                    Log.d("TTT", "onEventDispatcher:${data.size} ")
                    uiState.update { it.copy(components = data) }
                    Log.d("TTT", "onEventDispatcherName: ${intent.name}")
                }.launchIn(viewModelScope)
            }
        }

    }
}