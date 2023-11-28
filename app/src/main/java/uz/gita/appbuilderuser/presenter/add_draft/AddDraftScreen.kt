package uz.gita.appbuilderuser.presenter.add_draft

import android.os.Build
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.appbuilderuser.presenter.components.DateComponent
import uz.gita.appbuilderuser.presenter.components.InputComponent
import uz.gita.appbuilderuser.presenter.components.MultiSelectorComponent
import uz.gita.appbuilderuser.presenter.components.SampleSpinner
import uz.gita.appbuilderuser.presenter.components.TextComponent
import uz.gita.appbuilderuser.ui.theme.AppBuilderUserTheme

class AddDraftScreen : AndroidScreen() {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val viewModel: AddDraftContract.ViewModel = getViewModel<AddDraftViewModel>()

        AppBuilderUserTheme {
            MainContent(
                viewModel.uiState.collectAsState().value,
                viewModel::onEventDispatcher
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MainContent(
    uiState: AddDraftContract.UIState,
    onEventDispatcher: (AddDraftContract.Intent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1C2E))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0XFF1f2b3e))
                .padding(horizontal = 15.dp)
        ) {
            Button(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                onClick = {
                    onEventDispatcher(AddDraftContract.Intent.Draft)
                }
            ) {
                Text(
                    text = "Draft"
                )
            }

            Button(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                onClick = {
                    onEventDispatcher(AddDraftContract.Intent.Submit)
                }
            ) {
                Text(
                    text = "Submit"
                )
            }
        }

        Spacer(modifier = Modifier.size(5.dp))

        LazyColumn {
            itemsIndexed(uiState.components) { index, item ->
                when (item.componentsName) {
                    "Text" -> {
                        TextComponent(
                            item
                        )
                    }

                    "Input" -> {
                        InputComponent(
                            item,
                            isSubmit = uiState.isCheck
                        ) { _, value ->
                            onEventDispatcher.invoke(AddDraftContract.Intent.ChangeInputValue(value, index))
                        }
                    }

                    "Selector" -> {
                        SampleSpinner(
                            question = item.selectorDataQuestion,
                            item
                        ) { _, value ->
                            onEventDispatcher.invoke(AddDraftContract.Intent.ChangeSelectorValue(value, index))
                        }
                    }

                    "MultiSelector" -> {
                        MultiSelectorComponent(
                            list = item.multiSelectorDataAnswers,
                            question = item.multiSelectDataQuestion
                        ) {
//                            true bolsa value ni ozi keladi aks holda ""
                        }
                    }

                    "Date Picker" -> {
                        DateComponent(
                            item.datePicker
                        ) { value ->
                            onEventDispatcher.invoke(AddDraftContract.Intent.ChangeDataPicker(value, index))
                        }
                    }
                }
            }
        }

        onEventDispatcher.invoke(AddDraftContract.Intent.Check(false))
    }
}