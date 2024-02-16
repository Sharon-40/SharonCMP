package presentation.screens

import ColorResources
import StringResources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.preferences.LocalSharedStorage
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.PrimaryButton
import presentation.components.ProfileListTile
import presentation.components.ToolBarWithBack
import presentation.navigation.NavigationRoute

@Composable
fun ProfileScreen(navigator: Navigator,localSharedStorage: LocalSharedStorage) {

    Scaffold(topBar = { ToolBarWithBack(navigator,StringResources.Profile) }) {

        Column (modifier = Modifier.fillMaxSize().background(ColorResources.Background).padding(10.dp)) {

            ProfileListTile(Icons.Default.AccountCircle,StringResources.UserName,localSharedStorage.getUserName())

            Spacer(modifier = Modifier.height(5.dp))

            ProfileListTile(Icons.Default.Place,StringResources.Plant,localSharedStorage.getPlant())

            Spacer(modifier = Modifier.height(5.dp))

            ProfileListTile(Icons.Default.Home,StringResources.Warehouse,localSharedStorage.getWareHouse())

            Spacer(modifier = Modifier.height(5.dp))

            ProfileListTile(Icons.Default.Info,StringResources.Printer,localSharedStorage.getPrinter())

            Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){

                PrimaryButton(StringResources.LogOut) {
                    localSharedStorage.clearAll()
                    navigator.navigate(NavigationRoute.OauthWebView.route)
                }
            }

        }
    }
}