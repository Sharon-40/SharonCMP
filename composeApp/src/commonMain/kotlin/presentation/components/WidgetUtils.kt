package presentation.components

import ColorResources
import StringResources
import StyleUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveButton
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveCircularProgressIndicator
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTopAppBar
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import moe.tlaster.precompose.navigation.Navigator


@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun CustomCircleProgressbar(modifier: Modifier=Modifier.fillMaxSize())
{
    Box(modifier = modifier , contentAlignment = Alignment.Center) {
        AdaptiveCircularProgressIndicator(adaptationScope ={
            material {
                color=ColorResources.ColorPrimary
            } } )
    }
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun DialogCustomCircleProgressbar()
{
    Dialog(onDismissRequest = {}){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AdaptiveCircularProgressIndicator(adaptationScope ={
                material {
                    color=Color.White
                } } )
        }
    }
}

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
            Text(text = title, style = StyleUtils.getRegularFontStyle())
            Text(text = desc, style = StyleUtils.getBoldFontStyle())
        }

    }
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun ToolBar(title: String)
{
    AdaptiveTopAppBar(
        title = { Text(title,style = StyleUtils.getBoldFontStyle()) }
    )
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun ToolBarWithBack(navigator: Navigator,title: String)
{
    AdaptiveTopAppBar(
        title = { Text(title,style = StyleUtils.getBoldFontStyle()) },
        navigationIcon = {
            Image(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null, colorFilter = ColorFilter.tint(color = ColorResources.ColorPrimary),modifier = Modifier.clickable {
                navigator.popBackStack()
            })
        }
    )
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun ToolBarWithBack(onBackPressed:() ->Unit ,title: String)
{
    AdaptiveTopAppBar(
        title = { Text(title,style = StyleUtils.getBoldFontStyle()) },
        navigationIcon = {
            Image(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null, colorFilter = ColorFilter.tint(color = ColorResources.ColorAccent),modifier = Modifier.clickable {
                onBackPressed()
            })
        }
    )
}


@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun PrimaryButton(text: String, onClick: () -> Unit = {}) {
    AdaptiveButton(modifier = Modifier.width(150.dp).padding(5.dp),onClick = { onClick() })
    {
        Text(text = text, style = TextStyle(color = Color.White, fontFamily = StyleUtils.getSemiBoldFont(), fontWeight = FontWeight.SemiBold))
    }
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun SecondaryButton(text: String,modifier: Modifier=Modifier.width(150.dp).padding(5.dp), onClick: () -> Unit = {}) {
    AdaptiveButton(modifier = modifier ,onClick = { onClick() })
    {
        Text(text = text, style = TextStyle(color = Color.White, fontFamily = StyleUtils.getSemiBoldFont(), fontWeight = FontWeight.SemiBold))
    }
}

@Composable
fun NoDataView()
{
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = StringResources.NoDataFound, style = StyleUtils.getBoldFontStyle())
    }
}
