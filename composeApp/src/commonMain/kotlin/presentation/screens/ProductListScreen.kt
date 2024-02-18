package presentation.screens

import StringResources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.CustomCircleProgressbar
import presentation.components.ToolBarWithBack
import presentation.viewmodels.ProductListViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    navigator: Navigator,
    onClick: (Int) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(topBar = {
        ToolBarWithBack(navigator,StringResources.Warehouse)
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
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    uiState.value.data?.let { list ->
                        items(list) {
                            Column(
                                modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                    .clickable {
                                        onClick(it.id)
                                    }
                                    .padding(12.dp)
                            ) {
                                KamelImage(
                                    modifier = Modifier.fillMaxWidth().height(200.dp),
                                    resource = asyncPainterResource(it.image),
                                    contentDescription = null
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = it.title,
                                    style = MaterialTheme.typography.body1,
                                    overflow = TextOverflow.Ellipsis, maxLines = 2
                                )

                            }
                        }
                    }

                }
            }

        }
    }


}