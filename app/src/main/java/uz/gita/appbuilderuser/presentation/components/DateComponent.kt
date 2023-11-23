package uz.gita.appbuilderuser.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.commandiron.wheel_picker_compose.WheelDatePicker
import java.time.LocalDate

@Composable
fun DateComponent() {
    WheelDatePicker(
        onSnappedDate = {

        }
    )
}