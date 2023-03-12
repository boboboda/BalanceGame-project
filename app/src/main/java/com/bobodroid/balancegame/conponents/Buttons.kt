@file:OptIn(ExperimentalMaterialApi::class)

package com.bobodroid.balancegame.conponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobodroid.balancegame.ui.theme.*
import com.bobodroid.balancegame.viewmodels.dataViewModels.ItemKind

@Composable
fun Buttons(
    label: String,
    onClicked:(()->Unit)?,
    color: Color, fontColor: Color,
    enabled: Boolean = true,
    modifier: Modifier,
    fontSize: Int) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
            contentColor = fontColor,
            disabledContentColor = Color.White,
            disabledBackgroundColor = Color.Gray),
        modifier = modifier,
        enabled = enabled,
        onClick = {onClicked?.invoke()})
    {
        Text(text = label, fontSize = fontSize.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(0.dp))
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActionButton(
    action: ItemKind,
    onClicked: () -> Unit) {
    Card(
        elevation = 8.dp,
        onClick =  onClicked
    ) {
        Text(action.kindName,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }

}



enum class SnsButtonType {
    FILL, OUTLINE
}
@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    type: SnsButtonType = SnsButtonType.FILL,
    title: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    when(type) {
        SnsButtonType.FILL -> MainFilledButton(
            modifier,
            title,
            enabled,
            isLoading,
            onClick
        )
        SnsButtonType.OUTLINE -> MainOutlineButton(
            modifier,
            title,
            enabled,
            isLoading,
            onClick
        )
    }

}

@Composable
fun MainFilledButton(
    modifier: Modifier = Modifier,
    title: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            backgroundColor = Color.Black,
            disabledContentColor = Color.White,
            disabledBackgroundColor = Gray
        ),
        enabled = enabled,
        onClick = onClick
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier
                    .scale(1f)
                    .size(20.dp)
            )
        } else {
            Text(text = title,
                fontSize = 15.sp,
                modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun MainOutlineButton(
    modifier: Modifier = Modifier,
    title: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            backgroundColor = Color.White,
            disabledContentColor = Color.White,
            disabledBackgroundColor = Color.White
        ),
        enabled = enabled,
        border = BorderStroke(1.dp, Color.Black),
        onClick = onClick
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.Black,
                modifier = Modifier
                    .scale(1f)
                    .size(20.dp)
            )
        } else {
            Text(text = title,
                fontSize = 15.sp,
                color = Color.Black,
                modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun LogInBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit) {
    Button(
        modifier = modifier
            .size(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            backgroundColor = Color.White,
            disabledContentColor = Color.White,
            disabledBackgroundColor = Color.White
        ),
        border = BorderStroke(1.dp, Border),
        onClick = onClick
    ) {
        Image(
            painter = painterResource(id = com.bobodroid.balancegame.R.drawable.back_arrow),
            "뒤로가기 버튼",
            modifier = Modifier.size(20.dp))

    }
}


@Composable
fun MyPageButtons(label: String,
                  id: Int,
                  selectedId:Int?,
                  onClicked:((Int)->Unit)?,
                  fontColor: Color,
                  enabled: Boolean = true,
                  modifier: Modifier,
                  fontSize: Int
) {

    var currentCardId : Int = id

    var color = if (selectedId == currentCardId) MyPageListButtonColor else MyPageButtonColor



    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
            contentColor = fontColor,
            disabledContentColor = Color.White,
            disabledBackgroundColor = Color.Gray),
        modifier = modifier,
        enabled = enabled!!,
        onClick = {onClicked?.invoke(currentCardId)})
    {
        Text(text = label, fontSize = fontSize.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(0.dp))
    }
}


