package uz.gita.appbuilderuser.presenter.add

import uz.gita.appbuilderuser.navigator.AppNavigator
import uz.gita.appbuilderuser.presenter.login.LoginScreen
import uz.gita.appbuilderuser.presenter.userDataScreen.UserDataScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : AddContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }
}