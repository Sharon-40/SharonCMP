package presentation.screens.bintobin

import ColorResources
import StringResources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.ToolBarWithBack
import presentation.viewmodels.BinToBinViewModel

@Composable
fun BinToBinScreen(navigator: Navigator,viewModel: BinToBinViewModel) {

    Scaffold(topBar = {
        ToolBarWithBack(navigator, StringResources.Apps.BinToBin.name)
    }) {

        Column (modifier = Modifier.fillMaxHeight().background(ColorResources.Background).padding(10.dp)){
            BinToBinByBinScreen(viewModel)
        }
    }
}