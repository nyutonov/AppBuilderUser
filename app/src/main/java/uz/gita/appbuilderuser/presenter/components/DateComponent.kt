package uz.gita.appbuilderuser.presenter.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateComponent(
    date: String = "",
    listener: (String) -> Unit = {}
) {
    val list = if (date.isNotEmpty()) date.split(" ") else listOf()

    Box {
        WheelDatePicker(
            startDate = if (list.isEmpty()) LocalDate.now() else LocalDate.of(
                list[0].toInt(),
                list[1].toInt(),
                list[2].toInt()
            ),
            textColor = Color.LightGray,
            onSnappedDate = {
                listener("${it.year} ${it.monthValue} ${it.dayOfMonth}")
            }
        )
    }
}