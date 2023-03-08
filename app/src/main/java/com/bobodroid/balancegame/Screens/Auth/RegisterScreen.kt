package com.bobodroid.balancegame.Screens.Auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.bobodroid.balancegame.MainActivity.Companion.TAG
import com.bobodroid.balancegame.Routes.AuthRoute
import com.bobodroid.balancegame.Routes.AuthRouteAction
import com.bobodroid.balancegame.conponents.BaseButton
import com.bobodroid.balancegame.conponents.GamePasswordTextField
import com.bobodroid.balancegame.conponents.GameTextField
import com.bobodroid.balancegame.conponents.LogInBackButton
import com.bobodroid.balancegame.ui.theme.Purple200
import com.bobodroid.balancegame.viewmodels.AuthViewModel
import com.bobodroid.balancegame.viewmodels.GameViewModel
import com.bobodroid.balancegame.viewmodels.MyPageViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(authViewModel: AuthViewModel,
                   routeAction: AuthRouteAction,
                   myPageViewModel: MyPageViewModel, gameViewModel: GameViewModel) {

    val emailInput = authViewModel.registerEmailInputFlow.collectAsState()

    val nickname = authViewModel.registerNicknameFlow.collectAsState()

    val passwordInput = authViewModel.registerPasswordInputFlow.collectAsState()

    val passwordConfirmInput = authViewModel.registerPasswordConfirmInputFlow.collectAsState()

    val isPasswordsNotEmpty = passwordInput.value.isNotEmpty() &&
            passwordConfirmInput.value.isNotEmpty()


    val isPasswordMatched = passwordInput.value == passwordConfirmInput.value

    val isRegisterBtnActive = emailInput.value.isNotEmpty() &&
            isPasswordsNotEmpty && isPasswordMatched

    val scrollState = rememberScrollState()

    val snackBarHostState = remember{ SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()

    val isLoading = authViewModel.registerIsLoadingFlow.collectAsState()

    val registerSuccess = authViewModel.registerSuccessFlow.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        authViewModel.registerCompleteFlow.collectLatest {
            snackBarHostState
                .showSnackbar(
                "회원가입 완료! 로그인 해주세요!",
                    actionLabel = "확인", SnackbarDuration.Short)
                .let{
                    when(it) {
                        SnackbarResult.Dismissed -> Log.d("TAG", "스낵바 닫힘")
                        SnackbarResult.ActionPerformed ->{
                            routeAction.navTo(AuthRoute.LOGIN)
                        }
                    }
                }
        }
    })

    Column(
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .fillMaxSize()
            .verticalScroll(scrollState, enabled = true),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        LogInBackButton(
            modifier = Modifier
                .padding(top = 20.dp),
            onClick = routeAction.goBack
        )

        Text(
            "회원가입 화면",
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        GameTextField(
            label = "이메일",
            value = emailInput.value,
            onValueChanged = {
                coroutineScope.launch {
                    authViewModel.registerEmailInputFlow.emit(it)
                }
            })


        GameTextField(
            label = "닉네임",
            value = nickname.value,
            onValueChanged = {
                coroutineScope.launch {
                    authViewModel.registerNicknameFlow.emit(it)
                }
            })


        GamePasswordTextField(
            label = "비밀번호",
            value = passwordInput.value,
            onValueChanged = {
                coroutineScope.launch {
                    authViewModel.registerPasswordInputFlow.emit(it)
                }
            })

        GamePasswordTextField(
            label = "비밀번호 확인",
            value = passwordConfirmInput.value,
            onValueChanged = {
                coroutineScope.launch {
                    authViewModel.registerPasswordConfirmInputFlow.emit(it)
                }
            })

        Spacer(modifier = Modifier.height(10.dp))

        BaseButton(
            title = "회원가입",
            enabled = isRegisterBtnActive,
            isLoading = isLoading.value,
            onClick = {
                Log.d("회원가입화면", "로그인 버튼 클릭")
                coroutineScope.launch {
                    authViewModel.registerUser()

                    gameViewModel.registerNickName()

               }

            })


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "이미 계정이 있으신가요?")
            TextButton(onClick = { routeAction.navTo(AuthRoute.LOGIN) }) {
                Text(text = "로그인 하러가기")

            }
        }
        Spacer(modifier = Modifier.weight(1f))
        SnackbarHost(hostState = snackBarHostState)
        Spacer(modifier = Modifier.height(30.dp))
    }
}


