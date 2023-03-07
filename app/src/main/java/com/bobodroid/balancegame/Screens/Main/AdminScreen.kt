package com.bobodroid.balancegame.Screens.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobodroid.balancegame.MainRoute
import com.bobodroid.balancegame.MainRouteAction
import com.bobodroid.balancegame.conponents.MyPageButtons
import com.bobodroid.balancegame.lists.AdminQuestionList
import com.bobodroid.balancegame.lists.GameSaveLists
import com.bobodroid.balancegame.lists.QuestionList
import com.bobodroid.balancegame.ui.theme.BackgroundColor
import com.bobodroid.balancegame.viewmodels.AuthViewModel
import com.bobodroid.balancegame.viewmodels.GameViewModel
import com.bobodroid.balancegame.viewmodels.HomeViewModel
import com.bobodroid.balancegame.viewmodels.MyPageViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun AdminScreen(routeAction: MainRouteAction, authViewModel: AuthViewModel, gameViewModel: GameViewModel, myPageViewModel: MyPageViewModel, homeViewModel: HomeViewModel){

    val postsListScrollSate = rememberLazyListState()

    val userEmail = Firebase.auth.currentUser.let { it?.email }

    val adminUser = if(userEmail == "kju9038@naver.com") "관리자" else ""

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
            Text(text = "$adminUser", color = Color.Blue)

            Spacer(modifier = Modifier.fillMaxWidth(0.1f))

            TextButton(onClick = {
                coroutineScope.launch {
                    Firebase.auth.signOut()
                    routeAction.navTo(MainRoute.Home)
                    authViewModel.isLoggedIn.emit(false)
                    homeViewModel.selectedCardId.emit(1)
                }

            }) {

                Text(text = "로그아웃")
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {

            Card {
                Text(text = "관리자 모드", fontSize = 25.sp)
            }

            Spacer(modifier = Modifier.width(20.dp))

            Card {
                Text(text = "질문리스트", fontSize = 25.sp)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(modifier = Modifier.weight(1f)
        ) {
            AdminQuestionList(gameViewModel)
            }
        }
    }
