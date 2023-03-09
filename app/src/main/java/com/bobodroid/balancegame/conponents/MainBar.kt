package com.bobodroid.balancegame

import android.annotation.SuppressLint
import android.util.Log
import android.widget.EdgeEffect
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.magnifier
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bobodroid.balancegame.ui.theme.BottomColor
import com.bobodroid.balancegame.ui.theme.BottomSelectedColor
import com.bobodroid.balancegame.ui.theme.TopBarColor
import com.bobodroid.balancegame.viewmodels.AuthViewModel
import com.bobodroid.balancegame.viewmodels.GameViewModel
import com.bobodroid.balancegame.viewmodels.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainTopBar() {

    TopAppBar(backgroundColor = TopBarColor
    ) {
        Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center) {
        Text(text = "궁합 밸런스 게임",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center)
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun MainBottomBar(
    mainRouteAction: MainRouteAction,
    mainRouteBackStack: NavBackStackEntry?,
    gameViewModel: GameViewModel,
    homeViewModel: HomeViewModel,
    authViewModel: AuthViewModel
) {


    val selectedCardId = homeViewModel.selectedCardId.collectAsState()

    val isPlayGame = gameViewModel.isPlayGame.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()

    val user = Firebase.auth.currentUser

    val currentEmail = user?.let { it.email }

    Log.d(TAG, "${currentEmail}")

    val adminUser = if(currentEmail == "kju9038@naver.com") true else false

    val needAuth = authViewModel.needAuthContext.collectAsState()

    val isLoggedIn = authViewModel.isLoggedIn.collectAsState()



    BottomNavigation(
        modifier = Modifier.fillMaxWidth()
    ) {
        MainRoute.Home.let {
            var color = if (selectedCardId.value == it.selectValue) BottomSelectedColor else  BottomColor
            BottomNavigationItem(
                modifier = Modifier.background(color),
                label = { Text(text = it.title!!) },
                icon = {
                    it.iconResId?.let { iconId ->
                        Icon(painter = painterResource(iconId), contentDescription = it.title)
                    }
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black,
                selected = (mainRouteBackStack?.destination?.route) == it.routeName,
                onClick = {
                    if(isPlayGame.value == false)
                    { coroutineScope.launch {
                            snackBarHostState.showSnackbar("게임이 진행중입니다. 종료 후 시도하세요.",
                                actionLabel = "닫기", SnackbarDuration.Short)}
                        }
                    else
                    {mainRouteAction.navTo(it)
                    homeViewModel.selectedCardId.value = it.selectValue!!}
                },
            )
        }

            MainRoute.MyPage.let {
                var color = if (selectedCardId.value == it.selectValue) BottomSelectedColor else  BottomColor
                BottomNavigationItem(modifier = Modifier.background(color),
                    label = { Text(text = it.title!!)},
                    icon = {
                        it.iconResId?.let { iconId ->
                            Icon(painter = painterResource(iconId) , contentDescription =it.title)
                        }
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Black,
                    selected = (mainRouteBackStack?.destination?.route) == it.routeName,
                    onClick = {



                        if(isPlayGame.value == false)
                        {coroutineScope.launch {
                                snackBarHostState.showSnackbar("게임이 진행중입니다. 종료 후 시도하세요.",
                                    actionLabel = "닫기", SnackbarDuration.Short)
                        } }
                        else
                        {
                            coroutineScope.launch { authViewModel.needAuthContext.emit(true) }
                            if(isLoggedIn.value == false) return@BottomNavigationItem
                            if(adminUser)
                        {mainRouteAction.navTo(MainRoute.Admin)
                            homeViewModel.selectedCardId.value = it.selectValue!!
                        } else {
                            mainRouteAction.navTo(it)
                            homeViewModel.selectedCardId.value = it.selectValue!!}
                        }
                    },

                )
        }

        MainRoute.DoItYourSelf.let {
            var color = if (selectedCardId.value == it.selectValue) BottomSelectedColor else  BottomColor
            it.selectValue
            BottomNavigationItem(modifier = Modifier.background(color),
                label = { Text(text = it.title!!)},
                icon = {
                    it.iconResId?.let { iconId ->
                        Icon(painter = painterResource(iconId) , contentDescription =it.title)
                    }
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black,
                selected = (mainRouteBackStack?.destination?.route) == it.routeName,
                onClick = {
                    if(isPlayGame.value == false) {
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                "게임이 진행중입니다. 종료 후 시도하세요.",
                                actionLabel = "닫기", SnackbarDuration.Short
                            )
                        }
                    }
                    else
                    {
                        coroutineScope.launch { authViewModel.needAuthContext.emit(true) }
                        if(isLoggedIn.value == false) return@BottomNavigationItem
                        mainRouteAction.navTo(it)
                        homeViewModel.selectedCardId.value = it.selectValue!! } }
                )
        }

        SnackbarHost(hostState = snackBarHostState, modifier = Modifier)


    }
}


