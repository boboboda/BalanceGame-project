package com.bobodroid.balancegame

import androidx.navigation.NavHostController


sealed class MainRoute(
    open val routeName: String? = null,
    open val title: String? = null,
    open val selectValue: Int? = null,
    open val iconResId: Int? = null
) {
    object Home: MainRoute("HOME", "홈", 1 ,R.drawable.home)
    object MyPage: MainRoute("My_PAGE","마이페이지",2, R.drawable.profile)
    object DoItYourSelf: MainRoute("DO_IT_YOUR_SELF","DIY", 3, R.drawable.ic_create)
    object SingleGame: MainRoute("SINGLE_GAME",null,null, null)
    object CompatibilityGame: MainRoute("COMPATIBILITY_GAME", null,null, null)
}


// 메인 관련 화면 라우트 액션
class MainRouteAction(navHostController: NavHostController) {


    //특정 라우트로 이동
    val navTo: (MainRoute) -> Unit = { Route ->
        navHostController.navigate(Route.routeName!!) {
            popUpTo(Route.routeName!!){ inclusive = true }

        }
    }

    // 뒤로가기 이동
    val goBack: () -> Unit = {
        navHostController.navigateUp()
    }

}