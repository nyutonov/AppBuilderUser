package uz.gita.appbuilderuser.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GetVerticalSpaceSmall() {
    Spacer(modifier = Modifier.height(8.dp))
}
@Composable
fun GetVerticalSpaceMedium() {
    Spacer(modifier = Modifier.height(16.dp))
}
@Composable
fun GetVerticalSpaceLarge() {
    Spacer(modifier = Modifier.height(32.dp))
}