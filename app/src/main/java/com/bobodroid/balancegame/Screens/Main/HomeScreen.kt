package com.bobodroid.balancegame.Screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobodroid.balancegame.*
import com.bobodroid.balancegame.conponents.Buttons
import com.bobodroid.balancegame.conponents.KindDialog
import com.bobodroid.balancegame.conponents.StartGameCodeInputDialog
import com.bobodroid.balancegame.conponents.dialogs.LoadingDataDialog
import com.bobodroid.balancegame.ui.theme.CompatibilityColor
import com.bobodroid.balancegame.ui.theme.MyPageSaveListColor
import com.bobodroid.balancegame.ui.theme.Purple200
import com.bobodroid.balancegame.viewmodels.GameViewModel
import font.fontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
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

    val loadOpenDialog = remember { mutableStateOf(false) }

    val successLoadFlow = gameViewModel.successLoadFlow.collectAsState()

    val gameItemEmptyCheck = gameViewModel.gameItemCheckFlow.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Purple200),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
       Row(modifier = Modifier
           .fillMaxHeight(0.1f)
           .wrapContentSize(),
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
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(modifier = Modifier
                .fillMaxWidth(0.8f)
                .clip(shape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp))
                .fillMaxHeight(0.38f),
                onClick = {
                    coroutineScope.launch {
                        gameViewModel.gameItemEmptyCheck()
                        loadOpenDialog.value = true
                        delay(1000)
                        gameViewModel.mainGameItemLoadSuccessFlow.collectLatest {
                            if(gameItemEmptyCheck.value == true) {
                            gameViewModel.singleGameState.value = false
                            gameViewModel.isPlayGame.value = false
                            routeAction.navTo(MainRoute.SingleGame)
                            } else {
                                Log.d(TAG, "초기게임값 불러오기 실패")
                                loadOpenDialog.value = false
                                snackBarHostState.showSnackbar(
                                    "게임불러오기 실패하였습니다. 다시 시도해주세요.",
                                    actionLabel = "닫기", SnackbarDuration.Short
                                )
                            }
                        }
                    }
                }
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(MyPageSaveListColor),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "시작",
                        fontSize = 70.sp,
                        fontFamily = fontFamily,
                        color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(modifier = Modifier
                .fillMaxWidth(0.8f)
                .clip(shape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp))
                .fillMaxHeight(0.7f),
                onClick = {
                    if(openCodeInputDialog.value == false) openCodeInputDialog.value = !openCodeInputDialog.value else null }
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(CompatibilityColor),
                    contentAlignment = Alignment.Center
                ){
                    Text(modifier = Modifier.padding(5.dp),
                        text = "궁합\n테스트",
                        fontSize = 70.sp,
                        fontFamily = fontFamily,
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
                onDismissRequest = {
                    openCodeInputDialog.value = it
                    gameViewModel.gameCode.value = ""
                                   },
                selected = {
                    coroutineScope.launch {
                        gameViewModel.fetchMatchItem()
                        delay(1000)
                        gameViewModel.successLoadFlow.collectLatest {
                            if (successLoadFlow.value) {
                                loadOpenDialog.value = false
                                if (receiveSuccess.value) {
                                    routeAction.navTo(MainRoute.CompatibilityGame)
                                    gameViewModel.startTogetherGame()
                                    Log.d(TAG, "성공")
                                } else {
                                    Log.d(TAG, "실패")
                                    snackBarHostState.showSnackbar(
                                        "게임코드가 맞지 않습니다 다시 입력해주세요.",
                                        actionLabel = "닫기", SnackbarDuration.Short
                                    )
                                }
                            }

                        }

                    }
                    openCodeInputDialog.value = false },
                startLoadDialog = {loadOpenDialog.value = it},
                gameViewModel = gameViewModel
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        SnackbarHost(hostState = snackBarHostState, modifier = Modifier)

        if(loadOpenDialog.value) {
            LoadingDataDialog()
        }

    }

}
