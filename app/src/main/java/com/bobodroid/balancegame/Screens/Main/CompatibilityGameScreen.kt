package com.bobodroid.balancegame.Screens.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobodroid.balancegame.MainRoute
import com.bobodroid.balancegame.MainRouteAction
import com.bobodroid.balancegame.conponents.Buttons
import com.bobodroid.balancegame.conponents.GameListView
import com.bobodroid.balancegame.conponents.SaveQuestionDialog
import com.bobodroid.balancegame.ui.theme.BottomSelectedColor
import com.bobodroid.balancegame.ui.theme.Purple200
import com.bobodroid.balancegame.viewmodels.GameViewModel

@Composable
fun CompatibilityGameScreen(routeAction: MainRouteAction, gameViewModel: GameViewModel){

//    val gameStage = gameViewModel.gameStage.collectAsState()
//
//    val usedGameItem = gameViewModel.usedGameItem.collectAsState()
//
//    val openDialog = remember { mutableStateOf(false) }
//
//
//
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Purple200),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        GameListView(
//            firstText = usedGameItem.value.firstItem,
//            secondText = usedGameItem.value.secondItem,
//            firstOnClicked = {
//                if(gameStage.value == 10) openDialog.value = true else openDialog.value = false
//                if(gameStage.value == 10) return@GameListView
//                gameViewModel.moveToNextStage() },
//            secondClicked = {
//                if(gameStage.value == 10) openDialog.value = true else openDialog.value = false
//                if(gameStage.value == 10) return@GameListView
//                gameViewModel.moveToNextStage() },
//            gameState = gameStage.value
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Row(modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center) {
//            Buttons(
//                label = "제작자: ${usedGameItem.value.makerName}",
//                onClicked = { },
//                color = BottomSelectedColor,
//                fontColor = Color.DarkGray,
//                modifier = Modifier
//                    .height(50.dp),
//                fontSize = 20
//            )
//            Spacer(modifier = Modifier.fillMaxWidth(0.3f))
//
//            Buttons(
//                label = "그만두기",
//                onClicked = {
//                    gameViewModel.isPlayGame.value = true
//                    gameViewModel.gameItemReset()
//                    routeAction.navTo(MainRoute.Home) },
//                color = BottomSelectedColor,
//                fontColor = Color.DarkGray,
//                modifier = Modifier
//                    .height(50.dp),
//                fontSize = 20
//            )
//        }
//
//        if(openDialog.value) {
//            SaveQuestionDialog(
//                onDismissRequest = {openDialog.value = it},
//                saveSelected = {
//                    gameViewModel.gameState.value = true
//                    gameViewModel.saveGameItem()
//                    gameViewModel.gameItemReset()
//                    gameViewModel.isPlayGame.value = true
//                    routeAction.navTo(MainRoute.Home)
//                },
//                closeSelected = {
//                    gameViewModel.gameState.value = true
//                    gameViewModel.gameItemReset()
//                    gameViewModel.isPlayGame.value = true
//                    routeAction.navTo(MainRoute.Home) },
//                gameViewModel
//            )
//        }
//    }

}
