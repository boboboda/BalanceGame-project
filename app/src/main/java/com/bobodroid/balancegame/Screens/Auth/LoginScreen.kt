package com.bobodroid.balancegame.Screens.Auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobodroid.balancegame.MainRoute
import com.bobodroid.balancegame.Routes.AuthRoute
import com.bobodroid.balancegame.Routes.AuthRouteAction
import com.bobodroid.balancegame.TAG
import com.bobodroid.balancegame.conponents.BaseButton
import com.bobodroid.balancegame.conponents.GamePasswordTextField
import com.bobodroid.balancegame.conponents.GameTextField
import com.bobodroid.balancegame.conponents.LogInBackButton
import com.bobodroid.balancegame.ui.theme.Primary
import com.bobodroid.balancegame.ui.theme.Purple200
import com.bobodroid.balancegame.viewmodels.AuthViewModel
import com.bobodroid.balancegame.viewmodels.GameViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(routeAction: AuthRouteAction, authViewModel: AuthViewModel, gameViewModel: GameViewModel){

    val emailInput = authViewModel.logInEmailInputFlow.collectAsState()

    val passwordInput = authViewModel.logInPasswordInputFlow.collectAsState()

    val isLoginBtnActive = emailInput.value.isNotEmpty() && passwordInput.value.isNotEmpty()

    val isLoggedIn = authViewModel.isLoggedIn.collectAsState()

    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier.padding(horizontal = 22.dp),
    ) {

        LogInBackButton(
            modifier = Modifier
                .padding(vertical = 20.dp),
            onClick =  routeAction.goBack
        )
        Text(
            "로그인 화면",
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        GameTextField(
            label = "이메일",
            value = emailInput.value,
            onValueChanged = {
                coroutineScope.launch {
                    authViewModel.logInEmailInputFlow.emit(it)
                }
            })

        Spacer(modifier = Modifier.height(30.dp))

        GamePasswordTextField(
            label = "비밀번호",
            value = passwordInput.value,
            onValueChanged = {
                coroutineScope.launch {
                    authViewModel.logInPasswordInputFlow.emit(it)
                }
            })

        Spacer(modifier = Modifier.height(30.dp))


        BaseButton(
            title = "로그인",
            enabled = isLoginBtnActive,
            onClick = {
                authViewModel.loginUser()
                coroutineScope.launch {
                    authViewModel.loadUserData()
                }
                Log.d("웰컴스크린", "로그인 버튼 클릭")
            })

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "계정이 없으신가요?")
            TextButton(onClick = { routeAction.navTo(AuthRoute.REGISTER)}) {
                Text(text = "회원가입 하러가기", color = Primary)
            }

            TextButton(onClick = {
                coroutineScope.launch {
                    authViewModel.isLoggedIn.emit(true)

                }

            }) {
                Text(text = "로그인완료", color = Primary)
            }
        }
    }
}