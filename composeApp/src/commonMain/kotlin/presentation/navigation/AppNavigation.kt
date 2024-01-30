package presentation.navigation

import androidx.compose.runtime.Composable
import data.preferences.LocalSharedStorage
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.koinInject
import presentation.screens.DashBoardScreen
import presentation.screens.ProductListScreen
import presentation.viewmodels.ProductListViewModel
import presentation.screens.ProfileScreen
import presentation.screens.putaway.PutAwayScreen

@Composable
fun AppNavigation() {

    val navigator = rememberNavigator()

    NavHost(navigator = navigator, initialRoute = NavigationRoute.DashBoard.route) {

        scene(route = NavigationRoute.DashBoard.route) {
            DashBoardScreen(navigator)
        }

        scene(route = NavigationRoute.Profile.route) {
            val localSharedStorage: LocalSharedStorage = koinInject()
            ProfileScreen(navigator,localSharedStorage)
        }

        scene(route = NavigationRoute.ProductList.route) {
            val viewModel: ProductListViewModel = koinViewModel(ProductListViewModel::class)
            ProductListScreen(viewModel, navigator) {

            }
        }

        scene(route = NavigationRoute.Putaway.route) {
            val localSharedStorage: LocalSharedStorage = koinInject()
            PutAwayScreen(navigator,localSharedStorage)
        }

    }


}

sealed class NavigationRoute(val route: String) {
    data object DashBoard : NavigationRoute("dashboard")
    data object ProductList : NavigationRoute("product_list")
    data object Profile : NavigationRoute("profile")

    data object Putaway : NavigationRoute("putaway")
}