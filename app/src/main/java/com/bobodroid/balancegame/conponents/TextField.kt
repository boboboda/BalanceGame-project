package com.bobodroid.balancegame.conponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bobodroid.balancegame.R
import com.bobodroid.balancegame.ui.theme.Border
import com.bobodroid.balancegame.ui.theme.Gray
import com.bobodroid.balancegame.ui.theme.LightGay

@Composable
fun GameTextField(
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    value: String,
    onValueChanged: (String) -> Unit

) {
    Column {
        Text(text = label, color = Gray)
        Surface(
            color = LightGay,
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Border),
            shape = RoundedCornerShape(8.dp)
        ) {
            BasicTextField(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 20.dp),
                textStyle = TextStyle(fontSize = 18.sp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                value = value,
                onValueChange = onValueChanged
            )
        }
    }
}


@Composable
fun GamePasswordTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
) {

    val passwordVisble = remember {
        mutableStateOf(false)
    }

    val passwordVisbleIcon = if (passwordVisble.value) R.drawable.ic_visible else R.drawable.ic_invisible

    Column {
        Text(text = label, color = Gray)
        Surface(
            color = LightGay,
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Border),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicTextField(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 20.dp
                        )
                        .weight(1f),

                    textStyle = TextStyle(fontSize = 18.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    visualTransformation = if (passwordVisble.value) VisualTransformation.None else PasswordVisualTransformation(),
                    value = value,
                    onValueChange = onValueChanged
                )

                IconButton(onClick = {
                    passwordVisble.value = !passwordVisble.value
                }) {
                    Image(
                        painter = painterResource(id = passwordVisbleIcon),
                        contentDescription = "비밀번호 노출여부 버튼"
                    )

                }
            }

        }
    }
}