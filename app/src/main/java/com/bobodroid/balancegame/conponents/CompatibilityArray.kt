package com.bobodroid.balancegame.conponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobodroid.balancegame.ui.theme.CompatibilityColor
import com.bobodroid.balancegame.ui.theme.GameCordListColor
import com.bobodroid.balancegame.ui.theme.MyPageButtonColor
import com.bobodroid.balancegame.ui.theme.Primary

@Composable
fun CompatibilityArray(list: String,
                       firstUser: String = "친구",
                       firstItem: String,
                       secondUser: String = "본인",
                       secondItem: String,
                       resultColor: Boolean,
                       resultText: String
) {
    Card(modifier = Modifier
        .height(60.dp)
        .padding(5.dp),
        shape = RoundedCornerShape(2.dp),
        backgroundColor = if(resultColor) MyPageButtonColor else CompatibilityColor
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp, bottom = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.width(4.dp))
            Row(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Text(text = list, fontSize = 20.sp)
            }
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.width(10.dp))


                Row {
                    BaseTextCard(firstText = firstUser, secondText = firstItem)

                    Spacer(modifier = Modifier.width(10.dp))

                    BaseTextCard(firstText = secondUser, secondText = secondItem)
                }
                Spacer(modifier = Modifier.width(10.dp))

                Row {
                    Text(text = "= $resultText", fontSize = 20.sp)
                }

            }
        }
    }

}


@Composable
fun BaseTextCard(firstText: String,
secondText: String) {
    Card(modifier = Modifier.wrapContentSize().padding(2.dp)) {
        Text(text = "$firstText: $secondText", fontSize = 20.sp, modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 3.dp, bottom = 3.dp))

    }
}