package presentation.screens

import ColorResources
import ImageResources
import StringResources
import StyleUtils
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.models.AppModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.navigation.NavigationRoute

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DashBoardScreen(navigator: Navigator) {

    val apps= arrayListOf(AppModel(StringResources.Apps.GoodsReceiving.name,ImageResources.goods_recieveing,NavigationRoute.ProductList.route),AppModel(StringResources.Apps.PutAway.name,ImageResources.icon_putaway,NavigationRoute.PutAway.route),AppModel(StringResources.Apps.BinToBin.name,ImageResources.icon_bin_to_bin,NavigationRoute.BinToBin.route))

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
                Text(text = StringResources.AppName, style = StyleUtils.getBoldFontStyle(), color = White)
                Image(imageVector = Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.clickable {
                    navigator.navigate(NavigationRoute.Profile.route)
                }, colorFilter = ColorFilter.tint(color = White))
            }

        }
    }) {

        Column (modifier = Modifier.fillMaxHeight().background(ColorResources.Background).padding(10.dp)) {

            Text(text = StringResources.WareHouseTransactions, style = StyleUtils.getBoldFontStyle())

            Spacer(modifier = Modifier.height(5.dp))

            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                items(apps) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth().wrapContentHeight()
                            .clickable {
                                navigator.navigate(it.navigatorSceneName)
                            }
                            .padding(2.dp).border(1.dp, ColorResources.ColorAccent, RoundedCornerShape(5.dp)).background(White).padding(10.dp)
                    ) {

                        Image(
                            painter = painterResource(it.icon),
                            null, modifier = Modifier.height(60.dp).width(60.dp)
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = it.appName,
                            style = TextStyle(color = ColorResources.ColorAccent,fontSize = 12.sp, fontFamily = StyleUtils.getBoldFont(), fontWeight = FontWeight.Bold),
                            overflow = TextOverflow.Ellipsis, maxLines = 2
                        )

                    }
                }
            }
        }
    }


}