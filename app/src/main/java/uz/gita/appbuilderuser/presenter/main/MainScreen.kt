package uz.gita.appbuilderuser.presenter.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.appbuilderuser.presenter.components.DateComponent
import uz.gita.appbuilderuser.presenter.components.InputComponent
import uz.gita.appbuilderuser.presenter.components.MultiSelectorComponent
import uz.gita.appbuilderuser.presenter.components.SampleSpinner
import uz.gita.appbuilderuser.presenter.components.TextComponent

class MainScreen(private val name: String) : AndroidScreen() {

    @Composable
    override fun Content() {
        val vm: MainContract.MainViewModel = getViewModel<MainViewModelImpl>()
        vm.onEventDispatcher(MainContract.Intent.Load(name))
        MainContent(
            uiState = vm.uiState.collectAsState(),
            name,
            onEventDispatcher = vm::onEventDispatcher
        )
    }

    @Composable
    private fun MainContent(
        uiState: State<MainContract.UiState>,
        name: String,
        onEventDispatcher: (MainContract.Intent) -> Unit,
    ) {
        var loaderText by remember {
            mutableStateOf(false)
        }
        loaderText = uiState.value.components.isEmpty()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F1C2E))
        ) {
            if (uiState.value.loader) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            if (!(uiState.value.loader) && loaderText) {
                Text(text = "Empty", fontSize = 18.sp, modifier = Modifier.align(Alignment.Center))
            }
            Column(modifier = Modifier

                .background(Color(0xFF0F1C2E))) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 15.dp)
                        .background(Color(0xff4d648d))
                ) {
                    Text(
                        text = "Home Screen ", fontSize = 28.sp, modifier = Modifier.align(
                            Alignment.CenterStart,
                        ),
                        fontFamily = FontFamily.Default,
                        color = Color.White
                    )
                    Text(
                        text = name, fontSize = 23.sp, modifier = Modifier.align(
                            Alignment.CenterEnd
                        ),
                        color = Color.White
                    )
                }
                    LazyColumn {
                        items(uiState.value.components) {

                            when (it.componentsName) {
                                "Text" -> {
                                    TextComponent(it)
                                    Log.d("TTT", "MainContent: $it")
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
    }
