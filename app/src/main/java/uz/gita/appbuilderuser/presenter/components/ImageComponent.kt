package uz.gita.appbuilderuser.presenter.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.core.net.toUri
import coil.compose.AsyncImage

@Composable
fun ImageComponent(
    size: String,
    uri: String,
    color: ULong,
    height: Float,
    aspectRatio: Float
) {
    var loader by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    Box(
        modifier = when (size) {
            "Custom" -> {
                Modifier
                    .fillMaxWidth()
                    .height(with(density) { height.toDp() })
                    .background(Color(color))
            }

            "Ratio" -> {
                Modifier
                    .aspectRatio(if (aspectRatio == 0f) 1f else aspectRatio)
                    .background(Color(color))
            }

            else -> {
                Modifier
                    .fillMaxWidth()
                    .background(Color(color))
            }
        }
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.Center),
            model = uri.toUri(),
            contentDescription = "",
            onLoading = { loader = true },
            onSuccess = { loader = false },
            onError = { loader = false }
        )

        if (loader) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}