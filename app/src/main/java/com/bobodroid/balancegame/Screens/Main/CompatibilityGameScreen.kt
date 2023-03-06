package com.bobodroid.balancegame.Screens.Main

import android.util.Log
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
import com.bobodroid.balancegame.TAG
import com.bobodroid.balancegame.conponents.*
import com.bobodroid.balancegame.ui.theme.BottomSelectedColor
import com.bobodroid.balancegame.ui.theme.Purple200
import com.bobodroid.balancegame.viewmodels.GameViewModel

@Composable
fun CompatibilityGameScreen(routeAction: MainRouteAction, gameViewModel: GameViewModel){

    val gameStage = gameViewModel.togetherGameStage.collectAsState()

    val usedGameItem = gameViewModel.currentTogetherGameItem.collectAsState()

    val openDialog = remember { mutableStateOf(false) }

    val testList = gameViewModel.togetherGameItem.collectAsState()

    val saveGameList = gameViewModel

    val test = gameViewModel.compatibilityFlow.collectAsState()


    Log.d(TAG, "${test.value}")
    Log.d(TAG, "${usedGameItem.value}")





    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple200),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TogetherGameListView(
            firstText = usedGameItem.value.firstItem!!,
            secondText = usedGameItem.value.secondItem!!,
            firstOnClicked = {
                if(gameStage.value == 10) openDialog.value = true else openDialog.value = false
                gameViewModel.compatibilityListNumber.value += 1
                gameViewModel.firstUserSelect.value = usedGameItem.value.selectItem!!
                gameViewModel.secondUserSelect.value = 1
                gameViewModel.actionSelect()
                gameViewModel.startTogetherGame()
                gameViewModel.togetherMoveToNextStage()

                             },
            secondClicked = {
                if(gameStage.value == 10) openDialog.value = true else openDialog.value = false
                gameViewModel.compatibilityListNumber.value += 1
                gameViewModel.firstUserSelect.value = usedGameItem.value.selectItem!!
                gameViewModel.secondUserSelect.value = 2
                gameViewModel.actionSelect()
                gameViewModel.startTogetherGame()
                gameViewModel.togetherMoveToNextStage()

                            },
            gameState = gameStage.value,
            kind = "${usedGameItem.value.itemKind!!.kindName}"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Buttons(
                label = "제작자: ${usedGameItem.value.makerName}",
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
                    gameViewModel.togetherGameItemReset()
                    routeAction.navTo(MainRoute.Home) },
                color = BottomSelectedColor,
                fontColor = Color.DarkGray,
                modifier = Modifier
                    .height(50.dp),
                fontSize = 20
            )
        }

        if(openDialog.value) {
            CompatibilityViewDialog(onDismissRequest = { openDialog.value = it },
                selected = {
                    gameViewModel.togetherGameItemReset()
                    routeAction.navTo(MainRoute.CompatibilityResult)
                }
            )
        }
    }

}
dddddd