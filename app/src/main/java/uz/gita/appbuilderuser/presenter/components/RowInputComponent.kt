
package uz.gita.appbuilderuser.presenter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.appbuilderuser.data.model.ComponentsModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowInputComponent(
    data: ComponentsModel,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var value by remember { mutableStateOf("") }

    TextField(
        value = value,
        onValueChange = { input ->
            if (data.type == "Number") {
                val numericValue = input.filter { it.isDigit() }

                if (data.isMaxValueForNumberEnabled) {
                    if (numericValue.isEmpty()) {
                        value = ""
                    } else {
                        numericValue.toIntOrNull()?.let { number ->
                            if (numericValue[0] != '0' && number < data.maxValueForNumber) {
                                value = numericValue
                            }
                        }
                    }
                } else {
                    value = numericValue
                }
            } else if (data.type == "Text") {
                if (data.isMaxLengthForTextEnabled) {
                    if (input.length <= data.maxLengthForText) {
                        value = input
                    }
                } else {
                    value = input
                }
            } else {
                value = input
            }
        },

        modifier = modifier // Anvarxon aytti
            .background(Color.Transparent)
            .padding(2.dp, bottom = 18.dp),
        placeholder = {
            Text(text = data.placeHolder)
        },
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(
            keyboardType = when (data.type) {
                "Email" -> KeyboardType.Email
                "Number" -> KeyboardType.Number
                "Phone" -> KeyboardType.Phone
                else -> KeyboardType.Text
            }
        )
    )
}


@Composable
@Preview
fun Prev() {
//    RowInputComponent(data = ComponentsModel(), {})
}

