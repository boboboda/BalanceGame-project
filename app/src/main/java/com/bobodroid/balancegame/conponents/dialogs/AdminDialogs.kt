package com.bobodroid.balancegame.conponents.dialogs

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bobodroid.balancegame.conponents.BaseCard
import com.bobodroid.balancegame.conponents.Buttons
import com.bobodroid.balancegame.conponents.KindDialog
import com.bobodroid.balancegame.ui.theme.MyPageButtonColor
import com.bobodroid.balancegame.ui.theme.Teal200
import com.bobodroid.balancegame.viewmodels.GameViewModel
import com.bobodroid.balancegame.viewmodels.dataViewModels.Compatibility

@Composable
fun LoadingDataDialog() {

    var progress by remember { mutableStateOf(0.1f) }

    LaunchedEffect(true) {
        if(progress > 0f) progress -= 0.1f}

    Dialog(
        onDismissRequest = {} ,
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




@Composable
fun MakeMatchTextItemDialog(
    onDismissRequest: (Boolean)->Unit,
    selected: ()-> Unit,
    closeSelected: ()->Unit,
    gameViewModel: GameViewModel

) {

    val saveName = gameViewModel.compatibilitySaveName.collectAsState()

    val zeroTextItem = gameViewModel.zeroFlow.collectAsState()

    val oneTextItem = gameViewModel.oneFlow.collectAsState()

    val twoTextItem = gameViewModel.twoFlow.collectAsState()

    val threeTextItem = gameViewModel.threeFlow.collectAsState()

    val fourTextItem = gameViewModel.fourFlow.collectAsState()

    val fiveTextItem = gameViewModel.fiveFlow.collectAsState()

    val sixTextItem = gameViewModel.sixFlow.collectAsState()

    val sevenTextItem = gameViewModel.sevenFlow.collectAsState()

    val eightTextItem = gameViewModel.eightFlow.collectAsState()

    val nineTextItem = gameViewModel.nineFlow.collectAsState()

    val tenTextItem = gameViewModel.tenFlow.collectAsState()


    Dialog(
        onDismissRequest = { onDismissRequest(false) },
        properties = DialogProperties()
    ) {

        val lazyScrollState = rememberLazyListState()

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
                Text(text = "궁합 문구 만들기")
            }
            LazyColumn(state = lazyScrollState,
                modifier = Modifier
                    .height(150.dp)
                    .wrapContentSize()
                    .padding(10.dp)){
                item {
                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        label = { Text(text = "저장할 제목을 입력해주세요") },
                        value = saveName.value,
                        onValueChange = { Input ->
                            gameViewModel.compatibilitySaveName.value = Input
                        },
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 1
                    )


                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        label = { Text(text = "0% 궁합 입력해주세요") },
                        value = zeroTextItem.value,
                        onValueChange = { Input ->
                            gameViewModel.zeroFlow.value = Input
                        },
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        label = { Text(text = "10% 궁합 입력해주세요") },
                        value = oneTextItem.value,
                        onValueChange = { Input ->
                            gameViewModel.oneFlow.value = Input
                        },
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        label = { Text(text = "20% 궁합 입력해주세요") },
                        value = twoTextItem.value,
                        onValueChange = { Input ->
                            gameViewModel.twoFlow.value = Input
                        },
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 1
                    )

                    TextField(
                        label = { Text(text = "30% 궁합 입력해주세요") },
                        value = threeTextItem.value,
                        onValueChange = { Input ->
                            gameViewModel.threeFlow.value = Input
                        },
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 1
                    )

                    TextField(
                        label = { Text(text = "40% 궁합 입력해주세요") },
                        value = fourTextItem.value,
                        onValueChange = { Input ->
                            gameViewModel.fourFlow.value = Input
                        },
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 1
                    )

                    TextField(
                        label = { Text(text = "50% 궁합 입력해주세요") },
                        value = fiveTextItem.value,
                        onValueChange = { Input ->
                            gameViewModel.fiveFlow.value = Input
                        },
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 1
                    )

                    TextField(
                        label = { Text(text = "60% 궁합 입력해주세요") },
                        value = sixTextItem.value,
                        onValueChange = { Input ->
                            gameViewModel.sixFlow.value = Input
                        },
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 1
                    )

                    TextField(
                        label = { Text(text = "70% 궁합 입력해주세요") },
                        value = sevenTextItem.value,
                        onValueChange = { Input ->
                            gameViewModel.sevenFlow.value = Input
                        },
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 1
                    )

                    TextField(
                        label = { Text(text = "80% 궁합 입력해주세요") },
                        value = eightTextItem.value,
                        onValueChange = { Input ->
                            gameViewModel.eightFlow.value = Input
                        },
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 1
                    )

                    TextField(
                        label = { Text(text = "90% 궁합 입력해주세요") },
                        value = nineTextItem.value,
                        onValueChange = { Input ->
                            gameViewModel.nineFlow.value = Input
                        },
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 1
                    )

                    TextField(
                        label = { Text(text = "100% 궁합 입력해주세요") },
                        value = tenTextItem.value,
                        onValueChange = { Input ->
                            gameViewModel.tenFlow.value = Input
                        },
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 1
                    )

                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Buttons(
                    label = "만들기",
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


        }

    }
}


@Composable
fun MatchTextItemDialog(
    onDismissRequest: (Boolean)->Unit,
    data: Compatibility,
    closeSelected: ()->Unit

) {
    Dialog(
        onDismissRequest = { onDismissRequest(false) },
        properties = DialogProperties()
    ) {

        val lazyScrollState = rememberLazyListState()

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
                Text(text = "궁합 문구")
            }
            LazyColumn(
                state = lazyScrollState,
                modifier = Modifier
                    .height(150.dp)
                    .wrapContentSize()
                    .padding(10.dp)
            ) {
                item {

                    BaseCard(
                        backgroundColor = MyPageButtonColor,
                        fontColor = Color.Black,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 5.dp, end = 5.dp),
                        text = "0% = ${data.zero}",
                        fontSize = 15
                    )
                    Spacer(modifier = Modifier.height(3.dp))

                    BaseCard(
                        backgroundColor = MyPageButtonColor,
                        fontColor = Color.Black,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 5.dp, end = 5.dp),
                        text = "10% = ${data.one}",
                        fontSize = 15
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    BaseCard(
                        backgroundColor = MyPageButtonColor,
                        fontColor = Color.Black,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 5.dp, end = 5.dp),
                        text = "20% = ${data.two}",
                        fontSize = 15
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    BaseCard(
                        backgroundColor = MyPageButtonColor,
                        fontColor = Color.Black,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 5.dp, end = 5.dp),
                        text = "30% = ${data.three}",
                        fontSize = 15
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    BaseCard(
                        backgroundColor = MyPageButtonColor,
                        fontColor = Color.Black,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 5.dp, end = 5.dp),
                        text = "40% = ${data.four}",
                        fontSize = 15
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    BaseCard(
                        backgroundColor = MyPageButtonColor,
                        fontColor = Color.Black,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 5.dp, end = 5.dp),
                        text = "50% = ${data.five}",
                        fontSize = 15
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    BaseCard(
                        backgroundColor = MyPageButtonColor,
                        fontColor = Color.Black,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 5.dp, end = 5.dp),
                        text = "60% = ${data.six}",
                        fontSize = 15
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    BaseCard(
                        backgroundColor = MyPageButtonColor,
                        fontColor = Color.Black,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 5.dp, end = 5.dp),
                        text = "70% = ${data.seven}",
                        fontSize = 15
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    BaseCard(
                        backgroundColor = MyPageButtonColor,
                        fontColor = Color.Black,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 5.dp, end = 5.dp),
                        text = "80% = ${data.eight}",
                        fontSize = 15
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    BaseCard(
                        backgroundColor = MyPageButtonColor,
                        fontColor = Color.Black,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 5.dp, end = 5.dp),
                        text = "90% = ${data.nine}",
                        fontSize = 15
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    BaseCard(
                        backgroundColor = MyPageButtonColor,
                        fontColor = Color.Black,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 5.dp, end = 5.dp),
                        text = "100% = ${data.ten}",
                        fontSize = 15
                    )

                }
            }


            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

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
fun AdminMakeQuestionDialog(
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
