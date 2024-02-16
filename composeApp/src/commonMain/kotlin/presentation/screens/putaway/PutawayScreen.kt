package presentation.screens.putaway

import StringResources
import StyleUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.PlatformUtils
import data.logs.LogUtils
import data.preferences.LocalSharedStorage
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.PrimaryButton
import presentation.components.ToolBarWithBack
import presentation.custom_views.ChipQRPickerTextField
import presentation.custom_views.HorizontalCustomText

@Composable
fun PutAwayScreen(navigator: Navigator, localSharedStorage: LocalSharedStorage,platformUtils: PlatformUtils) {

    val warehouseOrders = ArrayList<String>()
    val warehouseTasks = ArrayList<String>()
    val purchaseOrders = ArrayList<String>()
    val inboundDeliveries = ArrayList<String>()
    val products = ArrayList<String>()

    Scaffold(topBar = {
        ToolBarWithBack(navigator, StringResources.Apps.PutAway.name)
    }) {

        Column (modifier = StyleUtils.getStandardModifier()) {


            Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                HorizontalCustomText(headerText = StringResources.Plant, valueText = localSharedStorage.getPlant())
                Spacer(modifier = Modifier.width(2.dp))
                HorizontalCustomText(headerText = StringResources.Warehouse, valueText = localSharedStorage.getWareHouse())
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
        }
    }


}