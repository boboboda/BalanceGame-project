package com.bobodroid.balancegame.Screens.Auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bobodroid.balancegame.R
import com.bobodroid.balancegame.Routes.AuthRoute
import com.bobodroid.balancegame.Routes.AuthRouteAction
import com.bobodroid.balancegame.conponents.BaseButton
import com.bobodroid.balancegame.conponents.SnsButtonType
import com.bobodroid.balancegame.ui.theme.Purple200

@Composable
fun WelcomeScreen(routeAction: AuthRouteAction){
    Column(
        modifier = Modifier.padding(horizontal = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {




        Spacer(modifier = Modifier.height(80.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.ic_heart), contentDescription = "")
            Spacer(modifier = Modifier.width(10.dp))
            Text("궁합 밸런스 게임", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }
        

        Spacer(modifier = Modifier.height(80.dp))

        LottieAnimationView()


        Spacer(modifier = Modifier.weight(1f))


        BaseButton(
            title = "로그인",
            onClick = { routeAction.navTo(AuthRoute.LOGIN) })

        Spacer(modifier = Modifier.height(15.dp))

        BaseButton(
            type = SnsButtonType.OUTLINE,
            title = "회원가입",
            onClick = { routeAction.navTo(AuthRoute.REGISTER) })

        Spacer(modifier = Modifier.height(40.dp))

    }
}



@Composable
fun LottieAnimationView() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .Asset("love_animation.json")
    )

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.height(400.dp)
    )
}