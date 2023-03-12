package com.bobodroid.balancegame

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bobodroid.balancegame.Routes.AuthRoute
import com.bobodroid.balancegame.Routes.AuthRouteAction
import com.bobodroid.balancegame.Screens.Auth.LoginScreen
import com.bobodroid.balancegame.Screens.Auth.PasswordFindScreen
import com.bobodroid.balancegame.Screens.Auth.RegisterScreen
import com.bobodroid.balancegame.Screens.Auth.WelcomeScreen
import com.bobodroid.balancegame.Screens.HomeScreen
import com.bobodroid.balancegame.Screens.Main.*
import com.bobodroid.balancegame.ui.theme.BalanceGameTheme
import com.bobodroid.balancegame.viewmodels.AuthViewModel
import com.bobodroid.balancegame.viewmodels.GameViewModel
import com.bobodroid.balancegame.viewmodels.HomeViewModel
import com.bobodroid.balancegame.viewmodels.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val TAG = "메인"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val TAG = "메인 액티비티"
    }

    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()
    private val myPageViewModel: MyPageViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BalanceGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppScreen(authViewModel, homeViewModel, gameViewModel, myPageViewModel)
                }
            }
        }
    }
}


@Composable
fun MainNaHost(
    mainNavController: NavHostController,
    startRouter: MainRoute = MainRoute.Home,
    mainRouteAction: MainRouteAction,
    authRouteAction: AuthRouteAction,
    authViewModel: AuthViewModel,
    gameViewModel: GameViewModel,
    myPageViewModel: MyPageViewModel,
    homeViewModel: HomeViewModel
) {
  NavHost(
      navController = mainNavController,
      startDestination = startRouter.routeName!!) {
      composable(MainRoute.Home.routeName!!) {
          HomeScreen(mainRouteAction, gameViewModel)
      }
      composable(MainRoute.SavePage.routeName!!){
          SaveListScreen(mainRouteAction, authViewModel, myPageViewModel, homeViewModel, gameViewModel)
      }
      composable(MainRoute.DoItYourSelf.routeName!!){
          DiyScreen(mainRouteAction, authRouteAction, authViewModel, homeViewModel)
      }
      composable(MainRoute.SingleGame.routeName!!){
          SingleGameScreen(mainRouteAction, gameViewModel, authViewModel)
      }
      composable(MainRoute.CompatibilityGame.routeName!!){
          CompatibilityGameScreen(mainRouteAction, gameViewModel)
      }
      composable(MainRoute.CompatibilityResult.routeName!!){
          ResultScreen(mainRouteAction, gameViewModel)
      }
      composable(MainRoute.Admin.routeName!!){
          AdminScreen(mainRouteAction, authViewModel, gameViewModel, myPageViewModel, homeViewModel)
      }

  }

}

@Composable
fun AuthNavHost(
    authNavController: NavHostController,
    startRouter: AuthRoute = AuthRoute.LOGIN,
    routeAction: AuthRouteAction,
    authViewModel: AuthViewModel,
    myPageViewModel: MyPageViewModel,
    gameViewModel: GameViewModel,
    mainRouteAction: MainRouteAction,
    homeViewModel: HomeViewModel
){
    NavHost(
        navController = authNavController,
        startDestination = startRouter.routeName) {
        composable(AuthRoute.LOGIN.routeName) {
            LoginScreen(routeAction,mainRouteAction, homeViewModel  ,authViewModel, gameViewModel)
        }
        composable(AuthRoute.REGISTER.routeName) {
            RegisterScreen(authViewModel, routeAction, myPageViewModel, gameViewModel)
        }
        composable(AuthRoute.PASSWORD.routeName) {
            PasswordFindScreen(routeAction)
        }
    }
}


//composable(AuthRoute.WELCOME.routeName) {
//    WelcomeScreen(routeAction)
//}



@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AppScreen(
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    gameViewModel: GameViewModel,
    myPageViewModel: MyPageViewModel
) {

    val isLoggedIn = authViewModel.isLoggedIn.collectAsState()

    val mainNavController = rememberNavController()
    val mainRouteAction = remember(mainNavController) {
        MainRouteAction(mainNavController)
    }

    val authNavController = rememberNavController()
    val authRouteAction = remember(authNavController) {
        AuthRouteAction(authNavController)
    }

    val mainBackStack = authNavController.currentBackStackEntryAsState()

    val selectedBottombar= homeViewModel.selectedCardId.collectAsState()

    val needAuth = authViewModel.needAuthContext.collectAsState()


    if(!needAuth.value)

    {
        Scaffold(
            topBar = { MainTopBar() },
            bottomBar = {
                MainBottomBar(
                    mainRouteAction,
                    mainBackStack.value,
                    gameViewModel,
                    homeViewModel,
                    authViewModel
                )
            },

            ) {
            Column(
                modifier = Modifier.padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
            ) {

                MainNaHost(
                    mainNavController = mainNavController,
                    mainRouteAction = mainRouteAction,
                    authViewModel = authViewModel,
                    authRouteAction = authRouteAction,
                    gameViewModel = gameViewModel,
                    myPageViewModel = myPageViewModel,
                    homeViewModel = homeViewModel
                )
            }
        }

    } else {

        if(!isLoggedIn.value) {

            AuthNavHost(
                authNavController = authNavController,
                routeAction = authRouteAction ,
                authViewModel = authViewModel,
                myPageViewModel = myPageViewModel,
                gameViewModel = gameViewModel,
                mainRouteAction = mainRouteAction,
                homeViewModel = homeViewModel)

        }

        else {
            Scaffold(
                topBar = { MainTopBar() },
                bottomBar = {
                    MainBottomBar(
                        mainRouteAction,
                        mainBackStack.value,
                        gameViewModel,
                        homeViewModel,
                        authViewModel
                    )
                },

                ) {
                Column(
                    modifier = Modifier.padding(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding()
                    )
                ) {

                    MainNaHost(
                        mainNavController = mainNavController,
                        mainRouteAction = mainRouteAction,
                        authViewModel = authViewModel,
                        authRouteAction = authRouteAction,
                        gameViewModel = gameViewModel,
                        myPageViewModel = myPageViewModel,
                        homeViewModel = homeViewModel
                    )
                }
            }
        }

    }





}













