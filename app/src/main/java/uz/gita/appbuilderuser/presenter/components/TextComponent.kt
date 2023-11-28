package uz.gita.appbuilderuser.presenter.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import uz.gita.appbuilderuser.data.model.ComponentsModel

@Composable
fun TextComponent(
    data: ComponentsModel
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = data.text, modifier = Modifier.padding(start = 10.dp),
            color = Color.White
        )
    }
}