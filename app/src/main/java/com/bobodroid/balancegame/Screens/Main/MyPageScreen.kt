package com.bobodroid.balancegame.Screens.Main

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.bobodroid.balancegame.AuthNavHost
import com.bobodroid.balancegame.MainRoute
import com.bobodroid.balancegame.MainRouteAction
import com.bobodroid.balancegame.Routes.AuthRouteAction
import com.bobodroid.balancegame.conponents.Buttons
import com.bobodroid.balancegame.conponents.MyPageButtons
import com.bobodroid.balancegame.lists.GameSaveLists
import com.bobodroid.balancegame.lists.QuestionList
import com.bobodroid.balancegame.ui.theme.BackgroundColor
import com.bobodroid.balancegame.ui.theme.BottomSelectedColor
import com.bobodroid.balancegame.ui.theme.MyPageButtonColor
import com.bobodroid.balancegame.ui.theme.Purple200
import com.bobodroid.balancegame.viewmodels.AuthViewModel
import com.bobodroid.balancegame.viewmodels.GameViewModel
import com.bobodroid.balancegame.viewmodels.MyPageViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MyPageScreen(routeAction: MainRouteAction, authViewModel: AuthViewModel, gameViewModel: GameViewModel, myPageViewModel: MyPageViewModel){

    val postsListScrollSate = rememberLazyListState()

    val myPageChangeListButton = myPageViewModel.myPageChangeListButton.collectAsState()

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
            Text(text = "구현중", color = Color.Blue)

            Spacer(modifier = Modifier.fillMaxWidth(0.1f))

            TextButton(onClick = {

                coroutineScope.launch {
                    authViewModel.isLoggedIn.emit(false)
                }

            }) {

                Text(text = "로그아웃")
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            MyPageButtons(
                label = "질문리스트",
                id = 1,
                selectedId = myPageChangeListButton.value,
                onClicked = { myPageViewModel.myPageChangeListButton.value = it },
                fontColor = Color.DarkGray,
                modifier = Modifier
                    .height(50.dp),
                fontSize = 20
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))

            MyPageButtons(
                label = "저장된 게임",
                id = 2,
                selectedId = myPageChangeListButton.value,
                onClicked = { myPageViewModel.myPageChangeListButton.value = it },
                fontColor = Color.DarkGray,
                modifier = Modifier
                    .height(50.dp),
                fontSize = 20
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(modifier = Modifier.weight(1f)
        ) {
            when(myPageChangeListButton.value) {
                1-> QuestionList()
                2-> GameSaveLists(gameViewModel)

            }
        }
    }
}















