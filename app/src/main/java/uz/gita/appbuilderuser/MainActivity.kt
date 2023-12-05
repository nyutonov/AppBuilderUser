package uz.gita.appbuilderuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import uz.gita.appbuilderuser.domain.repository.AppRepository
import uz.gita.appbuilderuser.navigator.NavigationHandler
import uz.gita.appbuilderuser.presenter.login.LoginScreen
import uz.gita.appbuilderuser.presenter.userDataScreen.UserDataScreen
import uz.gita.appbuilderuser.ui.theme.AppBuilderUserTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationHandler: NavigationHandler
    @Inject
    lateinit var repository: AppRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBuilderUserTheme {
                Navigator(screen = if (repository.isLogin()) UserDataScreen() else LoginScreen()) { navigator ->
                    LaunchedEffect(key1 = navigator) {
                        navigationHandler.navigationFlow
                            .onEach {
                                it(navigator)
                            }.collect()
                    }
                    CurrentScreen()
                }
            }
        }
    }
}