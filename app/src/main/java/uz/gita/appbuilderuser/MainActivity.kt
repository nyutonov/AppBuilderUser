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
import uz.gita.appbuilderuser.ui.theme.AppBuilderUserTheme
import uz.gita.testcleanafb6.presentation.navigator.NavigationHandler
import uz.gita.appbuilderuser.presentation.login.LoginScreen
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationHandler: NavigationHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBuilderUserTheme {
                Navigator(screen = LoginScreen()) { navigator ->
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
