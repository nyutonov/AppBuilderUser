package uz.gita.appbuilderuser.presenter.login

import uz.gita.appbuilderuser.navigator.AppNavigator
import uz.gita.appbuilderuser.presenter.main.MainScreen
import uz.gita.appbuilderuser.presenter.userDataScreen.UserDataScreen
import javax.inject.Inject
import javax.inject.Singleton

interface LoginDirection {
    suspend fun moveToMainScreen(name: String)
}

@Singleton
class LoginDirectionImp @Inject constructor(private val appNavigator: AppNavigator) :
    LoginDirection {

    override suspend fun moveToMainScreen(name: String) {
        appNavigator.replace(UserDataScreen(name))
    }

}