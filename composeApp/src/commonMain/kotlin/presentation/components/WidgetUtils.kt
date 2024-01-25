package presentation.components

import ColorResources
import Utils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ProfileListTile(leadingIcon:ImageVector,title:String,desc:String)
{
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().border(1.dp,
        ColorResources.ColorAccent, RoundedCornerShape(5.dp)).background(
        Color.White
    ).padding(10.dp)) {
        Image(imageVector = leadingIcon, contentDescription = null, colorFilter = ColorFilter.tint(color = ColorResources.ColorAccent))

        Spacer(modifier = Modifier.width(5.dp))

        Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
            Text(text = title, style = Utils.headerFontStyle())
            Text(text = desc, style = Utils.valueFontStyle())
        }

    }
}

@Composable
fun ToolBarWithBack(navigator: Navigator,title: String)
{
    TopAppBar(
        contentColor = Color.White,
        backgroundColor = ColorResources.ColorPrimary
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null, colorFilter = ColorFilter.tint(color = Color.White),modifier = Modifier.clickable {
                navigator.popBackStack()
            })
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = title, style = Utils.valueFontStyle(), color = Color.White)
        }
    }
}