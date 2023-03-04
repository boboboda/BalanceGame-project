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
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import com.bobodroid.balancegame.conponents.GameCodeDialog
import com.bobodroid.balancegame.ui.theme.GameCordListColor
import com.bobodroid.balancegame.ui.theme.MyPageButtonColor
import com.bobodroid.balancegame.ui.theme.MyPageSaveListColor
import com.bobodroid.balancegame.viewmodels.GameViewModel

@Composable
fun QuestionList(){


    Column(modifier = Modifier.fillMaxSize()) {
        MypageList(
            listNumber = "리스트순서",
            saveName = "질문",
            itemCode = "공개여부",
            firstCardBackgroundColor = Color.White,
            secondCardBackgroundColor = Color.White,
            thirdCardBackgroundColor = Color.White,
            gameCodeClicked = null
        )
        Spacer(modifier = Modifier.height(2.dp))



    }




}








@Composable
fun GameSaveLists(gameViewModel: GameViewModel) {

    val saveGameItemList = gameViewModel.userSaveGameItem.collectAsState()

    val openDialog = remember { mutableStateOf(false) }



    Column(modifier = Modifier.fillMaxSize()) {
        MypageList(
            listNumber = "리스트순서",
            saveName = "저장된 게임 제목",
            itemCode = "게임코드",
            firstCardBackgroundColor = Color.White,
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
                    listNumber = list.listNumber.toString(),
                    saveName = list.saveName,
                    itemCode = list.GameCode.toString(),
                    firstCardBackgroundColor = MyPageButtonColor,
                    secondCardBackgroundColor = MyPageSaveListColor,
                    thirdCardBackgroundColor = GameCordListColor,
                    gameCodeClicked = {
                        openDialog.value = !openDialog.value
                    }
                )
                if (openDialog.value) {
                    GameCodeDialog(
                        onDismissRequest = {openDialog.value = it},
                        gameCode = " ${list.GameCode.toString()}",
                        context = LocalContext.current,
                        selectedCopy = {openDialog.value = it})
                }
            }
        }



    }
}







@Composable
fun MypageList(listNumber: String,
             saveName: String,
             itemCode: String,
firstCardBackgroundColor: Color,
secondCardBackgroundColor: Color,
thirdCardBackgroundColor: Color,
gameCodeClicked:(() -> Unit)?){

        Row(modifier = Modifier.padding(2.dp)) {
            Card(modifier = Modifier
                .border(
                    border = BorderStroke(1.dp, color = Color.Black),
                    shape = RoundedCornerShape(3.dp)
                )
                .background(color = firstCardBackgroundColor)
                .wrapContentSize()
            ) {
                Row(modifier = Modifier
                    .height(50.dp)
                    .width(100.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Text(text = listNumber)
                }
            }

            Spacer(modifier = Modifier.width(0.5.dp))

            Card(modifier = Modifier
                .border(
                    border = BorderStroke(1.dp, color = Color.Black),
                    shape = RoundedCornerShape(3.dp)
                )
                .background(color = secondCardBackgroundColor)
                .weight(1f)
            ) {
                Row(modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                    Text(text = saveName)
                }

            }

            Card(modifier = Modifier
                .border(
                    border = BorderStroke(1.dp, color = Color.Black),
                    shape = RoundedCornerShape(3.dp)
                )
                .background(color = thirdCardBackgroundColor)
                .wrapContentSize()
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