package presentation.screens.putaway

import ColorResources
import StringResources
import StyleUtils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import data.PlatformUtils
import data.logs.LogUtils
import data.model.WarehouseTaskModel
import data.preferences.LocalSharedStorage
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.PrimaryButton
import presentation.components.SecondaryButton
import presentation.components.ToolBarWithBack
import presentation.custom_views.ChipQRPickerTextField
import presentation.custom_views.HorizontalCustomText
import presentation.custom_views.VerticalCustomText
import presentation.viewmodels.PutAwayViewModel

@Composable
fun PutAwayScreen(navigator: Navigator, localSharedStorage: LocalSharedStorage,platformUtils: PlatformUtils,viewModel: PutAwayViewModel) {

    val warehouseOrders = ArrayList<String>()
    val warehouseTasks = ArrayList<String>()
    val purchaseOrders = ArrayList<String>()
    val inboundDeliveries = ArrayList<String>()
    val products = ArrayList<String>()

    var openWarehouseTasks: List<WarehouseTaskModel> by remember { mutableStateOf(emptyList()) }

    var showDialog by  remember { mutableStateOf(false) }

    Scaffold(topBar = {
        ToolBarWithBack(navigator, StringResources.Apps.PutAway.name)
    }) {

        Column (modifier = StyleUtils.getStandardModifier()) {


            Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                HorizontalCustomText(headerText = StringResources.Plant, valueText = localSharedStorage.getPlant())
                Spacer(modifier = Modifier.width(2.dp))
                HorizontalCustomText(headerText = StringResources.Warehouse, valueText = localSharedStorage.getWareHouse())
            }

            Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                SecondaryButton(StringResources.Open_WHO) {
                    if (openWarehouseTasks.isNotEmpty())
                    {
                        showDialog=true
                    }else{
                        platformUtils.makeToast(StringResources.NoDataFound)
                    }
                }
            }


            Spacer(modifier = Modifier.height(5.dp))

            ChipQRPickerTextField (headerText = StringResources.WareHouseTechTerms.WarehouseOrder, validation = true, validationType = StringResources.ValidationTypes.ValidationType_PutAway_WarehouseOrder, onChipSelected = {
                warehouseOrders.clear()
                warehouseOrders.addAll(it)
            })

            Spacer(modifier = Modifier.height(5.dp))

            ChipQRPickerTextField(headerText = StringResources.WareHouseTechTerms.WarehouseTask,validation = true, validationType = StringResources.ValidationTypes.ValidationType_PutAway_WarehouseTask , onChipSelected = {
                warehouseTasks.clear()
                warehouseTasks.addAll(it)
            })

            Spacer(modifier = Modifier.height(5.dp))

            ChipQRPickerTextField(headerText = StringResources.WareHouseTechTerms.PurchaseOrder,validation = true, validationType = StringResources.ValidationTypes.ValidationType_PutAway_PurchaseOrder , onChipSelected = {
                purchaseOrders.clear()
                purchaseOrders.addAll(it)
            })

            Spacer(modifier = Modifier.height(5.dp))

            ChipQRPickerTextField(headerText = StringResources.WareHouseTechTerms.InboundDelivery,validation = true, validationType = StringResources.ValidationTypes.ValidationType_PutAway_Inbound , onChipSelected = {
                inboundDeliveries.clear()
                inboundDeliveries.addAll(it)
            })

            Spacer(modifier = Modifier.height(5.dp))

            ChipQRPickerTextField(headerText = StringResources.WareHouseTechTerms.ProductId,validation = true, validationType = StringResources.ValidationTypes.ValidationType_PutAway_Product, onChipSelected = {
                products.clear()
                products.addAll(it)
            })

            Spacer(modifier = Modifier.height(5.dp))

            Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){

                PrimaryButton(StringResources.Execute) {
                    LogUtils.logDebug(StringResources.RESPONSE,warehouseOrders.toString())
                    if (warehouseOrders.isEmpty() && warehouseTasks.isEmpty() && purchaseOrders.isEmpty() && inboundDeliveries.isEmpty() && products.isEmpty())
                    {
                        platformUtils.makeToast(StringResources.Enter_one_of_the_below_field)
                    }else{
                        platformUtils.makeToast("Success")
                    }
                }
            }

            if (showDialog)
            {
                OpenWhoDialog({ showDialog=it },openWarehouseTasks,platformUtils,viewModel)
            }
        }
    }

    LaunchedEffect(Unit){

        viewModel.getOpenWareHouseTasks(localSharedStorage.getWareHouse(),"")

        viewModel._openWhoState.collect{
            when {
                it.data!=null ->{
                    openWarehouseTasks=it.data
                }
            }
        }

    }


}

@Composable
fun OpenWhoDialog(setShowDialog: (Boolean) -> Unit , opensTasks:List<WarehouseTaskModel>,platformUtils: PlatformUtils,viewModel: PutAwayViewModel) {

    Dialog(onDismissRequest = { setShowDialog(false) }) {

        Surface(shape = RoundedCornerShape(16.dp), color = Color.White) {

            Box(contentAlignment = Alignment.Center) {

                Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                    Row(
                        modifier = Modifier.fillMaxWidth().height(40.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Open Warehouse Tasks (${opensTasks.size})",
                            style = StyleUtils.getBoldFontStyle(color = ColorResources.ColorPrimary)
                        )

                        IconButton(onClick = {
                            setShowDialog(false)
                        }){
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = null,
                                tint = ColorResources.ColorPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    if (opensTasks.isNotEmpty())
                    {
                        LazyColumn (modifier = Modifier.fillMaxHeight(0.85f)){

                            itemsIndexed(opensTasks){ index, item ->

                                var isChecked  by remember { mutableStateOf(item.isSelected) }

                                Row (modifier = Modifier.fillMaxWidth().padding(3.dp).border(border =  BorderStroke(1.dp, ColorResources.ColorPrimary), shape = RoundedCornerShape(8.dp)).padding(5.dp)){

                                    Checkbox(isChecked,{
                                        isChecked=it
                                        item.isSelected=it
                                        opensTasks[index].isSelected=it
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
                            if (viewModel.getSelectedData(opensTasks.toList()).isNotEmpty())
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
}