package uz.gita.appbuilderuser.presenter.main

import uz.gita.appbuilderuser.navigator.AppNavigator
import uz.gita.appbuilderuser.presenter.login.LoginScreen
import uz.gita.appbuilderuser.presenter.userDataScreen.UserDataScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : MainContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }

    override suspend fun moveToDraw(name: String) {
        appNavigator.navigateTo(UserDataScreen(name))
    }

    override suspend fun moveToLogin() {
        appNavigator.replace(LoginScreen())
    }
}