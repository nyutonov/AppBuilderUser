package uz.gita.appbuilderuser.presenter.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import uz.gita.appbuilderuser.data.model.ComponentsModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowTextComponent(
    data: ComponentsModel,
    onClick: () -> Unit = {},
    modifier: Modifier,
) {
    Row(modifier = modifier) {
        Text(
            text = data.text,
//            modifier = Modifier
//                .align(Alignment.CenterVertically)
//                .background(Color(0x80FFFFFF))
//                .padding(start = 2.dp),
            color = Color.White,
        )
    }
}
