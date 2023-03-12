package com.bobodroid.balancegame.conponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BaseCard(backgroundColor: Color,
             fontColor: Color,
             modifier: Modifier,
             text: String,
             fontSize: Int) {


    Card(
        contentColor = fontColor,
        backgroundColor = backgroundColor,
        modifier = modifier,
        elevation = 0.dp) {

        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, fontSize = fontSize.sp, textAlign = TextAlign.Center)
        }

    }


}