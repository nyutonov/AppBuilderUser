package uz.gita.appbuilderuser.presentation.login

import uz.gita.appbuilderuser.presentation.main.MainScreen
import uz.gita.testcleanafb6.presentation.navigator.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface LoginDirection {
    suspend fun moveToMainScreen()
}

@Singleton
class LoginDirectionImp @Inject constructor(private val appNavigator: AppNavigator) :
    LoginDirection {

    override suspend fun moveToMainScreen() {
        appNavigator.replaceAll(MainScreen())
    }

}