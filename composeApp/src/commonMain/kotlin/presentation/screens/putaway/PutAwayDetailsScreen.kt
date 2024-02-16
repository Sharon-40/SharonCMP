package presentation.screens.putaway

import ColorResources
import StringResources
import StyleUtils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.PlatformUtils
import data.model.WarehouseTaskModel
import data.preferences.LocalSharedStorage
import moe.tlaster.precompose.koin.koinViewModel
import org.koin.compose.koinInject
import presentation.components.PrimaryButton
import presentation.components.ToolBarWithBack
import presentation.custom_views.HorizontalCustomText
import presentation.custom_views.VerticalCustomText
import presentation.viewmodels.PutAwayViewModel

class PutAwayDetailsScreen(private val warehouseTasks:List<WarehouseTaskModel>) : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val viewModel: PutAwayViewModel = koinViewModel(PutAwayViewModel::class)
        val localSharedStorage: LocalSharedStorage = koinInject()
        val platformUtils: PlatformUtils = koinInject()


        Scaffold(topBar = {
            ToolBarWithBack({
                navigator.pop()
            }, StringResources.Apps.PutAway.name)
        }) {

            Column (modifier = StyleUtils.getStandardModifier()) {


                Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    HorizontalCustomText(headerText = StringResources.Plant, valueText = localSharedStorage.getPlant())
                    Spacer(modifier = Modifier.width(2.dp))
                    HorizontalCustomText(headerText = StringResources.Warehouse, valueText = localSharedStorage.getWareHouse())
                }

                Spacer(modifier = Modifier.height(5.dp))

                if (warehouseTasks.isNotEmpty())
                {
                    LazyColumn (modifier = Modifier.fillMaxHeight(0.85f)){

                        itemsIndexed(warehouseTasks){ index, item ->

                            var isChecked  by remember { mutableStateOf(item.isSelected) }

                            Row (modifier = Modifier.fillMaxWidth().padding(3.dp).border(border =  BorderStroke(1.dp, ColorResources.ColorPrimary), shape = RoundedCornerShape(8.dp)).padding(5.dp)){

                                Checkbox(isChecked,{
                                    isChecked=it
                                    item.isSelected=it
                                    warehouseTasks[index].isSelected=it
                                })

                                Spacer(modifier = Modifier.width(2.dp))

                                Column {

                                    Row {
                                        VerticalCustomText(headerText = StringResources.WareHouseTechTerms.WarehouseOrder,modifier = Modifier.weight(1f), valueText = item.wo)
                                        VerticalCustomText(headerText = StringResources.WareHouseTechTerms.InboundDelivery,modifier = Modifier.weight(1f), valueText = item.ewmDelivery)
                                        VerticalCustomText(headerText = StringResources.WareHouseTechTerms.PurchaseOrder,modifier = Modifier.weight(1f), valueText = item.purchasingDocument)
                                    }

                                    Row {
                                        VerticalCustomText(headerText = StringResources.WareHouseTechTerms.WarehouseTask,modifier = Modifier.weight(1f), valueText = item.woTaskId)
                                        VerticalCustomText(headerText = StringResources.WareHouseTechTerms.Product,modifier = Modifier.weight(1f), valueText = item.product)
                                        //VerticalCustomText(headerText = StringResources.WareHouseTechTerms.CreatedOn,modifier = Modifier.weight(1f), valueText = item.createdOn)
                                        VerticalCustomText(headerText = StringResources.WareHouseTechTerms.CreatedBy,modifier = Modifier.weight(1f), valueText = item.createdBy)
                                    }
                                }
                            }
                        }
                    }

                    PrimaryButton(StringResources.Select) {
                        if (viewModel.getSelectedData(warehouseTasks.toList()).isNotEmpty())
                        {
                            platformUtils.makeToast("Success")
                        }else{
                            platformUtils.makeToast(StringResources.SelectAtLeastOne)
                        }
                    }
                }

            }
        }
    }
}