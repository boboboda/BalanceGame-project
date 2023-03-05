package com.bobodroid.balancegame.Screens.Main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bobodroid.balancegame.MainRoute
import com.bobodroid.balancegame.MainRouteAction
import com.bobodroid.balancegame.conponents.Buttons
import com.bobodroid.balancegame.conponents.SaveQuestionDialog
import com.bobodroid.balancegame.conponents.SingleGameListView
import com.bobodroid.balancegame.ui.theme.BottomSelectedColor
import com.bobodroid.balancegame.ui.theme.Purple200
import com.bobodroid.balancegame.viewmodels.GameViewModel


@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@Composable
fun SingleGameScreen(routeAction: MainRouteAction, gameViewModel: GameViewModel) {


    val gameStage = gameViewModel.singleGameStage.collectAsState()

    val currentGameItem = gameViewModel.currentRandomGameItem.collectAsState()

    val openDialog = remember { mutableStateOf(false) }





    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple200),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            SingleGameListView(
                firstText = currentGameItem.value.firstItem!!,
                secondText = currentGameItem.value.secondItem!!,
                firstOnClicked = {
                    gameViewModel.stageResultValue.value = 1
                    gameViewModel.usedGameItem(currentGameItem.value)
                    if(gameStage.value == 10) {openDialog.value = true
                    }
                    else
                    {openDialog.value = false
                        gameViewModel.moveToNextStage()
                    } },

                secondClicked = {
                    gameViewModel.stageResultValue.value = 2
                    gameViewModel.usedGameItem(currentGameItem.value)
                    if(gameStage.value == 10) {openDialog.value = true
                    }
                    else
                    {openDialog.value = false
                        gameViewModel.moveToNextStage()
                    } },
                gameState = gameStage.value,
                kind = "${currentGameItem.value.itemKind!!.kindName}"
            )

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Buttons(
                label = "제작자: ${currentGameItem.value.makerName}",
                onClicked = { },
                color = BottomSelectedColor,
                fontColor = Color.DarkGray,
                modifier = Modifier
                    .height(50.dp),
                fontSize = 20
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.3f))

            Buttons(
                label = "그만두기",
                onClicked = {
                    gameViewModel.isPlayGame.value = true
                    gameViewModel.gameItemReset()
                    routeAction.navTo(MainRoute.Home) },
                color = BottomSelectedColor,
                fontColor = Color.DarkGray,
                modifier = Modifier
                    .height(50.dp),
                fontSize = 20
            )
        }

        if(openDialog.value) {
            SaveQuestionDialog(
                onDismissRequest = {openDialog.value = it},
                saveSelected = {
                    gameViewModel.listNumber.value += 1
                    gameViewModel.saveGameItem()
                    gameViewModel.singleGameState.value = true
                    gameViewModel.gameItemReset()
                    gameViewModel.isPlayGame.value = true
                    routeAction.navTo(MainRoute.Home)
                               },
                closeSelected = {
                    gameViewModel.singleGameState.value = true
                    gameViewModel.gameItemReset()
                    gameViewModel.isPlayGame.value = true
                    routeAction.navTo(MainRoute.Home) },
                gameViewModel
            )
        }
    }

}






