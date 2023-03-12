package com.bobodroid.balancegame.conponents

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobodroid.balancegame.ui.theme.FirstGameItemColor
import com.bobodroid.balancegame.ui.theme.SecondGameItemColor
import com.bobodroid.balancegame.ui.theme.StageColor
import font.fontFamily


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SingleGameListView(
    kind: String,
    firstText: String,
    secondText: String,
    firstOnClicked: ()-> Unit,
    secondClicked: ()-> Unit,
    gameState: Int) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.8f)
//        .background(Color.White),

        ) {

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Card(modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(50.dp)
                .padding(start = 20.dp),
                onClick = {

                }) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(StageColor),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = "10/$gameState",fontFamily = fontFamily ,fontSize = 25.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(50.dp)
                    .padding(start = 20.dp),
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(StageColor),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = "$kind",fontFamily = fontFamily, fontSize = 25.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.38f),
                onClick = firstOnClicked) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(FirstGameItemColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = firstText,
                        fontFamily = fontFamily,
                        fontSize = 70.sp,
                        color = Color.DarkGray
                    )
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
                .fillMaxHeight(0.8f),
                onClick = secondClicked
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(SecondGameItemColor),
                    contentAlignment = Alignment.Center
                ){
                    Text(modifier = Modifier.padding(5.dp),
                        text = secondText,
                        fontFamily = fontFamily,
                        fontSize = 70.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center)
                }
            }
        }

    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TogetherGameListView(
    kind: String,
    firstText: String,
    secondText: String,
    firstOnClicked: ()-> Unit,
    secondClicked: ()-> Unit,
    gameState: Int) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
//        .background(Color.White),

    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Card(modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .padding(start = 20.dp),
                onClick = {

                }) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(StageColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "10/$gameState",
                        fontSize = 25.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(4.dp))

            Card(
                modifier = Modifier
                    .width(80.dp)
                    .height(50.dp)
                    .padding(start = 20.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(StageColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$kind",
                        fontSize = 25.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.width(4.dp))

            Card(
                modifier = Modifier
                    .width(80.dp)
                    .height(50.dp)
                    .padding(start = 20.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(StageColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "궁합",
                        fontSize = 25.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(200.dp),
                onClick = firstOnClicked
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(FirstGameItemColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = firstText,
                        fontSize = 80.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(60.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(200.dp),
                onClick = secondClicked
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SecondGameItemColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = secondText,
                        fontSize = 80.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }
}
