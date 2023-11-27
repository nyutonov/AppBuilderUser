package uz.gita.appbuilderuser.presenter.add_draft

import android.util.Log
import uz.gita.appbuilderuser.navigator.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddDraftDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : AddDraftContract.Direction {
    override suspend fun back() {
        Log.d("TTT", "back: ")

        appNavigator.back()
    }
}