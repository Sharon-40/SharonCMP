package presentation.screens

import ColorResources
import StyleUtils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.CustomCircleProgressbar
import presentation.components.ToolBarWithBack
import presentation.custom_views.HorizontalCustomText
import presentation.viewmodels.ProductListViewModel

@Composable
fun TaskListScreen(
    viewModel: ProductListViewModel,
    navigator: Navigator,
    onClick: (String) -> Unit
) {


    LaunchedEffect(Unit)
    {
        viewModel.getTasks()
    }

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(topBar = {
        ToolBarWithBack(navigator, "Tasks")
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

                LazyColumn {
                    uiState.value.data?.let { list ->
                        itemsIndexed(list) { index, item ->

                            Card(modifier = Modifier.padding(10.dp), elevation = 10.dp) {

                                Row {

                                    Divider( modifier = Modifier.width(5.dp).height(100.dp).padding(3.dp), color = ColorResources.ColorPrimary , thickness = 1.dp)

                                    Column(
                                        modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                            .clickable {
                                                onClick(item.taskId)
                                            }
                                            .padding(12.dp)
                                    ) {

                                        Text(
                                            item.description,
                                            style = StyleUtils.getBoldFontStyle()
                                        )

                                        HorizontalCustomText(
                                            "Created By : ",
                                            valueText = item.createdBy ?: ""
                                        )

                                        HorizontalCustomText(
                                            "Start Time : ",
                                            valueText = item.startTime ?: ""
                                        )

                                        HorizontalCustomText(
                                            "Distance : ",
                                            valueText = item.distance ?: "-"
                                        )

                                        Box(
                                            modifier = Modifier.border(border = BorderStroke(1.dp,ColorResources.ColorPrimary), shape = RoundedCornerShape(4.dp)).background(ColorResources.ColorPrimary).padding(10.dp)
                                        ) {
                                            Text(item.status, style = TextStyle(color = Color.White))
                                        }

                                    }


                                }
                            }

                        }
                    }

                }
            }

        }
    }


}