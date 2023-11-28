package uz.gita.appbuilderuser.presenter.componentsimport android.util.Logimport androidx.compose.foundation.layout.fillMaxWidthimport androidx.compose.foundation.layout.paddingimport androidx.compose.foundation.text.KeyboardOptionsimport androidx.compose.material3.ExperimentalMaterial3Apiimport androidx.compose.material3.Textimport androidx.compose.material3.TextFieldimport androidx.compose.runtime.Composableimport androidx.compose.runtime.getValueimport androidx.compose.runtime.mutableStateOfimport androidx.compose.runtime.rememberimport androidx.compose.runtime.setValueimport androidx.compose.ui.Modifierimport androidx.compose.ui.text.TextStyleimport androidx.compose.ui.text.input.KeyboardTypeimport androidx.compose.ui.unit.dpimport androidx.compose.ui.unit.spimport uz.gita.appbuilderuser.data.model.ComponentsModel@OptIn(ExperimentalMaterial3Api::class)@Composablefun InputComponent(    data: ComponentsModel,    isRead: Boolean = false,    listener: (String , String) -> Unit) {    var value by remember {        mutableStateOf(data.text)    }    TextField(        value = value,        onValueChange = { input ->            if (data.type == "Number") {                val numericValue = input.filter { it.isDigit() }                if (data.isMaxValueForNumberEnabled) {                    if (numericValue.isEmpty()) {                        value = ""                    } else {                        numericValue.toIntOrNull()?.let { number ->                            if (numericValue[0] != '0' && number < data.maxValueForNumber) {                                value = numericValue                            }                        }                    }                } else {                    value = numericValue                }            } else if (data.type == "Text") {                if (data.isMaxLengthForTextEnabled) {                    if (input.length <= data.maxLengthForText) {                        value = input                    }                } else {                    value = input                }            } else {                value = input            }            listener(data.id , value)        },        modifier = Modifier            .fillMaxWidth()            .padding(16.dp),        placeholder = { Text(text = data.placeHolder) },        singleLine = true,        textStyle = TextStyle(fontSize = 18.sp),        readOnly = isRead,        keyboardOptions = KeyboardOptions(            keyboardType = when (data.type) {                "Email" -> KeyboardType.Email                "Number" -> KeyboardType.Number                else -> KeyboardType.Text            }        )    )}