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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.preferences.LocalSharedStorage
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveAlertDialog
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveButton
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.default
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.PrimaryButton
import presentation.components.ProfileListTile
import presentation.components.ToolBarWithBack
import presentation.navigation.NavigationRoute

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun ProfileScreen(navigator: Navigator, localSharedStorage: LocalSharedStorage) {

    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        ToolBarWithBack(navigator, StringResources.Profile)
    }) {

        Column(
            modifier = Modifier.fillMaxSize().background(ColorResources.Background).padding(10.dp)
        ) {

            ProfileListTile(
                Icons.Default.AccountCircle,
                StringResources.UserName,
                localSharedStorage.getUserName()
            )

            Spacer(modifier = Modifier.height(5.dp))

            ProfileListTile(
                Icons.Default.Place,
                StringResources.Plant,
                localSharedStorage.getPlant()
            )

            Spacer(modifier = Modifier.height(5.dp))

            ProfileListTile(
                Icons.Default.Home,
                StringResources.Warehouse,
                localSharedStorage.getWareHouse()
            )

            Spacer(modifier = Modifier.height(5.dp))

            ProfileListTile(
                Icons.Default.Info,
                StringResources.Printer,
                localSharedStorage.getPrinter()
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                PrimaryButton(StringResources.LogOut) {
                    showLogoutDialog = true
                }
            }

        }
    }

    if (showLogoutDialog) {

        AdaptiveAlertDialog(
            title = { Text(StringResources.LogOut) },
            message = { Text(StringResources.LogOutDesc) },
            buttons = {
                default(onClick = {
                    showLogoutDialog = false
                    localSharedStorage.clearAll()
                    navigator.navigate(NavigationRoute.OauthWebView.route)
                }) { Text(StringResources.OK) }
            },
            onDismissRequest = { showLogoutDialog = false }
        )

    }


}