package uz.gita.appbuilderuser.presenter.userDataScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.appbuilderuser.R
import uz.gita.appbuilderuser.presenter.add.AddContract
import uz.gita.appbuilderuser.presenter.components.DrawsComponent


class UserDataScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val vm: UserDataContract.UserDataViewModel = getViewModel<UserDataViewModelImpl>()

        UserDataScreenContent(
            vm.uiState.collectAsState(),
            vm::onEventDispatcher
        )
    }

    @Composable
    fun UserDataScreenContent(
        uiState: State<UserDataContract.UiState>,
        onEventDispatcher: (UserDataContract.Intent) -> Unit,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F1C2E))
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFF0F1C2E))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color(0XFF1f2b3e))
                        .padding(horizontal = 15.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = "Draw",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(40.dp)
                            .align(Alignment.CenterStart)
                            .clickable { onEventDispatcher.invoke(UserDataContract.Intent.Logout) },
                        colorFilter = ColorFilter.tint(Color.White)
                    )

                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "${uiState.value.name} drafts",
                        fontSize = 28.sp,
                        fontFamily = FontFamily.Default,
                        color = Color.White
                    )

                    IconButton(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterEnd),
                        onClick = { onEventDispatcher.invoke(UserDataContract.Intent.AddDraw) }
                    ) {
                        Icon(
                            tint = Color.White,
                            painter = painterResource(id = R.drawable.baseline_add_24),
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.size(5.dp))

                LazyColumn {
                    items(uiState.value.data) {
                        DrawsComponent(
                            drawsData = it,
                            onClick = {
                                onEventDispatcher.invoke(
                                    UserDataContract.Intent.ClickItem(
                                        it.key,
                                        it.state
                                    )
                                )
                            },
                            onLongClick = {
                                onEventDispatcher.invoke(UserDataContract.Intent.DeleteItem(it.key))
                            }
                        )
                    }
                }
            }

            if (uiState.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            if (!uiState.value.isLoading && uiState.value.data.isEmpty()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "Empty",
                    color = Color.White
                )
            }
        }
    }
}