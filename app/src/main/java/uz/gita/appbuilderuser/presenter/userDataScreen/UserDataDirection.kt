package uz.gita.appbuilderuser.presenter.userDataScreen

import uz.gita.appbuilderuser.navigator.AppNavigator
import uz.gita.appbuilderuser.presenter.add_draft.AddDraftScreen
import uz.gita.appbuilderuser.presenter.edit_draft.EditDraftScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : UserDataContract.Direction {
    override suspend fun moveToAddDraw() {
        appNavigator.navigateTo(AddDraftScreen())
    }

    override suspend fun moveToEditDraw(key: String) {
        appNavigator.navigateTo(EditDraftScreen(key))
    }
}