package presentation.screens.oauth

import StringResources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.preferences.LocalSharedStorage
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.CustomCircleProgressbar
import presentation.components.PrimaryButton
import presentation.navigation.NavigationRoute
import presentation.viewmodels.LoginViewModel

@Composable
fun RulesScreen(
    navigator: Navigator,
    viewModel: LoginViewModel,
    localSharedStorage: LocalSharedStorage) {

    val uiState = viewModel.uiState.collectAsState()

    viewModel.getProfile(localSharedStorage.getUserId())

    when {
        uiState.value.isLoading -> {
            CustomCircleProgressbar()
        }

        !uiState.value.error.isNullOrEmpty() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = uiState.value.error.toString())
                    Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){

                        PrimaryButton(StringResources.LogOut) {
                            localSharedStorage.clearAll()
                            navigator.navigate(NavigationRoute.OauthWebView.route)
                        }
                    }
                }
            }
        }

        uiState.value.data!=null ->{

            val userDetails=uiState.value.data!!

            userDetails.userParameters?.forEach {
                if (it.parameterID == StringResources.WareHouseTechTerms.Plant) {
                    localSharedStorage.savePlant(it.parameterValue)
                } else if (it.parameterID == StringResources.WareHouseTechTerms.WareHouse) {
                    localSharedStorage.saveWareHouse(it.parameterValue)
                }
            }

            localSharedStorage.savePrinter(userDetails.outputDevice)
            navigator.navigate(NavigationRoute.DashBoard.route)
        }

    }

}