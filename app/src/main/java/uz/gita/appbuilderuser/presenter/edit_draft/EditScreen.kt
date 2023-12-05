package uz.gita.appbuilderuser.presenter.edit_draft

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.appbuilderuser.presenter.add.AddContract
import uz.gita.appbuilderuser.presenter.add.AddViewModelImpl
import uz.gita.appbuilderuser.presenter.components.DateComponent
import uz.gita.appbuilderuser.presenter.components.ImageComponent
import uz.gita.appbuilderuser.presenter.components.InputComponent
import uz.gita.appbuilderuser.presenter.components.MultiSelectorComponent
import uz.gita.appbuilderuser.presenter.components.RowComponent
import uz.gita.appbuilderuser.presenter.components.SampleSpinner
import uz.gita.appbuilderuser.presenter.components.TextComponent
import uz.gita.appbuilderuser.ui.theme.AppBuilderUserTheme

class EditScreen(val key_: String, val state: Boolean) : AndroidScreen() {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val viewModel: EditContract.ViewModel = getViewModel<EditViewModel>()

        viewModel.onEventDispatcher(EditContract.Intent.Load(key_, state))

        AppBuilderUserTheme {
            MainContent(
                viewModel.uiState.collectAsState(),
                viewModel::onEventDispatcher
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MainContent(
    uiState: State<EditContract.UIState>,
    onEventDispatcher: (EditContract.Intent) -> Unit
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

        Column(
            modifier = Modifier
                .background(Color(0xFF0F1C2E))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color(0XFF1f2b3e))
                    .padding(horizontal = 15.dp)
            )

            Spacer(modifier = Modifier.size(5.dp))

            LazyColumn {
                itemsIndexed(uiState.value.components) { index, it ->
                    when (it.componentsName) {
                        "Text" -> {
                            if (!it.visibility) {
                                TextComponent(it)
                            } else {
                                var visibility1 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility2 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility3 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility4 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility5 by remember {
                                    mutableStateOf(true)
                                }

                                var name by remember {
                                    mutableStateOf("")
                                }
                                it.list.forEach { module ->
                                    if (module.componentName == "in") {
                                        name = ""
                                        var value = ""
                                        uiState.value.inputList.forEach { input ->
                                            if (module.componentId == input.id) {
                                                value = input.value
                                            }
                                            if (module.inMultiSelectorValue == value) {
                                                TextComponent(it)
                                            }
                                        }
                                    } else if (module.componentName == "!in") {
                                        name = ""
                                        var value = ""
                                        uiState.value.inputList.forEach { input ->
                                            if (module.componentId == input.id) {
                                                value = input.value
                                            }
                                            module.list.forEach { data ->
                                                if (value == data) {
                                                    TextComponent(it)
                                                }
                                            }
                                        }
                                    } else if (module.componentName == "Input") {
                                        name = "Input"
                                        uiState.value.components.forEach { data ->
                                            if (module.operator == "==" || module.operator == "=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility1 =
                                                                (module.value == input.value)
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == "<=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility2 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() >= module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == ">=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility3 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() <= module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == "<") {

                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility4 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() < module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == ">") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility5 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() > module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    } else if (module.componentName == "Selector") {
                                        name = ""

                                        if (module.operator == "==") {
                                            var count = 2
                                            uiState.value.components.forEach { data ->
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            if (module.value == input.value) {
                                                                if (count % 2 == 0) {
                                                                    TextComponent(data = it)
                                                                    count++
                                                                }
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        } else if (module.operator == "!=") {
                                            var count = 2
                                            uiState.value.components.forEach { data ->
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            if (module.value != input.value) {
                                                                if (count % 2 == 0) {
                                                                    TextComponent(data = it)
                                                                    count++
                                                                }
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        } else if (module.componentName == "Multi Selector") {
                                            name = ""
                                            if (module.operator == "==") {
                                                var count = 2
                                                uiState.value.components.forEach { data ->
                                                    if (module.componentId == data.id) {
                                                        uiState.value.inputList.forEach { input ->
                                                            if (data.id == input.id) {
                                                                if (module.value == input.value) {
                                                                    if (count % 2 == 0) {
                                                                        TextComponent(data = it)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == "!=") {
                                                var count = 2
                                                uiState.value.components.forEach { data ->
                                                    if (module.componentId == data.id) {
                                                        uiState.value.inputList.forEach { input ->
                                                            if (data.id == input.id) {
                                                                if (module.value != input.value) {
                                                                    if (count % 2 == 0) {
                                                                        TextComponent(data = it)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            name = ""
                                        }
                                    }
                                    if (name == "Input" && visibility1 && visibility2 && visibility3 && visibility4 && visibility5) {
                                        TextComponent(it)
                                    }
                                }
                            }
                        }

                        "Input" -> {
                            if (!it.visibility) {

                                InputComponent(
                                    it,
                                    uiState.value.state,
                                    isSubmit = uiState.value.isCheck
                                ) { id, value ->
                                    onEventDispatcher.invoke(
                                        EditContract.Intent.OnChangeInputValue(
                                            id,
                                            value
                                        )
                                    )
                                    onEventDispatcher(
                                        EditContract.Intent.ChangeInputValue(
                                            value,
                                            index
                                        )
                                    )
                                }
                            } else {
                                var visibility1 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility2 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility3 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility4 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility5 by remember {
                                    mutableStateOf(true)
                                }

                                var name by remember {
                                    mutableStateOf("")
                                }

                                it.list.forEach { module ->
                                    if (module.componentName == "in") {
                                        name = ""
                                        var value = ""
                                        uiState.value.inputList.forEach { input ->
                                            if (module.componentId == input.id) {
                                                value = input.value
                                            }
                                            if (module.inMultiSelectorValue == value) {
                                                InputComponent(
                                                    it,
                                                    uiState.value.state,
                                                    isSubmit = uiState.value.isCheck
                                                ) { id, value ->
                                                    onEventDispatcher.invoke(
                                                        EditContract.Intent.OnChangeInputValue(
                                                            id,
                                                            value
                                                        )
                                                    )
                                                    onEventDispatcher(
                                                        EditContract.Intent.ChangeInputValue(
                                                            value,
                                                            index
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    } else if (module.componentName == "!in") {
                                        name = ""
                                        var value = ""
                                        uiState.value.inputList.forEach { input ->
                                            if (module.componentId == input.id) {
                                                value = input.value
                                            }
                                            module.list.forEach { data ->
                                                if (value == data) {
                                                    InputComponent(
                                                        it,
                                                        uiState.value.state,
                                                        isSubmit = uiState.value.isCheck
                                                    ) { id, value ->
                                                        onEventDispatcher.invoke(
                                                            EditContract.Intent.OnChangeInputValue(
                                                                id,
                                                                value
                                                            )
                                                        )
                                                        onEventDispatcher(
                                                            EditContract.Intent.ChangeInputValue(
                                                                value,
                                                                index
                                                            )
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    } else if (module.componentName == "Input") {
                                        name = "Input"
                                        uiState.value.components.forEach { data ->
                                            if (module.operator == "==" || module.operator == "=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility1 =
                                                                (module.value == input.value)
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == ">=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility2 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() >= module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == "<=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility3 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() <= module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == ">") {

                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility4 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() < module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == "<") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility5 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() > module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (module.componentName == "Selector") {
                                        name = ""
                                        uiState.value.components.forEach { data ->
                                            if (module.componentId == data.id) {
                                                uiState.value.inputList.forEach { input ->
                                                    if (data.id == input.id) {
                                                        if (module.value == input.value) {
                                                            InputComponent(
                                                                it,
                                                                uiState.value.state,
                                                                isSubmit = uiState.value.isCheck
                                                            ) { id, value ->
                                                                onEventDispatcher.invoke(
                                                                    EditContract.Intent.OnChangeInputValue(
                                                                        id,
                                                                        value
                                                                    )
                                                                )
                                                                onEventDispatcher(
                                                                    EditContract.Intent.ChangeInputValue(
                                                                        value,
                                                                        index
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (module.componentName == "Multi Selector") {
                                        name = ""
                                        uiState.value.components.forEach { data ->
                                            if (module.componentId == data.id) {
                                                uiState.value.inputList.forEach { input ->
                                                    if (data.id == input.id) {
                                                        if (module.value == input.value) {
                                                            InputComponent(
                                                                it,
                                                                uiState.value.state,
                                                                isSubmit = uiState.value.isCheck
                                                            ) { id, value ->
                                                                onEventDispatcher.invoke(
                                                                    EditContract.Intent.OnChangeInputValue(
                                                                        id,
                                                                        value
                                                                    )
                                                                )
                                                                onEventDispatcher(
                                                                    EditContract.Intent.ChangeInputValue(
                                                                        value,
                                                                        index
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        name = ""
                                    }
                                }
                                if (name == "Input" && visibility1 && visibility2 && visibility3 && visibility4 && visibility5) {
                                    InputComponent(
                                        it,
                                        uiState.value.state,
                                        isSubmit = uiState.value.isCheck
                                    ) { id, value ->
                                        onEventDispatcher.invoke(
                                            EditContract.Intent.OnChangeInputValue(
                                                id,
                                                value
                                            )
                                        )
                                        onEventDispatcher(
                                            EditContract.Intent.ChangeInputValue(
                                                value,
                                                index
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        "Selector" -> {
                            if (!it.visibility) {
                                SampleSpinner(
                                    question = it.selectorDataQuestion,
                                    it,
                                    uiState.value.state
                                ) { id, value ->
                                    onEventDispatcher.invoke(
                                        EditContract.Intent.OnChangeInputValue(
                                            id,
                                            value
                                        )
                                    )
                                    onEventDispatcher(
                                        EditContract.Intent.ChangeSelectorValue(
                                            value,
                                            index
                                        )
                                    )
                                }
                            } else {
                                var visibility1 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility2 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility3 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility4 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility5 by remember {
                                    mutableStateOf(true)
                                }

                                var name by remember {
                                    mutableStateOf("")
                                }
                                it.list.forEach { module ->
                                    if (module.componentName == "in") {
                                        name = ""
                                        var value = ""
                                        uiState.value.inputList.forEach { input ->
                                            if (module.componentId == input.id) {
                                                value = input.value
                                            }
                                            if (module.inMultiSelectorValue == value) {
                                                SampleSpinner(
                                                    question = it.selectorDataQuestion,
                                                    it,
                                                    uiState.value.state
                                                ) { id, value ->
                                                    onEventDispatcher.invoke(
                                                        EditContract.Intent.OnChangeInputValue(
                                                            id,
                                                            value
                                                        )
                                                    )
                                                    onEventDispatcher(
                                                        EditContract.Intent.ChangeSelectorValue(
                                                            value,
                                                            index
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    } else if (module.componentName == "!in") {
                                        name = ""
                                        var value = ""
                                        uiState.value.inputList.forEach { input ->
                                            if (module.componentId == input.id) {
                                                value = input.value
                                            }
                                            module.list.forEach { data ->
                                                if (value == data) {
                                                    SampleSpinner(
                                                        question = it.selectorDataQuestion,
                                                        it,
                                                        uiState.value.state
                                                    ) { id, value ->
                                                        onEventDispatcher.invoke(
                                                            EditContract.Intent.OnChangeInputValue(
                                                                id,
                                                                value
                                                            )
                                                        )
                                                        onEventDispatcher(
                                                            EditContract.Intent.ChangeSelectorValue(
                                                                value,
                                                                index
                                                            )
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    } else if (module.componentName == "Input") {
                                        name = "Input"
                                        uiState.value.components.forEach { data ->
                                            if (module.operator == "==" || module.operator == "=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility1 =
                                                                (module.value == input.value)
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == ">=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility2 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() >= module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == "<=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility3 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() <= module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == ">") {

                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility4 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() < module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == "<") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility5 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() > module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (module.componentName == "Selector") {
                                        name = ""
                                        uiState.value.components.forEach { data ->
                                            if (module.componentId == data.id) {
                                                uiState.value.inputList.forEach { input ->
                                                    if (data.id == input.id) {
                                                        if (module.value == input.value) {
                                                            SampleSpinner(
                                                                question = it.selectorDataQuestion,
                                                                it,
                                                                uiState.value.state
                                                            ) { id, value ->
                                                                onEventDispatcher.invoke(
                                                                    EditContract.Intent.OnChangeInputValue(
                                                                        id,
                                                                        value
                                                                    )
                                                                )
                                                                onEventDispatcher(
                                                                    EditContract.Intent.ChangeSelectorValue(
                                                                        value,
                                                                        index
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (module.componentName == "Multi Selector") {
                                        name = ""
                                        uiState.value.components.forEach { data ->
                                            if (module.componentId == data.id) {
                                                uiState.value.inputList.forEach { input ->
                                                    if (data.id == input.id) {
                                                        if (module.value == input.value) {
                                                            SampleSpinner(
                                                                question = it.selectorDataQuestion,
                                                                it,
                                                                uiState.value.state
                                                            ) { id, value ->
                                                                onEventDispatcher.invoke(
                                                                    EditContract.Intent.OnChangeInputValue(
                                                                        id,
                                                                        value
                                                                    )
                                                                )
                                                                onEventDispatcher(
                                                                    EditContract.Intent.ChangeSelectorValue(
                                                                        value,
                                                                        index
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        name = ""
                                    }
                                }
                                if (name == "Input" && visibility1 && visibility2 && visibility3 && visibility4 && visibility5) {
                                    SampleSpinner(
                                        question = it.selectorDataQuestion,
                                        it,
                                        uiState.value.state
                                    ) { id, value ->
                                        onEventDispatcher.invoke(
                                            EditContract.Intent.OnChangeInputValue(
                                                id,
                                                value
                                            )
                                        )
                                        onEventDispatcher(
                                            EditContract.Intent.ChangeSelectorValue(
                                                value,
                                                index
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        "MultiSelector" -> {
                            if (!it.visibility) {
                                MultiSelectorComponent(
                                    list = it.multiSelectorDataAnswers,
                                    question = it.multiSelectDataQuestion,
                                    isRead = uiState.value.state
                                ) { value ->
//                            true bolsa value ni ozi keladi aks holda ""
                                    onEventDispatcher(
                                        EditContract.Intent.OnChangeInputValue(
                                            it.id,
                                            value
                                        )
                                    )
//                                        onEventDispatcher(
//                                            MainContract.Intent.ChangeMultiSelectorValue(
//                                                 ,
//                                                index
//                                            )
//                                        )
                                }
                            } else {

                                var visibility1 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility2 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility3 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility4 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility5 by remember {
                                    mutableStateOf(true)
                                }

                                var name by remember {
                                    mutableStateOf("")
                                }
                                it.list.forEach { module ->
                                    if (module.componentName == "Input") {
                                        name = "Input"
                                        uiState.value.components.forEach { data ->
                                            if (module.operator == "==" || module.operator == "=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility1 =
                                                                (module.value == input.value)
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == ">=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility2 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() >= module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == "<=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility3 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() <= module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == ">") {

                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility4 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() < module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == "<") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility5 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() > module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (module.componentName == "Selector") {
                                        name = ""
                                        uiState.value.components.forEach { data ->
                                            if (module.componentId == data.id) {
                                                uiState.value.inputList.forEach { input ->
                                                    if (data.id == input.id) {
                                                        if (module.value == input.value) {
                                                            MultiSelectorComponent(
                                                                list = it.multiSelectorDataAnswers,
                                                                question = it.multiSelectDataQuestion,
                                                                isRead = uiState.value.state
                                                            ) { value ->
//                            true bolsa value ni ozi keladi aks holda ""
                                                                onEventDispatcher(
                                                                    EditContract.Intent.OnChangeInputValue(
                                                                        it.id,
                                                                        value
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (module.componentName == "Multi Selector") {
                                        name = ""
                                        uiState.value.components.forEach { data ->
                                            if (module.componentId == data.id) {
                                                uiState.value.inputList.forEach { input ->
                                                    if (data.id == input.id) {
                                                        if (module.value == input.value) {
                                                            MultiSelectorComponent(
                                                                list = it.multiSelectorDataAnswers,
                                                                question = it.multiSelectDataQuestion,
                                                                isRead = uiState.value.state
                                                            ) { value ->
//                            true bolsa value ni ozi keladi aks holda ""
                                                                onEventDispatcher(
                                                                    EditContract.Intent.OnChangeInputValue(
                                                                        it.id,
                                                                        value
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        name = ""
                                    }
                                }
                                if (name == "Input" && visibility1 && visibility2 && visibility3 && visibility4 && visibility5) {
                                    MultiSelectorComponent(
                                        list = it.multiSelectorDataAnswers,
                                        question = it.multiSelectDataQuestion,
                                        isRead = uiState.value.state
                                    ) { value ->
                                        onEventDispatcher(
                                            EditContract.Intent.OnChangeInputValue(
                                                it.id,
                                                value
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        "Date Picker" -> {
                            if (!it.visibility) {
                                DateComponent(
                                    it.datePicker
                                ) { value ->
                                    onEventDispatcher.invoke(
                                        EditContract.Intent.OnChangeInputValue(
                                            it.id,
                                            value
                                        )
                                    )
                                    onEventDispatcher(
                                        EditContract.Intent.ChangeDataPicker(
                                            value,
                                            index
                                        )
                                    )
                                }
                            } else {
                                var visibility1 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility2 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility3 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility4 by remember {
                                    mutableStateOf(true)
                                }
                                var visibility5 by remember {
                                    mutableStateOf(true)
                                }

                                var name by remember {
                                    mutableStateOf("")
                                }
                                it.list.forEach { module ->
                                    if (module.componentName == "Input") {
                                        name = "Input"
                                        uiState.value.components.forEach { data ->
                                            if (module.operator == "==" || module.operator == "=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility1 =
                                                                (module.value == input.value)
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == ">=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility2 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() >= module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())

                                                        }
                                                    }
                                                }
                                            } else if (module.operator == "<=") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility3 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() <= module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == ">") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility4 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() < module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())
                                                        }
                                                    }
                                                }
                                            } else if (module.operator == "<") {
                                                if (module.componentId == data.id) {
                                                    uiState.value.inputList.forEach { input ->
                                                        if (data.id == input.id) {
                                                            visibility5 =
                                                                (input.value.isNotEmpty() && !input.value.contains(
                                                                    """\D""".toRegex()
                                                                ) && input.value.toInt() > module.value.replace(
                                                                    """\D""".toRegex(),
                                                                    ""
                                                                ).toInt())

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (module.componentName == "Selector") {
                                        name = ""
                                        uiState.value.components.forEach { data ->
                                            if (module.componentId == data.id) {
                                                uiState.value.inputList.forEach { input ->
                                                    if (data.id == input.id) {
                                                        if (module.value == input.value) {
                                                            DateComponent() { value ->
                                                                onEventDispatcher(
                                                                    EditContract.Intent.ChangeDataPicker(
                                                                        value,
                                                                        index
                                                                    )
                                                                )
                                                            }

                                                        }
                                                    }

                                                }
                                            }
                                        }

                                    } else if (module.componentName == "Multi Selector") {
                                        name = ""
                                        uiState.value.components.forEach { data ->
                                            if (module.componentId == data.id) {
                                                uiState.value.inputList.forEach { input ->
                                                    if (data.id == input.id) {
                                                        if (module.value == input.value) {
                                                            DateComponent() { value ->
                                                                onEventDispatcher(
                                                                    EditContract.Intent.ChangeDataPicker(
                                                                        value,
                                                                        index
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        name = ""
                                    }

                                    if (name == "Input" && visibility1 && visibility2 && visibility3 && visibility4 && visibility5) {
                                        DateComponent() { value ->
                                            onEventDispatcher(
                                                EditContract.Intent.ChangeDataPicker(
                                                    value,
                                                    index
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        "Image" -> {
                            if (it.isIdInputted) {
                                uiState.value.inputList.forEach { module ->
                                    if (module.id == it.selectedIdForImage) {
                                        ImageComponent(
                                            size = it.selectedImageSize,
                                            uri = module.value,
                                            color = it.color,
                                            height = it.heightImage,
                                            aspectRatio = it.aspectRatio
                                        )
                                    }
                                }
                            } else {
                                ImageComponent(
                                    size = it.selectedImageSize,
                                    uri = it.imageUri,
                                    color = it.color,
                                    height = it.heightImage,
                                    aspectRatio = it.aspectRatio
                                )
                            }
                        }

                        "Row" -> {
                            RowComponent(componentsModel = it.lsRow)
                        }
                    }
                }
            }

            onEventDispatcher.invoke(EditContract.Intent.Check(false))
        }

        if (uiState.value.components.isNotEmpty() && !uiState.value.state) {
            Button(
                modifier = Modifier
                    .align(Alignment.TopStart),
                onClick = {
                    onEventDispatcher(EditContract.Intent.Draft)
                }
            ) {
                Text(
                    text = "Draft"
                )
            }

            Button(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onClick = {
                    onEventDispatcher(EditContract.Intent.Submit)
                }
            ) {
                Text(
                    text = "Submit"
                )
            }

            Text(
                text = "User Input",
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 10.dp),
                fontSize = TextUnit(20f, TextUnitType.Sp)
            )
        }
    }
}