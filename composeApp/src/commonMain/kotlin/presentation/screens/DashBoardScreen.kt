package presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.models.AppModel
import ColorResources
import Strings

@Composable
fun DashBoardScreen(onClick: (AppModel) -> Unit) {

    val apps= arrayListOf(AppModel(Strings.Apps.PutAway.name),AppModel(Strings.Apps.BinToBin.name))

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
                Text(text = Strings.AppName, style = MaterialTheme.typography.h6, color = White)
                Image(imageVector = Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.clickable {})
            }

        }
    }) {

        Column (modifier = Modifier.fillMaxHeight().background(ColorResources.Background).padding(10.dp)) {

            Text(text = Strings.WareHouseTransactions, style = TextStyle(color = Black, fontSize = 16.sp, fontWeight = FontWeight.Bold))

            Spacer(modifier = Modifier.height(5.dp))

            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                items(apps) {
                    Column(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight()
                            .clickable {
                                onClick(it)
                            }
                            .padding(2.dp).border(1.dp, ColorResources.ColorAccent, RoundedCornerShape(5.dp)).background(White).padding(10.dp)
                    ) {

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = it.appName,
                            style = TextStyle(color = ColorResources.ColorAccent,fontSize = 12.sp, fontWeight = FontWeight.Bold),
                            overflow = TextOverflow.Ellipsis, maxLines = 2
                        )

                    }
                }
            }
        }
    }


}