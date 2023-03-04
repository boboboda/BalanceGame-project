package com.bobodroid.balancegame.Screens.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.bobodroid.balancegame.AuthNavHost
import com.bobodroid.balancegame.MainRouteAction
import com.bobodroid.balancegame.Routes.AuthRouteAction
import com.bobodroid.balancegame.ui.theme.Purple200
import com.bobodroid.balancegame.viewmodels.AuthViewModel

@Composable
fun DiyScreen(
    mainRouteAction: MainRouteAction,
    authRouteAction: AuthRouteAction,
    authViewModel: AuthViewModel
){

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Purple200),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "DIY",
            fontSize = 50.sp)
    }}