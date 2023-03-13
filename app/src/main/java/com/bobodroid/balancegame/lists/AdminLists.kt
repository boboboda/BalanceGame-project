package com.bobodroid.balancegame.lists

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bobodroid.balancegame.conponents.Buttons
import com.bobodroid.balancegame.conponents.dialogs.AdminControllerQuestionDialog
import com.bobodroid.balancegame.conponents.dialogs.AdminMakeQuestionDialog
import com.bobodroid.balancegame.conponents.dialogs.MakeMatchTextItemDialog
import com.bobodroid.balancegame.conponents.dialogs.MatchTextItemDialog
import com.bobodroid.balancegame.ui.theme.GameCordListColor
import com.bobodroid.balancegame.ui.theme.MyPageButtonColor
import com.bobodroid.balancegame.ui.theme.MyPageSaveListColor
import com.bobodroid.balancegame.viewmodels.GameViewModel
import com.bobodroid.balancegame.viewmodels.dataViewModels.Compatibility
import com.bobodroid.balancegame.viewmodels.dataViewModels.GameItem
import kotlinx.coroutines.launch

@Composable
fun AdminQuestionLists(gameViewModel: GameViewModel){


    val openDialog = remember { mutableStateOf(false) }

    val allGameItemList = gameViewModel.allGameItemFlow.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val questionAdminDialog = remember { mutableStateOf(false) }



    Column(modifier = Modifier.fillMaxSize()) {
       AdminQuestionListTopView(
           firstText = "질문",
           secondText = "제작자",
           thirdText = "공개여부",
           secondCardBackgroundColor = Color.LightGray,
           thirdCardBackgroundColor = Color.LightGray
        )
        Spacer(modifier = Modifier.height(2.dp))


        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)) {

            items(allGameItemList.value, { item -> item.id!! }) { list ->
               AdminQuestionList(
                   data = list,
                   firstText = "${list.firstItem}/ ${list.secondItem} ",
                   secondText = list.makerName.toString(),
                   thirdText = if(list.private) "공개" else "비공개",
                   secondCardBackgroundColor = MyPageSaveListColor,
                   thirdCardBackgroundColor = GameCordListColor,
                   gameViewModel)



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
            AdminMakeQuestionDialog(
                onDismissRequest = {openDialog.value = it},
                selected = {
                    coroutineScope.launch {
                        gameViewModel.privateFlow.emit(true)
                    }
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
fun AdminSaveCompatibilityList(gameViewModel: GameViewModel){


    val openDialog = remember { mutableStateOf(false) }

    val openSaveItemDialog = remember {
        mutableStateOf(false)
    }

    val rememberList = remember {
        mutableStateOf(Compatibility())
    }

    val allMatchTextItem = gameViewModel.matchTextItem.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        MypageList(
            firstText = "제목",
            secondText = "id",
            secondCardBackgroundColor = Color.LightGray,
            thirdCardBackgroundColor = Color.LightGray,
            gameCodeClicked = null
        )
        Spacer(modifier = Modifier.height(2.dp))


        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)) {

            items(allMatchTextItem.value, { item -> item.id!! }) { list ->
                MypageList(
                    firstText = "${list.compatibilitySaveName} ",
                    secondText = list.id,
                    secondCardBackgroundColor = MyPageSaveListColor,
                    thirdCardBackgroundColor = GameCordListColor,
                    gameCodeClicked = {
                        rememberList.value = list
                        openSaveItemDialog.value = true }
                )



            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Buttons(
                label = "궁합 문구 만들기",
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
            MakeMatchTextItemDialog(
                onDismissRequest = {openDialog.value = it},
                selected = { gameViewModel.saveCompatibility()},
                closeSelected = { openDialog.value = false },
                gameViewModel = gameViewModel
            )
        }

        if(openSaveItemDialog.value)
        {
            MatchTextItemDialog(
                onDismissRequest = { openSaveItemDialog.value = it },
                data = rememberList.value
            ) { openSaveItemDialog.value = false }

        }

    }

}


@Composable
fun AdminQuestionListTopView(
    firstText: String,
    secondText: String,
    thirdText: String,
    secondCardBackgroundColor: Color,
    thirdCardBackgroundColor: Color){



        Row(modifier = Modifier.padding(2.dp)) {

            Spacer(modifier = Modifier.width(0.5.dp))

            Card(
                modifier = Modifier
                    .border(
                        border = BorderStroke(1.dp, color = Color.Black),
                        shape = RoundedCornerShape(3.dp)
                    )
                    .weight(1f),
                backgroundColor = secondCardBackgroundColor
            ) {
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = firstText)
                }

            }

            Spacer(modifier = Modifier.width(0.5.dp))

            Card(
                modifier = Modifier
                    .border(
                        border = BorderStroke(1.dp, color = Color.Black),
                        shape = RoundedCornerShape(3.dp)
                    )
                    .wrapContentSize(),
                backgroundColor = thirdCardBackgroundColor
            ) {
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .width(80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = AnnotatedString(secondText),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }

            Spacer(modifier = Modifier.width(0.5.dp))

            Card(
                modifier = Modifier
                    .border(
                        border = BorderStroke(1.dp, color = Color.Black),
                        shape = RoundedCornerShape(3.dp)
                    )
                    .wrapContentSize(),
                backgroundColor = thirdCardBackgroundColor
            ) {
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .width(80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = AnnotatedString(thirdText),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
        }
    }


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AdminQuestionList(
    data: GameItem,
    firstText: String,
    secondText: String,
    thirdText: String,
    secondCardBackgroundColor: Color,
    thirdCardBackgroundColor: Color,
    gameViewModel: GameViewModel){

    val questionAdminDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .height(50.dp)
            .padding(2.dp),
        shape = RoundedCornerShape(0.dp),
        onClick = { questionAdminDialog.value = true},
        ) {
        Row() {
            Card(
                modifier = Modifier
                    .border(
                        border = BorderStroke(1.dp, color = Color.Black),
                        shape = RoundedCornerShape(3.dp)
                    )
                    .weight(1f),
                backgroundColor = secondCardBackgroundColor
            ) {
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = firstText)
                }

            }

            Spacer(modifier = Modifier.width(0.5.dp))

            Card(
                modifier = Modifier
                    .border(
                        border = BorderStroke(1.dp, color = Color.Black),
                        shape = RoundedCornerShape(3.dp)
                    )
                    .wrapContentSize(),
                backgroundColor = thirdCardBackgroundColor
            ) {
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .width(80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = AnnotatedString(secondText),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }

            Spacer(modifier = Modifier.width(0.5.dp))

            Card(
                modifier = Modifier
                    .border(
                        border = BorderStroke(1.dp, color = Color.Black),
                        shape = RoundedCornerShape(3.dp)
                    )
                    .wrapContentSize(),
                backgroundColor = thirdCardBackgroundColor
            ) {
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .width(80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = AnnotatedString(thirdText),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
        }
    }
    if(questionAdminDialog.value){
        AdminControllerQuestionDialog(
            data = data,
            delete = {   },
            privateOpen = { gameViewModel.editPrivateGameItem(data, true) },
            privateSecret = {   },
            onDismissRequest = {questionAdminDialog.value = it}
        )
    }
}