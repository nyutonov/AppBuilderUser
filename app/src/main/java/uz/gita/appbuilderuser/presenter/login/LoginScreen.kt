package uz.gita.appbuilderuser.presenter.login

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.appbuilderuser.R
import uz.gita.appbuilderuser.presenter.components.CustomButton
import uz.gita.appbuilderuser.presenter.components.EditTextField
import uz.gita.appbuilderuser.presenter.components.GetVerticalSpaceLarge
import uz.gita.appbuilderuser.presenter.components.GetVerticalSpaceSmall


class LoginScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val vm: LoginContract.ViewModel = getViewModel<LoginViewModel>()
        val sideEffect = vm.sideEffect.collectAsState()

        when (sideEffect.value) {
            LoginContract.SideEffect.Init -> {}
            is LoginContract.SideEffect.ShowToast -> {
                Toast.makeText(
                    LocalContext.current,
                    (sideEffect.value as LoginContract.SideEffect.ShowToast).message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        LoginScreenContent(
            uiState = vm.uiState.collectAsState(),
            onEventDispatcher = vm::onEventDispatcher
        )

    }
}

@Composable
fun LoginScreenContent(
    uiState: State<LoginContract.UiState>,
    onEventDispatcher: (LoginContract.Intent) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF253F63))
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color(0xFF253F63))
        ) {
            GetVerticalSpaceLarge()
            Image(
                painter = painterResource(id = R.drawable.login_image),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 119.dp)
                    .width(188.dp)
                    .height(174.dp)
                    .align(CenterHorizontally),
                contentScale = ContentScale.Crop

            )
            GetVerticalSpaceLarge()

            EditTextField(labelText = "Username",
                value = uiState.value.name,
                paddingHorizontal = 16.dp,
                trailIcon = { },
                onValueChanged = { onEventDispatcher.invoke(LoginContract.Intent.EnteringName(it)) }
            )

            GetVerticalSpaceSmall()
            EditTextField(
                labelText = "Password",
                value = uiState.value.password,
                trailIcon = {
                    Icon(
                        painter = painterResource(id = if (uiState.value.showPassword) R.drawable.view else R.drawable.hide),
                        contentDescription = "Password show",
                        modifier = Modifier
                            .clickable {
                                onEventDispatcher.invoke(LoginContract.Intent.ClickPasswordEye)
                            }
                            .padding(15.dp)
                    )
                },
                visualTransformation = if (uiState.value.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                paddingHorizontal = 16.dp,
                onValueChanged = { onEventDispatcher.invoke(LoginContract.Intent.EnteringPassword(it)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            GetVerticalSpaceSmall()

            CustomButton(
                text = "Login",
                buttonState = uiState.value.buttonState,
                horizontalPadding = 16.dp,
                verticalPadding = 10.dp,
                progressAlpha = if (uiState.value.progress) 1f else 0f,
            )
            {
                Log.d("TTT", "LoginScreenContent: Login Bosildi")
                onEventDispatcher(LoginContract.Intent.Login)
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(showBackground = true)
fun LoginScreenPrev() {
    LoginScreenContent(mutableStateOf(LoginContract.UiState())) {}
}