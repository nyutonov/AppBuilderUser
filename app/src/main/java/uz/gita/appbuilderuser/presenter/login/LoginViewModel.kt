package uz.gita.appbuilderuser.presenter.login

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.appbuilderuser.app.App
import uz.gita.appbuilderuser.data.model.UserData
import uz.gita.appbuilderuser.domain.repository.AppRepository
import uz.gita.appbuilderuser.domain.repository.impl.AppRepositoryImpl
import uz.gita.appbuilderuser.utils.myToast
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @SuppressLint("StaticFieldLeak")
@Inject constructor(
    private val direction: LoginDirection,
    private val appRepository: AppRepository,
) : ViewModel(), LoginContract.ViewModel {



    override val uiState = MutableStateFlow(LoginContract.UiState())
    override val sideEffect =
        MutableStateFlow<LoginContract.SideEffect>(LoginContract.SideEffect.Init)


    override fun onEventDispatcher(intent: LoginContract.Intent) {
        when (intent) {
            LoginContract.Intent.Login -> {
                reduce { it.copy(progressBar = true) }
                viewModelScope.launch(Dispatchers.IO) {
                    appRepository.loginUser(UserData(uiState.value.name.trim(), uiState.value.password.trim()))
                        .onEach {
                            if (uiState.value.name.isNotEmpty()) {
                                if (uiState.value.password.isNotEmpty()) {
                                    if (it) {
                                        appRepository.setLogin(true)
                                        appRepository.setUserName(uiState.value.name.trim())
                                        myToast("Success")
                                        reduce { it.copy(progressBar = false) }
                                        direction.moveToMainScreen()
                                    } else {
                                        myToast("bunday user mavjud emas!")
                                        reduce { it.copy(progressBar = false) }
                                    }
                                } else {
                                    reduce { it.copy(progressBar = false) }
                                    myToast("password kiritilmadi iltimos password ni kiriting!")
                                }
                            } else {
                                myToast("name kiritilmadi iltimos name ni kiriting!")
                                reduce { it.copy(progressBar = false) }
                            }
                        }.launchIn(viewModelScope)
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