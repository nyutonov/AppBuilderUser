
package uz.gita.appbuilderuser.presenter.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.commandiron.wheel_picker_compose.WheelDatePicker
import uz.gita.appbuilderuser.data.model.ComponentsModel
import java.time.LocalDate


@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RowDateComponent(
    date: String = "",
    listener: (String) -> Unit,
    onLongClick: () -> Unit,
    dataaa: ComponentsModel,
    modifier: Modifier = Modifier,
) {
    val list = if (date.isNotEmpty()) date.split(" ") else listOf()

    WheelDatePicker(
        modifier = modifier,
//                .combinedClickable(onLongClick = onLongClick) { },
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