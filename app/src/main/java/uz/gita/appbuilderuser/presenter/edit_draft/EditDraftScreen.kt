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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.appbuilderuser.data.model.DrawsData
import uz.gita.appbuilderuser.presenter.add_draft.AddDraftContract
import uz.gita.appbuilderuser.presenter.components.DateComponent
import uz.gita.appbuilderuser.presenter.components.InputComponent
import uz.gita.appbuilderuser.presenter.components.MultiSelectorComponent
import uz.gita.appbuilderuser.presenter.components.RowComponent
import uz.gita.appbuilderuser.presenter.components.SampleSpinner
import uz.gita.appbuilderuser.presenter.components.TextComponent
import uz.gita.appbuilderuser.ui.theme.AppBuilderUserTheme

class EditDraftScreen(val key_: String, val state: Boolean) : AndroidScreen() {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val viewModel: EditDraftContract.ViewModel = getViewModel<EditDraftViewModel>()

        if (viewModel.uiState.collectAsState().value.key.isEmpty()) {
            viewModel.onEventDispatcher(EditDraftContract.Intent.LoadData(key_, state))
        }

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
    uiState: EditDraftContract.UIState,
    onEventDispatcher: (EditDraftContract.Intent) -> Unit
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
            if (uiState.components.isNotEmpty() && !uiState.state) {
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterStart),
                    onClick = {
                        onEventDispatcher.invoke(EditDraftContract.Intent.Draft)
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
                        onEventDispatcher.invoke(EditDraftContract.Intent.Submit)
                    }
                ) {
                    Text(
                        text = "Submit"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(5.dp))

        LazyColumn {
            itemsIndexed(uiState.components) { index, item ->
                Log.d("TTT", "rowType: ${item.rowType}")
                when (item.componentsName) {

                    "Row"->{
                        RowComponent(componentsModel = item.lsRow)
                    }
                    "Text" -> {
                        TextComponent(
                            item
                        )
                    }

                    "Input" -> {
                        InputComponent(
                            item,
                            uiState.state,
                            isSubmit = uiState.isCheck
                        ) { _, value ->
                            onEventDispatcher.invoke(
                                EditDraftContract.Intent.ChangeInputValue(
                                    value,
                                    index
                                )
                            )
                        }
                    }

                    "Selector" -> {
                        SampleSpinner(
                            question = item.selectorDataQuestion,
                            item,
                            uiState.state
                        ) { _, value ->
                            onEventDispatcher.invoke(
                                EditDraftContract.Intent.ChangeSelectorValue(
                                    value,
                                    index
                                )
                            )
                        }
                    }

                    "MultiSelector" -> {
                        MultiSelectorComponent(
                            list = item.multiSelectorDataAnswers,
                            question = item.multiSelectDataQuestion,
                            isRead = uiState.state
                        ) {}
                    }

                    "Date Picker" -> {
                        DateComponent(
                            item.datePicker
                        ) { value ->
                            onEventDispatcher.invoke(
                                EditDraftContract.Intent.ChangeDataPicker(
                                    value,
                                    index
                                )
                            )
                        }
                    }
                }
            }
        }

        onEventDispatcher.invoke(EditDraftContract.Intent.Check(false))
    }
}