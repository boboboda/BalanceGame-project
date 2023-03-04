package com.bobodroid.balancegame.Screens.Auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.bobodroid.balancegame.Routes.AuthRouteAction
import com.bobodroid.balancegame.ui.theme.Purple200

@Composable
fun PasswordFindScreen(routeAction: AuthRouteAction){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Purple200)
    ) {
        Text(text = "비밀번호 찾기",
            fontSize = 50.sp)
    }
}