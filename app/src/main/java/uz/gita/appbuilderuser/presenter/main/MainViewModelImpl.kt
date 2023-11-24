package uz.gita.appbuilderuser.presenter.main

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
import uz.gita.appbuilderuser.data.model.InputModel
import uz.gita.appbuilderuser.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val repository: AppRepository,
    private val direction: MainContract.Direction
) : ViewModel(),
    MainContract.MainViewModel {
    override val uiState = MutableStateFlow(MainContract.UiState())
    private var name = ""
    private val list = arrayListOf<InputModel>()

    fun reduce(block : (MainContract.UiState) -> MainContract.UiState) {
        val oldValue = uiState.value
        uiState.value = block(oldValue)
    }

    override fun onEventDispatcher(intent: MainContract.Intent) {
        when (intent) {
            is MainContract.Intent.ClickDrawButton -> {
                // keyingi screenga otish
                viewModelScope.launch {
                    direction.moveToDraw(name)
                }
            }

            is MainContract.Intent.SetValue -> {
                val newList = ArrayList<InputModel>()

                list.forEach {
                    if (it.id == intent.id) {
                        newList.add(InputModel(intent.value , it.id ))
                    }else {
                        newList.add(it)
                    }
                }
                reduce { it.copy(inputList = newList) }
            }

            is MainContract.Intent.Load -> {
                name = intent.name
                repository.getAllData(intent.name)
                    .onStart { uiState.update { it.copy(loader = true) } }
                    .onEach { data ->
                        uiState.update { it.copy(loader = false) }
                        uiState.update { it.copy(components = data) }
                        data.forEach {
                            if (it.componentsName == "Input") {

                                list.add(InputModel("" , it.id))
                            }
                        }
                        reduce { it.copy(inputList = list) }
                    }.launchIn(viewModelScope)
            }

            MainContract.Intent.Logout -> {
                Log.d("SSS", "Log out")
                repository.setLogin(false)
                repository.setUserName("")
                viewModelScope.launch { direction.back() }
            }
        }
    }
}