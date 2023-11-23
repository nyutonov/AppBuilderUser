package uz.gita.appbuilderuser.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.appbuilderuser.data.UserData
import uz.gita.appbuilderuser.repositoriya.AppRepositoriya
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val direction: LoginDirection,
    private val appRepositoriya: AppRepositoriya
) : ViewModel(), LoginContract.ViewModel {

    override val uiState = MutableStateFlow(LoginContract.UiState())
    override val sideEffect =
        MutableStateFlow<LoginContract.SideEffect>(LoginContract.SideEffect.Init)

    override fun onEventDispatcher(intent: LoginContract.Intent) {
        when (intent) {

            LoginContract.Intent.Login -> {
                viewModelScope.launch {
                    appRepositoriya.loginUser(UserData(uiState.value.name, uiState.value.password))
                        .onEach {
                            if (it) {
                                direction.moveToMainScreen()
                            }
                        }
                }
            }


            LoginContract.Intent.ClickPasswordEye -> {
                reduce { it.copy(showPassword = !uiState.value.showPassword) }
            }

            is LoginContract.Intent.EnteringName -> {
                reduce { it.copy(name = intent.name) }
            }

            is LoginContract.Intent.EnteringPassword -> {
                reduce { it.copy(password = intent.password) }
            }

        }
    }

    private fun reduce(block: (oldState: LoginContract.UiState) -> LoginContract.UiState) {
        val old = uiState.value
        uiState.value = block(old)
    }
}