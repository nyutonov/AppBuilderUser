package uz.gita.appbuilderuser.presenter.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uz.gita.appbuilderuser.ui.theme.BaseColor
import uz.gita.appbuilderuser.ui.theme.TextFieldColor
import uz.gita.appbuilderuser.ui.theme.buttonColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextField(
    labelText: String,
    value: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailIcon: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    ),
    onValueChanged: (String) -> Unit,
    paddingHorizontal: Dp = 0.dp,
    borderColor: Color = Color.Gray,
) {
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = paddingHorizontal)
            .height(58.dp)
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(5.dp)),
        placeholder = { Text(text = labelText) },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        value = value,
        onValueChange = onValueChanged,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = TextFieldColor,
            focusedIndicatorColor = borderColor,
            unfocusedIndicatorColor = borderColor,
            disabledLabelColor = Color.LightGray,
            cursorColor = BaseColor,
        ),
        shape = RoundedCornerShape(5.dp),
        singleLine = true,
        visualTransformation = visualTransformation,
        trailingIcon = trailIcon

//        trailingIcon = {
//            IconButton(onClick = onTrailingIconClick) {
//                trailIcon()
//            }
//        },

    )
}

@Composable
@Preview(showBackground = true)
fun EditTextFieldPreview() {
}

@Composable
fun CustomButton(
    text: String,
    buttonState: Boolean,
    progressAlpha: Float,
    horizontalPadding: Dp = 0.dp,
    verticalPadding: Dp = 0.dp,
    block: () -> Unit,
) {
    Button(
        onClick = { block.invoke() },
        enabled = buttonState,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = buttonColor, disabledContainerColor = BaseColor
        ),
        shape = RoundedCornerShape(12.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
            .height(56.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.align(CenterHorizontally)
                )
            }
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier
                    .padding(4.dp)
                    .height(30.dp)
                    .alpha(progressAlpha)
            )
        }
    }
}

@Composable
fun TrailingIconView(text: String) {
    IconButton(
        onClick = {
            text
        },
    ) {
        Icon(
            Icons.Default.Clear,
            contentDescription = "",
            tint = Color.Black
        )
    }
}
