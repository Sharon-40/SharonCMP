package presentation.screens

import ColorResources
import Strings
import Utils
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import data.prefrences.LocalSharedStorage
import domain.models.AppModel
import presentation.ImageResources

@Composable
fun ProfileScreen(localSharedStorage: LocalSharedStorage) {

    Scaffold(topBar = {
        TopAppBar(
            contentColor = White,
            backgroundColor = ColorResources.ColorPrimary
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = Strings.Profile, style = MaterialTheme.typography.h6, color = White)
            }
        }
    }) {

        Column (modifier = Modifier.fillMaxSize().background(ColorResources.Background).padding(10.dp)) {

            Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth().border(1.dp, ColorResources.ColorAccent, RoundedCornerShape(5.dp)).background(White).padding(10.dp)) {
                Text(text = Strings.UserName, style = Utils.headerFontStyle())
                Text(text = localSharedStorage.getUserName(), style = Utils.valueFontStyle())
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(horizontalArrangement = Arrangement.SpaceAround,modifier = Modifier.fillMaxWidth().border(1.dp, ColorResources.ColorAccent, RoundedCornerShape(5.dp)).background(White).padding(10.dp)) {
                Text(text = Strings.Plant, style = Utils.headerFontStyle())
                Text(text = localSharedStorage.getPlant(), style = Utils.valueFontStyle())
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(horizontalArrangement = Arrangement.SpaceAround,modifier = Modifier.fillMaxWidth().border(1.dp, ColorResources.ColorAccent, RoundedCornerShape(5.dp)).background(White).padding(10.dp)) {
                Text(text = Strings.Warehouse, style = Utils.headerFontStyle())
                Text(text = localSharedStorage.getWareHouse(), style = Utils.valueFontStyle())
            }

        }
    }


}