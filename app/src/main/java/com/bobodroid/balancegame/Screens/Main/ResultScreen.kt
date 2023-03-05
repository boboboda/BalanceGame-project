package com.bobodroid.balancegame.Screens.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobodroid.balancegame.MainRouteAction
import com.bobodroid.balancegame.conponents.CompatibilityArray
import com.bobodroid.balancegame.conponents.GameCodeDialog
import com.bobodroid.balancegame.conponents.MyPageButtons
import com.bobodroid.balancegame.lists.GameSaveLists
import com.bobodroid.balancegame.lists.MypageList
import com.bobodroid.balancegame.lists.QuestionList
import com.bobodroid.balancegame.ui.theme.*
import com.bobodroid.balancegame.viewmodels.AuthViewModel
import com.bobodroid.balancegame.viewmodels.GameViewModel
import com.bobodroid.balancegame.viewmodels.MyPageViewModel
import kotlinx.coroutines.launch

@Composable
fun ResultScreen(routeAction: MainRouteAction, gameViewModel: GameViewModel){

    val postsListScrollSate = rememberLazyListState()

    val compatibility = gameViewModel.compatibilityFlow.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundColor),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(10.dp))



        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(50.dp)
                    .padding(start = 20.dp),
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(StageColor),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = "궁합", fontSize = 25.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
                }
            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(modifier = Modifier.weight(1f)
        ) {
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .height(450.dp)) {

                items(compatibility.value, { item -> item.listNumber }) { list ->

                    CompatibilityArray(
                        list = list.listNumber.toString(),
                        firstItem = list.firstUserSelect,
                        secondItem = list.SecondUserSelect,
                        resultColor = list.selectResult,
                        resultText = if(list.selectResult) "천생연분" else "우린 달라")

                    }
                }

            Column(modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .background(MyPageSaveListColor)
                    .align(Alignment.CenterHorizontally)
                ) {
                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "")
                    }

                }
            }


            }
        }
    }
