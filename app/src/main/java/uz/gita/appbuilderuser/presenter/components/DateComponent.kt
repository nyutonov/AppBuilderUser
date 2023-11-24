package uz.gita.appbuilderuser.presenter.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.commandiron.wheel_picker_compose.WheelDatePicker
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateComponent(
    date: String = "",
) {
    val list = if (date.isNotEmpty()) date.split(" ") else listOf()

    WheelDatePicker(
        startDate = if (list.isEmpty()) LocalDate.now() else LocalDate.of(
            list[0].toInt(),
            list[1].toInt(),
            list[2].toInt()
        ),
        textColor = Color.LightGray,
        onSnappedDate = {

        }
    )
}