package uz.gita.testcleanafb6.presentation.navigator

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationDispatcher @Inject constructor( ) : AppNavigator, NavigationHandler {
    override val navigationFlow = MutableSharedFlow<navArgs>()

    private suspend fun navigate(navArgs: navArgs) {
        navigationFlow.emit(navArgs)
    }

    override suspend fun navigateTo(screen: myScreen) = navigate {
        this.push(screen)
    }

    override suspend fun replace(screen: myScreen) = navigate {
        this.replace(screen)
    }

    override suspend fun replaceAll(screen: myScreen) = navigate {
        this.replaceAll(screen)
    }

    override suspend fun back() = navigate {
        this.pop()
    }

    override suspend fun backToRoot(screen: myScreen) = navigate {
        this.popUntil { it == screen }
    }

}