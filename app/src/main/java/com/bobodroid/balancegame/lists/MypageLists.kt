package com.bobodroid.balancegame.lists

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobodroid.balancegame.TAG
import com.bobodroid.balancegame.conponents.Buttons
import com.bobodroid.balancegame.conponents.GameCodeDialog
import com.bobodroid.balancegame.conponents.MakeQuestionDialog
import com.bobodroid.balancegame.ui.theme.GameCordListColor
import com.bobodroid.balancegame.ui.theme.MyPageButtonColor
import com.bobodroid.balancegame.ui.theme.MyPageSaveListColor
import com.bobodroid.balancegame.viewmodels.GameViewModel

@Composable
fun QuestionList(){


    Column(modifier = Modifier.fillMaxSize()) {
        MypageList(
            saveName = "질문",
            itemCode = "공개여부",
            secondCardBackgroundColor = Color.White,
            thirdCardBackgroundColor = Color.White,
            gameCodeClicked = null
        )
        Spacer(modifier = Modifier.height(2.dp))



    }




}








@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameSaveLists(gameViewModel: GameViewModel) {

    val saveGameItemList = gameViewModel.userSaveGameItem.collectAsState()

    val openDialog = remember { mutableStateOf(false) }

    val GameCodeView = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        MypageList(
            saveName = "저장된 게임 제목",
            itemCode = "게임코드",
            secondCardBackgroundColor = Color.White,
            thirdCardBackgroundColor = Color.White,
            gameCodeClicked = null
        )
        Spacer(modifier = Modifier.height(2.dp))


        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)) {

            items(saveGameItemList.value, { item -> item.GameCode }) { list ->

                MypageList(
                    saveName = list.saveName,
                    itemCode = list.GameCode,
                    secondCardBackgroundColor = MyPageSaveListColor,
                    thirdCardBackgroundColor = GameCordListColor,
                    gameCodeClicked = {
                        openDialog.value = !openDialog.value
                        GameCodeView.value = list.GameCode
                    }
                )
                if (openDialog.value) {
                    GameCodeDialog(
                        onDismissRequest = {openDialog.value = it},
                        gameCode = GameCodeView.value,
                        context = LocalContext.current,
                        selectedCopy = {openDialog.value = it})
                }
            }
        }



    }
}




@Composable
fun AdminQuestionList(gameViewModel: GameViewModel){


    val openDialog = remember { mutableStateOf(false) }

    val allGameItemList = gameViewModel.gameItemFlow.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        MypageList(
            saveName = "질문",
            itemCode = "제작자",
            secondCardBackgroundColor = Color.LightGray,
            thirdCardBackgroundColor = Color.LightGray,
            gameCodeClicked = null
        )
        Spacer(modifier = Modifier.height(2.dp))


        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)) {

            items(allGameItemList.value, { item -> item.id!! }) { list ->
                MypageList(
                    saveName = "${list.firstItem}/ ${list.secondItem} ",
                    itemCode = list.makerName.toString(),
                    secondCardBackgroundColor = MyPageSaveListColor,
                    thirdCardBackgroundColor = GameCordListColor,
                    gameCodeClicked = {  }
                )

                }
            }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Buttons(
                label = "질문 만들기",
                onClicked = {
                    openDialog.value = true
                },
                color = MyPageButtonColor,
                fontColor = Color.Black,
                modifier = Modifier,
                fontSize = 25
            )
        }

        if (openDialog.value) {
            MakeQuestionDialog(
                onDismissRequest = {openDialog.value = it},
                selected = {
                    gameViewModel.makeGameItem()
                    openDialog.value = false
                },
                closeSelected = { openDialog.value = false},
                gameViewModel = gameViewModel
            )
        }

        }

    }











@Composable
fun MypageList(
    saveName: String,
    itemCode: String,
    secondCardBackgroundColor: Color,
    thirdCardBackgroundColor: Color,
    gameCodeClicked:(() -> Unit)?){

        Row(modifier = Modifier.padding(2.dp)) {

            Spacer(modifier = Modifier.width(0.5.dp))

            Card(modifier = Modifier
                .border(
                    border = BorderStroke(1.dp, color = Color.Black),
                    shape = RoundedCornerShape(3.dp)
                )
                .weight(1f),
                backgroundColor = secondCardBackgroundColor
            ) {
                Row(modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                    Text(text = saveName)
                }

            }

            Spacer(modifier = Modifier.width(0.5.dp))

            Card(modifier = Modifier
                .border(
                    border = BorderStroke(1.dp, color = Color.Black),
                    shape = RoundedCornerShape(3.dp)
                )
                .wrapContentSize(),
                backgroundColor = thirdCardBackgroundColor
            ) {
                Row(modifier = Modifier
                    .height(50.dp)
                    .width(100.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {

                    ClickableText(text = AnnotatedString(itemCode),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        onClick = { gameCodeClicked?.invoke() }  )
                }
            }
        }
}