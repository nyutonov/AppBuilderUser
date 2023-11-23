package uz.gita.appbuilderuser.presenter.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.appbuilderuser.presenter.components.DateComponent
import uz.gita.appbuilderuser.presenter.components.InputComponent
import uz.gita.appbuilderuser.presenter.components.MultiSelectorComponent
import uz.gita.appbuilderuser.presenter.components.SampleSpinner
import uz.gita.appbuilderuser.presenter.components.TextComponent

class MainScreen (private val name:String): AndroidScreen() {

    @Composable
    override fun Content() {
        val vm:MainContract.MainViewModel=getViewModel<MainViewModelImpl>()
        vm.onEventDispatcher(MainContract.Intent.Load(name))
        MainContent(uiState = vm.uiState.collectAsState(), onEventDispatcher = vm::onEventDispatcher)
    }

    @Composable
    private fun MainContent(
        uiState: State<MainContract.UiState>,
        onEventDispatcher: (MainContract.Intent) -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn{
                items(uiState.value.components){
                    when (it.componentsName) {
                        "Text" -> {
                            TextComponent(data = it)
                        }
                        "Input" -> {
                            InputComponent(it)
                        }
                        "Selector" -> {
                            SampleSpinner(it)
                        }
                        "MultiSelector" -> {
                            MultiSelectorComponent(list = it.multiSelectorDataAnswers)
                        }
                        "Date Picker" -> {
                            DateComponent()
                        }
                    }
                }
            }
        }
    }
}