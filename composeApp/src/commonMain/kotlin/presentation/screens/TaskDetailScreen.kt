package presentation.screens

import ColorResources
import StyleUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.CustomCircleProgressbar
import presentation.components.ToolBarWithBack
import presentation.custom_views.HorizontalCustomText
import presentation.viewmodels.ProductListViewModel

@Composable
fun TaskDetailScreen(
    viewModel: ProductListViewModel,
    navigator: Navigator,
    taskId: String,
    onClick: (Int) -> Unit
) {

    LaunchedEffect(Unit)
    {
        viewModel.getTasksDetails(taskId)
    }


    val uiState = viewModel.uiDetailsState.collectAsState()

    Scaffold(topBar = {
        ToolBarWithBack(navigator, "Task Details")
    }) {

        when {
            uiState.value.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CustomCircleProgressbar()
                }
            }

            uiState.value.error.isNotEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = uiState.value.error)
                }
            }

            else -> {

                Column(modifier = Modifier.padding(10.dp)) {

                    Text(
                        uiState.value.data?.taskEventDto?.subject.toString(),
                        style = StyleUtils.getBoldFontStyle()
                    )

                    Divider(color = Color.Black, thickness = 1.dp)

                    LazyColumn (modifier = Modifier.fillMaxHeight(.9f)) {
                        uiState.value.data?.customAttr?.let { list ->

                            itemsIndexed(list) { index, item ->

                                if (item.label != null) {

                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        HorizontalCustomText(
                                            headerText = item.label ?: "-",
                                            valueText = item.labelValue ?: "-",
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }

                    }

                    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween ){

                        IconButton(onClick = {  }) {
                            Column (horizontalAlignment = Alignment.CenterHorizontally){
                                Icon(Icons.Default.Done,contentDescription = null, tint = ColorResources.ColorPrimary)
                                Text("Mark Resolved")
                            }
                        }

                        IconButton(onClick = {  }) {
                            Column (horizontalAlignment = Alignment.CenterHorizontally){
                                Icon(Icons.Default.ArrowBack,contentDescription = null, tint = ColorResources.ColorPrimary)
                                Text("Return")
                            }
                        }

                        IconButton(onClick = {  }) {
                            Column (horizontalAlignment = Alignment.CenterHorizontally){
                                Icon(Icons.Default.Send,contentDescription = null, tint = ColorResources.ColorPrimary)
                                Text("Comment")
                            }
                        }

                        IconButton(onClick = {  }) {
                            Column (horizontalAlignment = Alignment.CenterHorizontally){
                                Icon(Icons.Default.Share,contentDescription = null, tint = ColorResources.ColorPrimary)
                                Text("Attach")
                            }
                        }

                    }

                }


            }

        }
    }


}