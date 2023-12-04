package uz.gita.appbuilderuser.presenter.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.appbuilderuser.R
import uz.gita.appbuilderuser.presenter.add_draft.AddDraftContract
import uz.gita.appbuilderuser.presenter.components.DateComponent
import uz.gita.appbuilderuser.presenter.components.ImageComponent
import uz.gita.appbuilderuser.presenter.components.InputComponent
import uz.gita.appbuilderuser.presenter.components.MultiSelectorComponent
import uz.gita.appbuilderuser.presenter.components.RowComponent
import uz.gita.appbuilderuser.presenter.components.SampleSpinner
import uz.gita.appbuilderuser.presenter.components.TextComponent
import uz.gita.appbuilderuser.ui.theme.AppBuilderUserTheme

class MainScreen(private val name: String) : AndroidScreen() {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val vm: MainContract.MainViewModel = getViewModel<MainViewModelImpl>()
        vm.onEventDispatcher(MainContract.Intent.Load(name))

        MainContent(
            uiState = vm.uiState.collectAsState(),
            onEventDispatcher = vm::onEventDispatcher
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun MainContent(
        uiState: State<MainContract.UiState>,
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
                ComposeLottieAnimation(modifier = Modifier.align(Alignment.Center))
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
                    itemsIndexed(uiState.value.components) {index, it ->
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

                                        if(module.componentName == "in") {
                                            name = ""
                                            var value = ""
                                            uiState.value.inputList.forEach { input ->
                                                if(module.componentId == input.id) {
                                                    value = input.value
                                                    Log.d("TTT" , "value $value")
                                                }
                                                if(module.inMultiSelectorValue == value) {
                                                    Log.d("TTT" , "hello")
                                                    TextComponent(it)
                                                }
                                            }
                                        }
                                        else if(module.componentName == "!in") {
                                            name = ""
                                            var value = ""
                                            uiState.value.inputList.forEach { input ->
                                                if(module.componentId == input.id) {
                                                    value = input.value
                                                }
                                                Log.d("TTT" , "size : ${module.list.size}")
                                                module.list.forEach { data->
                                                    if(value == data) {
                                                        TextComponent(it)
                                                    }
                                                }
                                            }
                                        }
                                        else if (module.componentName == "Input") {
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
                                        isSubmit = uiState.value.isCheck
                                    ) { id, value ->
                                        onEventDispatcher.invoke(MainContract.Intent.OnChangeInputValue(id , value))
                                        onEventDispatcher(MainContract.Intent.ChangeInputValue(value , index))
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
                                        if(module.componentName == "in") {
                                            name = ""
                                            var value = ""
                                            uiState.value.inputList.forEach { input ->
                                                if(module.componentId == input.id) {
                                                    value = input.value
                                                }
                                                if(module.inMultiSelectorValue == value) {
                                                    InputComponent(
                                                        it,
                                                        isSubmit = uiState.value.isCheck
                                                    ) { id, value ->
                                                        onEventDispatcher.invoke(MainContract.Intent.OnChangeInputValue(id , value))
                                                        onEventDispatcher(MainContract.Intent.ChangeInputValue(value , index))
                                                    }
                                                }
                                            }
                                        }
                                        else if(module.componentName == "!in") {
                                            name = ""
                                            var value = ""
                                            uiState.value.inputList.forEach { input ->
                                                if(module.componentId == input.id) {
                                                    value = input.value
                                                }
                                                Log.d("TTT" , "size : ${module.list.size}")
                                                module.list.forEach { data->
                                                    if(value == data) {
                                                        InputComponent(
                                                            it,
                                                            isSubmit = uiState.value.isCheck
                                                        ) { id, value ->
                                                            onEventDispatcher.invoke(MainContract.Intent.OnChangeInputValue(id , value))
                                                            onEventDispatcher(MainContract.Intent.ChangeInputValue(value , index))
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else if (module.componentName == "Input") {
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
                                                                    isSubmit = uiState.value.isCheck
                                                                ) { id, value ->
                                                                    onEventDispatcher.invoke(MainContract.Intent.OnChangeInputValue(id , value))
                                                                    onEventDispatcher(MainContract.Intent.ChangeInputValue(value , index))
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
                                                            Log.d("TTT", "enter multi selector")
                                                            if (module.value == input.value) {
                                                                InputComponent(
                                                                    it,
                                                                    isSubmit = uiState.value.isCheck
                                                                ) { id, value ->
                                                                    onEventDispatcher.invoke(MainContract.Intent.OnChangeInputValue(id , value))
                                                                    onEventDispatcher(MainContract.Intent.ChangeInputValue(value , index))
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
                                            isSubmit = uiState.value.isCheck
                                        ) { id, value ->
                                            onEventDispatcher.invoke(MainContract.Intent.OnChangeInputValue(id , value))
                                            onEventDispatcher(MainContract.Intent.ChangeInputValue(value , index))
                                        }
                                    }
                                }
                            }

                            "Selector" -> {
                                if (!it.visibility) {
                                    SampleSpinner(
                                        question = it.selectorDataQuestion,
                                        it
                                    ) { id, value ->
                                        onEventDispatcher.invoke(MainContract.Intent.OnChangeInputValue(id , value))
                                        onEventDispatcher(MainContract.Intent.ChangeSelectorValue(value , index))
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
                                        if(module.componentName == "in") {
                                            name = ""
                                            var value = ""
                                            uiState.value.inputList.forEach { input ->
                                                if(module.componentId == input.id) {
                                                    value = input.value
                                                }
                                                if(module.inMultiSelectorValue == value) {
                                                    SampleSpinner(
                                                        question = it.selectorDataQuestion,
                                                        it
                                                    ) { id, value ->
                                                        onEventDispatcher.invoke(MainContract.Intent.OnChangeInputValue(id , value))
                                                        onEventDispatcher(MainContract.Intent.ChangeSelectorValue(value , index))
                                                    }
                                                }
                                            }
                                        }
                                        else if(module.componentName == "!in") {
                                            name = ""
                                            var value = ""
                                            uiState.value.inputList.forEach { input ->
                                                if(module.componentId == input.id) {
                                                    value = input.value
                                                }
                                                Log.d("TTT" , "size : ${module.list.size}")
                                                module.list.forEach { data->
                                                    if(value == data) {
                                                        SampleSpinner(
                                                            question = it.selectorDataQuestion,
                                                            it
                                                        ) { id, value ->
                                                            onEventDispatcher.invoke(MainContract.Intent.OnChangeInputValue(id , value))
                                                            onEventDispatcher(MainContract.Intent.ChangeSelectorValue(value , index))
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else if (module.componentName == "Input") {
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
                                                                    it
                                                                ) { id, value ->
                                                                    onEventDispatcher.invoke(MainContract.Intent.OnChangeInputValue(id , value))
                                                                    onEventDispatcher(MainContract.Intent.ChangeSelectorValue(value , index))
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
                                                            Log.d("TTT", "enter multi selector")
                                                            if (module.value == input.value) {
                                                                SampleSpinner(
                                                                    question = it.selectorDataQuestion,
                                                                    it
                                                                ) { id, value ->
                                                                    onEventDispatcher.invoke(MainContract.Intent.OnChangeInputValue(id , value))
                                                                    onEventDispatcher(MainContract.Intent.ChangeSelectorValue(value , index))
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
                                            it
                                        ) { id, value ->
                                            onEventDispatcher.invoke(MainContract.Intent.OnChangeInputValue(id , value))
                                            onEventDispatcher(MainContract.Intent.ChangeSelectorValue(value , index))
                                        }
                                    }
                                }
                            }

                            "MultiSelector" -> {
                                if (!it.visibility) {
                                    MultiSelectorComponent(
                                        list = it.multiSelectorDataAnswers,
                                        question = it.multiSelectDataQuestion
                                    ) {value ->
//                            true bolsa value ni ozi keladi aks holda ""
                                        onEventDispatcher(
                                            MainContract.Intent.OnChangeInputValue(
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
                                                                    question = it.multiSelectDataQuestion
                                                                ) {value ->
//                            true bolsa value ni ozi keladi aks holda ""
                                                                    onEventDispatcher(
                                                                        MainContract.Intent.OnChangeInputValue(
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
                                                            Log.d("TTT", "enter multi selector")
                                                            if (module.value == input.value) {
                                                                MultiSelectorComponent(
                                                                    list = it.multiSelectorDataAnswers,
                                                                    question = it.multiSelectDataQuestion
                                                                ) {value ->
//                            true bolsa value ni ozi keladi aks holda ""
                                                                    onEventDispatcher(
                                                                        MainContract.Intent.OnChangeInputValue(
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
                                            question = it.multiSelectDataQuestion
                                        ) { value ->
                                            onEventDispatcher(
                                                MainContract.Intent.OnChangeInputValue(
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
                                        onEventDispatcher.invoke(MainContract.Intent.OnChangeInputValue(it.id  , value))
                                        onEventDispatcher(MainContract.Intent.ChangeDataPicker(value , index))
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
                                                                DateComponent(){value ->
                                                                    onEventDispatcher(MainContract.Intent.ChangeDataPicker(value , index))
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
                                                        Log.d("TTT", "enter multi selector")
                                                        if (module.value == input.value) {
                                                            DateComponent(){value ->
                                                                onEventDispatcher(MainContract.Intent.ChangeDataPicker(value , index))
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
                                        DateComponent(){value ->
                                            onEventDispatcher(MainContract.Intent.ChangeDataPicker(value , index))
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
            }

            Button(
                modifier = Modifier
                    .align(Alignment.TopStart),
                onClick = {
                    onEventDispatcher(MainContract.Intent.Draft)
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
                    onEventDispatcher(MainContract.Intent.Submit)
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

@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    AppBuilderUserTheme {
        MainScreen(name = "YourName")
    }
}


@Composable
fun TextTopComponent(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .height(2.dp)
                .background(
                    Color.White
                )
                .align(Alignment.CenterVertically)
        ) {
        }
        Spacer(modifier = Modifier.size(12.dp))
        Box(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = text, fontSize = 15.sp, modifier = Modifier.align(
                    Alignment.Center
                ), color = Color.White
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Row(
            modifier = Modifier
                .weight(1f)
                .height(2.dp)
                .background(
                    Color.White
                )
                .align(Alignment.CenterVertically)
        ) {
        }
    }
}

@Composable
fun ComposeLottieAnimation(modifier: Modifier) {
//
//    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty))
//
//    LottieAnimation(
//        modifier = modifier,
//        composition = composition,
//        iterations = LottieConstants.IterateForever,
//    )
}