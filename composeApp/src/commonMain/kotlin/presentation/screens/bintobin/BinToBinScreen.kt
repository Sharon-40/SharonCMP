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
import data.PlatformUtils
import data.preferences.LocalSharedStorage
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.ToolBarWithBack
import presentation.viewmodels.BinToBinViewModel
import presentation.viewmodels.GlobalViewModel

@Composable
fun BinToBinScreen(navigator: Navigator, viewModel: BinToBinViewModel, platformUtils: PlatformUtils,globalViewModel: GlobalViewModel,localSharedStorage: LocalSharedStorage) {

    Scaffold(topBar = {
        ToolBarWithBack(navigator, StringResources.Apps.BinToBin.name)
    }) {

        Column (modifier = Modifier.fillMaxHeight().background(ColorResources.Background).padding(10.dp)){
            BinToBinByBinScreen(viewModel,platformUtils,globalViewModel,localSharedStorage)
        }
    }
}