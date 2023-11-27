package uz.gita.appbuilderuser.presenter.edit_draft

import uz.gita.appbuilderuser.navigator.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditDraftDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : EditDraftContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }
}