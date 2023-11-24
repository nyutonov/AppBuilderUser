package uz.gita.appbuilderuser.presenter.main

import android.util.Log
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.appbuilderuser.R
import uz.gita.appbuilderuser.data.model.DrawsData
import uz.gita.appbuilderuser.presenter.components.DateComponent
import uz.gita.appbuilderuser.presenter.components.InputComponent
import uz.gita.appbuilderuser.presenter.components.MultiSelectorComponent
import uz.gita.appbuilderuser.presenter.components.SampleSpinner
import uz.gita.appbuilderuser.presenter.components.TextComponent
import uz.gita.appbuilderuser.ui.theme.AppBuilderUserTheme

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
                ) {
                }
                Spacer(modifier = Modifier.size(5.dp))
                LazyColumn {
                    items(uiState.value.components) {

                        when (it.componentsName) {
                            "Text" -> {
                                if (!it.visibility) {
                                    TextComponent(it)
                                }else {

                                    var count = 0
                                    uiState.value.components.forEach {data ->
                                        if (it.operator == "==" || it.operator == "=") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name == it.value && count == 0) {
                                                            TextComponent(it)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else if(it.operator == "<=" ) {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() <= it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            TextComponent(it)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == ">=") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() >= it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            TextComponent(it)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == ">") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() > it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            TextComponent(it)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == "<") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() < it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            TextComponent(it)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            "Input" -> {
                                if (!it.visibility) {
                                    InputComponent(it , list = uiState.value.inputList) {value , id ->
                                        onEventDispatcher.invoke(MainContract.Intent.SetValue(value ,id))
                                    }
                                }else {

                                    var count = 0
                                    uiState.value.components.forEach {data ->
                                        if (it.operator == "==" || it.operator == "=") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name == it.value && count == 0) {
                                                            InputComponent(it , list = uiState.value.inputList) {value , id ->
                                                                onEventDispatcher.invoke(MainContract.Intent.SetValue(value ,id))
                                                            }
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if(it.operator == "<=" ) {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() <= it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            InputComponent(it , list = uiState.value.inputList) {value , id ->
                                                                onEventDispatcher.invoke(MainContract.Intent.SetValue(value ,id))
                                                            }
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == ">=") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() >= it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            InputComponent(it , list = uiState.value.inputList) {value , id ->
                                                                onEventDispatcher.invoke(MainContract.Intent.SetValue(value ,id))
                                                            }
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == ">") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() > it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            InputComponent(it , list = uiState.value.inputList) {value , id ->
                                                                onEventDispatcher.invoke(MainContract.Intent.SetValue(value ,id))
                                                            }
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == "<") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() < it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            InputComponent(it , list = uiState.value.inputList) {value , id ->
                                                                onEventDispatcher.invoke(MainContract.Intent.SetValue(value ,id))
                                                            }
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            "Selector" -> {
                                if (!it.visibility) {
                                    SampleSpinner(it)
                                }else {

                                    var count = 0
                                    uiState.value.components.forEach {data ->
                                        if (it.operator == "==" || it.operator == "=") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name == it.value && count == 0) {
                                                            SampleSpinner(it)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if(it.operator == "<=" ) {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() <= it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            SampleSpinner(it)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == ">=") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() >= it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            SampleSpinner(it)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == ">") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() > it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            SampleSpinner(it)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == "<") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() < it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            SampleSpinner(it)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }

                            "MultiSelector" -> {
                                if (!it.visibility) {
                                    MultiSelectorComponent(list = it.multiSelectorDataAnswers)
                                }else {

                                    var count = 0
                                    uiState.value.components.forEach {data ->
                                        if (it.operator == "==" || it.operator == "=") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name == it.value && count == 0) {
                                                            MultiSelectorComponent(list = it.multiSelectorDataAnswers)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if(it.operator == "<=" ) {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() <= it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            MultiSelectorComponent(list = it.multiSelectorDataAnswers)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == ">=") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() >= it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            MultiSelectorComponent(list = it.multiSelectorDataAnswers)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == ">") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() > it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            MultiSelectorComponent(list = it.multiSelectorDataAnswers)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == "<") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() < it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            MultiSelectorComponent(list = it.multiSelectorDataAnswers)
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            "Date Picker" -> {
                                if (!it.visibility) {
                                    DateComponent()
                                }else {

                                    var count = 0
                                    uiState.value.components.forEach {data ->
                                        if (it.operator == "==" || it.operator == "=") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name == it.value && count == 0) {
                                                            DateComponent()
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if(it.operator == "<=" ) {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() <= it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            DateComponent()
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == ">=") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() >= it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            DateComponent()
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == ">") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() > it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            DateComponent()
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }else if (it.operator == "<") {
                                            if (it.idVisibility == data.id) {
                                                uiState.value.inputList.forEach {model ->
                                                    if (data.id == model.id) {
                                                        if (model.name.isNotEmpty() && !model.name.contains("""\D""".toRegex()) && model.name.toInt() < it.value.replace("""\D""".toRegex() , "").toInt() && count == 0) {
                                                            DateComponent()
                                                            count++
                                                        }else {
                                                            count = 0
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }

            Image(
                painter = painterResource(id = R.drawable.draw_svgrepo_com),
                contentDescription = "Draw",
                modifier = Modifier
                    .padding(8.dp)
                    .size(40.dp)
                    .align(Alignment.TopStart)
                    .clickable {
                        onEventDispatcher.invoke(
                            MainContract.Intent.ClickDrawButton(
                                DrawsData(
                                    key = "",
                                    value = "",
                                    false
                                )
                            )
                        )
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.save_2_svgrepo_com),
                contentDescription = "Draw",
                modifier = Modifier
                    .padding(8.dp)
                    .size(40.dp)
                    .align(Alignment.TopEnd)
            )

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
fun textTopComponent(text: String) {
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