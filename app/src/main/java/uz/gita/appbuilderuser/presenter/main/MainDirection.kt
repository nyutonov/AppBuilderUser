package uz.gita.appbuilderuser.presenter.main

import uz.gita.appbuilderuser.navigator.AppNavigator
import uz.gita.appbuilderuser.presenter.login.LoginScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : MainContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }
}