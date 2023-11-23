package uz.gita.appbuilderuser.presenter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MultiSelectorComponent(
    list: List<String>
) {
    Column {
        list.forEach {
            var check by remember { mutableStateOf(false) }

            Row {
                Checkbox(
                    checked = check,
                    onCheckedChange = { check = it }
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = it
                )
            }
        }
    }
}