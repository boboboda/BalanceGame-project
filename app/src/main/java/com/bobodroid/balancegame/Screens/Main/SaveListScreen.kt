package com.bobodroid.balancegame.Screens.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bobodroid.balancegame.MainRouteAction
import com.bobodroid.balancegame.conponents.BaseCard
import com.bobodroid.balancegame.lists.GameSaveLists
import com.bobodroid.balancegame.ui.theme.BackgroundColor
import com.bobodroid.balancegame.ui.theme.MyPageButtonColor
import com.bobodroid.balancegame.viewmodels.AuthViewModel
import com.bobodroid.balancegame.viewmodels.GameViewModel
import com.bobodroid.balancegame.viewmodels.HomeViewModel
import com.bobodroid.balancegame.viewmodels.MyPageViewModel

@Composable
fun SaveListScreen(routeAction: MainRouteAction,
                 authViewModel: AuthViewModel,
                 myPageViewModel: MyPageViewModel,
                 homeViewModel: HomeViewModel,
                 gameViewModel: GameViewModel){

    val postsListScrollSate = rememberLazyListState()

    val myPageChangeListButton = myPageViewModel.myPageChangeListButton.collectAsState()

    val userNickname = authViewModel.userNickname.collectAsState()

    val coroutineScope = rememberCoroutineScope()


    Column(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundColor),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {


            BaseCard(
                backgroundColor = MyPageButtonColor,
                fontColor = Color.DarkGray,
                modifier = Modifier.height(50.dp).width(200.dp).padding(start = 5.dp, end = 5.dp),
                text = "기기에 저장된 게임",
                fontSize = 20
            )

        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(modifier = Modifier.weight(1f)
        ) {
            GameSaveLists(gameViewModel)
            }
        }
    }
















