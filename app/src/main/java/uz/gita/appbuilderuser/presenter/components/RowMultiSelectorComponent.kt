package uz.gita.appbuilderuser.presenter.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.appbuilderuser.data.model.ComponentsModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowMultiSelectorComponent(
    question: String,
    list: List<String>,
    onLongClick: () -> Unit,
    onClickDelete: () -> Unit,
    data: ComponentsModel,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
    ) {
        Column(modifier = Modifier
            .combinedClickable(onLongClick = onLongClick) { }
            .padding(bottom = 20.dp)) {
            Row {
                Text(
                    text = question,
                    color = Color.White
                )
            }

            list.forEach {
                var check by remember { mutableStateOf(false) }

                Row {
                    Checkbox(
                        checked = check,
                        onCheckedChange = { check = it }
                    )

                    Text(
                        modifier = Modifier,
                        text = it,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Rowprev() {
//    RowMultiSelectorComponent("question", listOf(), {}, {}, data = ComponentsModel())
}