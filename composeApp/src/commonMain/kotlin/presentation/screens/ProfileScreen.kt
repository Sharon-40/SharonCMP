package presentation.screens

import ColorResources
import presentation.components.ProfileListTile
import StringResources
import presentation.components.ToolBarWithBack
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.prefrences.LocalSharedStorage
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ProfileScreen(navigator: Navigator,localSharedStorage: LocalSharedStorage) {

    Scaffold(topBar = { ToolBarWithBack(navigator,StringResources.Profile) }) {

        Column (modifier = Modifier.fillMaxSize().background(ColorResources.Background).padding(10.dp)) {

            ProfileListTile(Icons.Default.AccountCircle,StringResources.UserName,localSharedStorage.getUserName())

            Spacer(modifier = Modifier.height(5.dp))

            ProfileListTile(Icons.Default.Place,StringResources.Plant,localSharedStorage.getPlant())

            Spacer(modifier = Modifier.height(5.dp))

            ProfileListTile(Icons.Default.Home,StringResources.Warehouse,localSharedStorage.getWareHouse())

        }
    }


}