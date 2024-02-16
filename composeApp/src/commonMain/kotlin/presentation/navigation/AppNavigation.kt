package presentation.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import data.PlatformUtils
import data.preferences.LocalSharedStorage
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.koinInject
import presentation.screens.DashBoardScreen
import presentation.screens.ProductListScreen
import presentation.screens.ProfileScreen
import presentation.screens.SplashScreen
import presentation.screens.bintobin.BinToBinScreen
import presentation.screens.oauth.OAuth2Screen
import presentation.screens.oauth.OAuth2WebView
import presentation.screens.oauth.RulesScreen
import presentation.screens.putaway.PutAwayScreen
import presentation.viewmodels.BinToBinViewModel
import presentation.viewmodels.LoginViewModel
import presentation.viewmodels.ProductListViewModel
import presentation.viewmodels.PutAwayViewModel

@Composable
fun AppNavigation() {

    val navigator = rememberNavigator()

    val localSharedStorage: LocalSharedStorage = koinInject()
    val platformUtils: PlatformUtils = koinInject()

    NavHost(navigator = navigator, initialRoute = NavigationRoute.Splash.route) {

        scene(route = NavigationRoute.Splash.route) {
           SplashScreen {

               if (localSharedStorage.getUserId().isEmpty())
               {
                   navigator.navigate(NavigationRoute.OauthWebView.route)
               }else{
                   navigator.navigate(NavigationRoute.DashBoard.route)
               }
           }
        }

        scene(route = NavigationRoute.OauthWebView.route) {
            OAuth2WebView(koinInject(),koinInject()) {
                navigator.navigate(NavigationRoute.Oauth.route)
            }
        }

        scene(route = NavigationRoute.Oauth.route) {
            OAuth2Screen(navigator,koinInject(),koinInject(),koinInject())
        }

        scene(route = NavigationRoute.BussRules.route) {
            val viewModel: LoginViewModel = koinViewModel(LoginViewModel::class)
            RulesScreen(navigator,viewModel,koinInject())
        }

        scene(route = NavigationRoute.DashBoard.route) {
            DashBoardScreen(navigator)
        }

        scene(route = NavigationRoute.Profile.route) {
            ProfileScreen(navigator,localSharedStorage)
        }

        scene(route = NavigationRoute.ProductList.route) {
            val viewModel: ProductListViewModel = koinViewModel(ProductListViewModel::class)
            ProductListScreen(viewModel, navigator) {

            }
        }

        scene(route = NavigationRoute.PutAway.route) {
            Navigator(PutAwayScreen(navigator))
        }

        scene(route = NavigationRoute.BinToBin.route) {
            val viewModel: BinToBinViewModel = koinViewModel(BinToBinViewModel::class)
            BinToBinScreen(navigator,viewModel,platformUtils)
        }

    }


}

sealed class NavigationRoute(val route: String) {
    data object DashBoard : NavigationRoute("dashboard")
    data object ProductList : NavigationRoute("product_list")
    data object Profile : NavigationRoute("profile")

    data object PutAway : NavigationRoute("putAway")

    data object BinToBin : NavigationRoute("binTobin")

    data object Oauth : NavigationRoute("oauth")

    data object OauthWebView : NavigationRoute("oauthWebView")

    data object Splash : NavigationRoute("splash")

    data object BussRules : NavigationRoute("buss_rules")


}