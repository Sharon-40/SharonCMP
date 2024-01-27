package presentation.screens.putaway

import StringResources
import Utils
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
import data.preferences.LocalSharedStorage
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.PrimaryButton
import presentation.components.ToolBarWithBack
import presentation.custom_views.HorizontalCustomText
import presentation.custom_views.QRPickerTextFiled

@Composable
fun PutawayScreen(navigator: Navigator,localSharedStorage: LocalSharedStorage) {

    Scaffold(topBar = {
        ToolBarWithBack(navigator, StringResources.Apps.PutAway.name)
    }) {

        Column (modifier = Utils.getStandardModifier()) {


            Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                HorizontalCustomText(headerText = StringResources.Plant, valueText = localSharedStorage.getPlant())
                Spacer(modifier = Modifier.width(2.dp))
                HorizontalCustomText(headerText = StringResources.Warehouse, valueText = localSharedStorage.getWareHouse())
            }

            Spacer(modifier = Modifier.height(5.dp))

            QRPickerTextFiled(headerText = StringResources.WareHouseTechTerms.WarehouseOrder, validation = true, validationType = StringResources.ValidationTypes.ValidationType_PutAway_WarehouseOrder)

            Spacer(modifier = Modifier.height(5.dp))

            QRPickerTextFiled(headerText = StringResources.WareHouseTechTerms.WarehouseTask,validation = true, validationType = StringResources.ValidationTypes.ValidationType_PutAway_WarehouseTask)

            Spacer(modifier = Modifier.height(5.dp))

            QRPickerTextFiled(headerText = StringResources.WareHouseTechTerms.PurchaseOrder,validation = true, validationType = StringResources.ValidationTypes.ValidationType_PutAway_PurchaseOrder)

            Spacer(modifier = Modifier.height(5.dp))

            QRPickerTextFiled(headerText = StringResources.WareHouseTechTerms.InboundDelivery,validation = true, validationType = StringResources.ValidationTypes.ValidationType_PutAway_Inbound)

            Spacer(modifier = Modifier.height(5.dp))

            QRPickerTextFiled(headerText = StringResources.WareHouseTechTerms.ProductId,validation = true, validationType = StringResources.ValidationTypes.ValidationType_PutAway_Product)

            Spacer(modifier = Modifier.height(5.dp))

            Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){

                PrimaryButton(StringResources.Execute) {

                }
            }
        }
    }


}