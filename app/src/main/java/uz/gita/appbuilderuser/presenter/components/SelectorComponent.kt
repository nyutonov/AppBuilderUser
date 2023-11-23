package uz.gita.appbuilderuser.presenter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uz.gita.appbuilderuser.data.model.ComponentsModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleSpinner(
    data: ComponentsModel,
    listener: (String) -> Unit
) {

    var selected by remember { mutableStateOf(data.preselected) }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            OutlinedTextField(
                value = (selected),
                onValueChange = {
                    listener.invoke(it)
                },
                label = { Text(text = "List name") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                readOnly = true
            )
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                data.selectorDataAnswers.forEach { entry ->

                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selected = entry
                            expanded = false
                        },
                        text = {
                            Text(
                                text = (entry),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .align(Alignment.Start)
                            )
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { expanded = !expanded }
                )
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SampleSpinner_Preview() {
//    MaterialTheme {
//
//        val entry1 = Pair("Key1", "Entry1")
//        val entry2 = Pair("Key2", "Entry2")
//        val entry3 = Pair("Key3", "Entry3")
//
//        SampleSpinner(
//            data = ComponentsModel(
//                preselected = entry1,
//                selectorDataAnswers = listOf(entry1, entry2, entry3)
//            )
//        )
//    }
//}