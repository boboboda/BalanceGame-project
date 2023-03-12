package com.bobodroid.balancegame.conponents

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bobodroid.balancegame.TAG
import com.bobodroid.balancegame.ui.theme.MyPageButtonColor
import com.bobodroid.balancegame.ui.theme.MyPageSaveListColor
import com.bobodroid.balancegame.ui.theme.Teal200
import com.bobodroid.balancegame.viewmodels.GameViewModel
import com.bobodroid.balancegame.viewmodels.dataViewModels.ItemKind
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SaveQuestionDialog(
    onDismissRequest: (Boolean)->Unit,
    saveSelected: ()-> Unit,
    closeSelected: ()->Unit,
    gameViewModel: GameViewModel

) {

    val saveInput = gameViewModel.saveName.collectAsState()

    Dialog(
        onDismissRequest = { onDismissRequest(false) },
        properties = DialogProperties()
    ) {
        Column(modifier = Modifier
            .wrapContentSize()
            .padding(10.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.padding(10.dp)) {
                        Text(text = "게임을 저장하여 다른 사람과 궁합을 보시겠습니까?")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        label = { Text(text = "저장할 게임 제목을 입력해주세요")},
                        value = saveInput.value,
                        onValueChange = { Input ->
                            gameViewModel.saveName.value = Input
                        },
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(25.dp))

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                        Buttons(
                            label = "예",
                            onClicked = saveSelected,
                            color = Teal200,
                            fontColor = Color.Black,
                            modifier = Modifier
                                .height(40.dp)
                                .width(80.dp),
                            fontSize = 15
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                        Buttons(
                            label = "닫기",
                            onClicked = closeSelected,
                            color = Teal200,
                            fontColor = Color.Black,
                            modifier = Modifier
                                .height(40.dp)
                                .width(80.dp),
                            fontSize = 15
                        )
                    }



        }
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun KindDialog(
    onDismissRequest: (Boolean)->Unit,
    selected: (ItemKind)-> Unit

) {


    val kindList = listOf(
        ItemKind.FOOD,ItemKind.TRAVEL,
        ItemKind.LOVE,ItemKind.ETC,
    )

    Dialog(
        onDismissRequest = { onDismissRequest(false) },
        properties = DialogProperties()
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(
                2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(kindList) { akindList ->
                        ActionButton(
                            action = akindList,
                            onClicked = {
                                selected(akindList)
                                onDismissRequest(false)
                            })
                    }

                })

        }
    }
}


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun GameCodeDialog(
    onDismissRequest: (Boolean)->Unit,
    gameCode: String,
    selectedCopy: (Boolean) -> Unit,
    context: Context) {

    Dialog(
        onDismissRequest = { onDismissRequest(false) },
        properties = DialogProperties()
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .height(230.dp)
                .padding(15.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "게임코드")

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = gameCode, modifier = Modifier.padding(start = 30.dp, end = 30.dp))

            Spacer(modifier = Modifier.height(30.dp))

            Buttons(
                label = "게임코드 복사하기",
                onClicked = {
                            val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    var clipData: ClipData = ClipData.newPlainText("text",  "$gameCode")
                    clipboardManager.setPrimaryClip(clipData)
                    selectedCopy(false)
                },
                color = MyPageButtonColor,
                fontColor = Color.Black,
                modifier = Modifier.height(50.dp),
                fontSize = 15
            )
            Spacer(modifier = Modifier.height(15.dp))

        }
    }

}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun StartGameCodeInputDialog(onDismissRequest: (Boolean)->Unit,
                             selected: ()-> Unit,
                             successLoad: Boolean,
                             gameViewModel: GameViewModel) {

    val gameCode = gameViewModel.gameCode.collectAsState()

    val openDialog = remember {
        mutableStateOf(false)
    }

    Dialog(
        onDismissRequest = { onDismissRequest(false) },
        properties = DialogProperties()
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                label = { Text(text = "친구 코드를 입력해주세요") },
                value = gameCode.value,
                onValueChange = { Input ->
                    gameViewModel.gameCode.value = Input
                },
                shape = RoundedCornerShape(10.dp),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Buttons(
                    label = "시작",
                    onClicked = {
                        selected.invoke()
                        openDialog.value = true
                                },
                    color = Teal200,
                    fontColor = Color.Black,
                    modifier = Modifier
                        .height(40.dp)
                        .width(80.dp),
                    fontSize = 15,
                )


                Spacer(modifier = Modifier.width(30.dp))
                Buttons(
                    label = "닫기",
                    onClicked = {
                        gameViewModel.gameCode.value = ""
                        onDismissRequest(false)
                    },
                    color = Teal200,
                    fontColor = Color.Black,
                    modifier = Modifier
                        .height(40.dp)
                        .width(80.dp),
                    fontSize = 15
                )


            }
            if(openDialog.value){
                LoadingDataDialog(successLoad = successLoad)
            }

        }

    }

}



@Composable
fun CompatibilityViewDialog(
    onDismissRequest: (Boolean)->Unit,
    selected: ()-> Unit) {

    Dialog(
        onDismissRequest = { onDismissRequest(false) },
        properties = DialogProperties()
    ) {
        Column(modifier = Modifier
            .wrapContentSize()
            .padding(10.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(25.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {

                Buttons(
                    label = "궁합 결과보기",
                    onClicked = selected,
                    color = Teal200,
                    fontColor = Color.Black,
                    modifier = Modifier
                        .height(40.dp)
                        .wrapContentSize(),
                    fontSize = 15
                )
            }



        }
    }
}



@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MakeQuestionDialog(
    onDismissRequest: (Boolean)->Unit,
    selected: ()-> Unit,
    closeSelected: ()->Unit,
    gameViewModel: GameViewModel

) {

    val firstGameItem = gameViewModel.makeFirstGameItem.collectAsState()

    val secondGameItem = gameViewModel.makeSecondGameItem.collectAsState()

    val makeKindName = gameViewModel.makeSelectedKindItem.collectAsState()


    val openDialog = remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { onDismissRequest(false) },
        properties = DialogProperties()
    ) {
        Column(modifier = Modifier
            .wrapContentSize()
            .padding(10.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.padding(10.dp)) {
                Text(text = "질문 만들기")
            }
            Spacer(modifier = Modifier.height(10.dp))

            Buttons(
                label = "${makeKindName.value.kindName}",
                onClicked = { openDialog.value = true },
                color = MyPageButtonColor,
                fontColor = Color.Black,
                modifier = Modifier,
                fontSize = 20
            )

            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                label = { Text(text = "첫번째 카드를 입력해주세요")},
                value = firstGameItem.value,
                onValueChange = { Input ->
                    gameViewModel.makeFirstGameItem.value = Input
                },
                shape = RoundedCornerShape(10.dp),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(25.dp))

            TextField(
                label = { Text(text = "두번째 카드를 입력해주세요")},
                value = secondGameItem.value,
                onValueChange = { Input ->
                    gameViewModel.makeSecondGameItem.value = Input
                },
                shape = RoundedCornerShape(10.dp),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Buttons(
                    label = "예",
                    onClicked = selected,
                    color = Teal200,
                    fontColor = Color.Black,
                    modifier = Modifier
                        .height(40.dp)
                        .width(80.dp),
                    fontSize = 15
                )
                Spacer(modifier = Modifier.width(30.dp))
                Buttons(
                    label = "닫기",
                    onClicked = closeSelected,
                    color = Teal200,
                    fontColor = Color.Black,
                    modifier = Modifier
                        .height(40.dp)
                        .width(80.dp),
                    fontSize = 15
                )
            }

            if (openDialog.value) {
                KindDialog(
                    onDismissRequest = {openDialog.value = it} ,
                    selected = {
                        gameViewModel.makeSelectedKindItem.value = it
                    }
                )
            }



        }
    }
}


@Composable
fun LoadingDataDialog(
    successLoad: Boolean
    ) {

    var progress by remember { mutableStateOf(0.1f) }

    LaunchedEffect(successLoad) {
        if(successLoad == false) {if(progress > 0f) progress -= 0.1f}
    }

    Log.d(TAG, "로딩 $successLoad")
    Dialog(
        onDismissRequest = {successLoad} ,
        properties = DialogProperties()
    ) {
        Column(modifier = Modifier
            .padding(10.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(25.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {

                BaseCard(backgroundColor = Color.White, fontColor = Color.Black, modifier = Modifier, text = "불러오는중", fontSize = 30)
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

