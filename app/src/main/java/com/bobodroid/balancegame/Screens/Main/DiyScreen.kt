package com.bobodroid.balancegame.Screens.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.bobodroid.balancegame.AuthNavHost
import com.bobodroid.balancegame.MainRoute
import com.bobodroid.balancegame.MainRouteAction
import com.bobodroid.balancegame.Routes.AuthRouteAction
import com.bobodroid.balancegame.conponents.BaseCard
import com.bobodroid.balancegame.lists.GameSaveLists
import com.bobodroid.balancegame.lists.QuestionList
import com.bobodroid.balancegame.ui.theme.BackgroundColor
import com.bobodroid.balancegame.ui.theme.MyPageButtonColor
import com.bobodroid.balancegame.ui.theme.Purple200
import com.bobodroid.balancegame.viewmodels.AuthViewModel
import com.bobodroid.balancegame.viewmodels.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun DiyScreen(
    mainRouteAction: MainRouteAction,
    authRouteAction: AuthRouteAction,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
){

    val postsListScrollSate = rememberLazyListState()

    val userNickname = authViewModel.userNickname.collectAsState()

    val coroutineScope = rememberCoroutineScope()


    Column(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundColor),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {

            Text(text = "사용자:")
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "${userNickname.value}", color = Color.Blue)

            Spacer(modifier = Modifier.fillMaxWidth(0.1f))

            TextButton(onClick = {
                coroutineScope.launch {
                    authViewModel.successLogin.emit(false)
                }
                coroutineScope.launch {
                    mainRouteAction.navTo(MainRoute.Home)
                    authViewModel.isLoggedIn.emit(false)
                    homeViewModel.selectedCardId.emit(1)
                }
                Firebase.auth.signOut()

            }) {

                Text(text = "로그아웃")
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {


            BaseCard(
                backgroundColor = MyPageButtonColor,
                fontColor = Color.DarkGray,
                modifier = Modifier
                    .height(50.dp)
                    .width(100.dp),
                text = "질문 리스트",
                fontSize = 20
            )

        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(modifier = Modifier.weight(1f)
        ) {
            QuestionList()
        }
    }
}
