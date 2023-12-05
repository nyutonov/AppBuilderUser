package uz.gita.appbuilderuser.presenter.userDataScreen

import uz.gita.appbuilderuser.navigator.AppNavigator
import uz.gita.appbuilderuser.presenter.edit_draft.EditScreen
import uz.gita.appbuilderuser.presenter.add.AddScreen
import uz.gita.appbuilderuser.presenter.login.LoginScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : UserDataContract.Direction {
    override suspend fun moveToAddDraw() {
        appNavigator.navigateTo(AddScreen())
    }

    override suspend fun moveToEditDraw(key: String, state: Boolean) {
        appNavigator.navigateTo(EditScreen(key, state))
    }

    override suspend fun moveToLogin() {
        appNavigator.replace(LoginScreen())
    }
}