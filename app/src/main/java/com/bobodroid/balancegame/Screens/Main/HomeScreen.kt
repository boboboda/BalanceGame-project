package com.bobodroid.balancegame.Screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobodroid.balancegame.*
import com.bobodroid.balancegame.conponents.Buttons
import com.bobodroid.balancegame.conponents.KindDialog
import com.bobodroid.balancegame.conponents.StartGameCodeInputDialog
import com.bobodroid.balancegame.ui.theme.Purple200
import com.bobodroid.balancegame.viewmodels.GameViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(routeAction: MainRouteAction, gameViewModel: GameViewModel){

    val kindButtonValue = gameViewModel.selectedKindItem.collectAsState()

    val isSingleGameButton by remember { mutableStateOf(false) }

    var openDialog = remember { mutableStateOf(false) }

    var openCodeInputDialog = remember{ mutableStateOf(false) }


    val snackBarHostState = remember { SnackbarHostState() }

    val receiveSuccess = gameViewModel.success.collectAsState(initial = false)

    val coroutineScope = rememberCoroutineScope()









    Column(modifier = Modifier
        .fillMaxSize()
        .background(Purple200),

    ) {

        Spacer(modifier = Modifier.height(40.dp))
       Row(modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.Center) {
           Buttons(
               label = "주제: ${kindButtonValue.value.kindName}",
               onClicked = {
                   gameViewModel.gameItemReset()
                   if(openDialog.value == false) openDialog.value = !openDialog.value else null },
               color = Color.Magenta,
               fontColor = Color.White,
               modifier = Modifier,
               fontSize = 25
           )
       }
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(200.dp),
                onClick = {
                    gameViewModel.singleGameState.value = false
                    gameViewModel.isPlayGame.value = false
                    routeAction.navTo(MainRoute.SingleGame) }
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "시작",
                        fontSize = 80.sp,
                        color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(60.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(200.dp),
                onClick = {
                    if(openCodeInputDialog.value == false) openCodeInputDialog.value = !openCodeInputDialog.value else null }
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ){
                    Text(modifier = Modifier.padding(5.dp),
                        text = "궁합\n테스트",
                        fontSize = 80.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center)
                }
            }
        }

        if (openDialog.value) {
            KindDialog(
                onDismissRequest = {openDialog.value = it} ,
                selected = {
                    gameViewModel.selectedKindItem.value = it
                           }
            )
        }

        if(openCodeInputDialog.value) {
            StartGameCodeInputDialog(
                onDismissRequest = {openCodeInputDialog.value = it
                                   gameViewModel.gameCode.value = ""
                                   },
                selected = {
                    if(!receiveSuccess.value) {
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                "게임코드가 맞지 않습니다 다시입력해 주세요.",
                                actionLabel = "닫기", SnackbarDuration.Short
                            )
                        }
                    openCodeInputDialog.value = false
                    } else
                    {
                        routeAction.navTo(MainRoute.CompatibilityGame)
                        gameViewModel.startTogetherGame()
                    } },
                gameViewModel = gameViewModel,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        SnackbarHost(hostState = snackBarHostState, modifier = Modifier)

    }

}
