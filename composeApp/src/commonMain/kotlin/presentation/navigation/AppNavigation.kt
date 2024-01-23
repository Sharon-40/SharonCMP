package presentation.navigation

import androidx.compose.runtime.Composable
import data.prefrences.LocalSharedStorage
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.koinInject
import presentation.screens.DashBoardScreen
import presentation.screens.ProductListScreen
import presentation.screens.ProductListViewModel
import presentation.screens.ProfileScreen

@Composable
fun AppNavigation() {

    val navigator = rememberNavigator()

    NavHost(navigator = navigator, initialRoute = NavigationRoute.DashBoard.route) {

        scene(route = NavigationRoute.DashBoard.route) {
            DashBoardScreen(navigator)
        }

        scene(route = NavigationRoute.Profile.route) {
            val localSharedStorage: LocalSharedStorage = koinInject()
            ProfileScreen(localSharedStorage)
        }

        scene(route = NavigationRoute.ProductList.route) {
            val viewModel: ProductListViewModel = koinViewModel(ProductListViewModel::class)
            ProductListScreen(viewModel, navigator) {

            }
        }
    }


}

sealed class NavigationRoute(val route: String) {
    object DashBoard : NavigationRoute("dashboard")
    object ProductList : NavigationRoute("product_list")
    object Profile : NavigationRoute("profile")
}