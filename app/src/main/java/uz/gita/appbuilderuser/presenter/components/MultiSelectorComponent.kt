package uz.gita.appbuilderuser.presenter.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MultiSelectorComponent(
    question: String,
    list: List<String> ,
    isRead: Boolean = false,
    click : (String) -> Unit
) {
    Column {
        Text(
            text = question,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 2.dp, vertical = 4.dp)
        )

        list.forEach { value ->
            var check by remember { mutableStateOf(false) }

            Row {
                Checkbox(
                    checked = check,
                    onCheckedChange = {
                        if (!isRead) {
                            if (!check) click(value)
                            else click("")
                            check = it
                        }
                    }
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = value,
                    color = Color.LightGray
                )
            }
        }
    }
}