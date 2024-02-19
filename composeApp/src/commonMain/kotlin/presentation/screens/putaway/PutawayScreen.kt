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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.PlatformUtils
import data.logs.LogUtils
import data.model.putaway.WarehouseTaskModel
import data.preferences.LocalSharedStorage
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveIconButton
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.DialogCustomCircleProgressbar
import presentation.components.PrimaryButton
import presentation.components.ProfileHeaderView
import presentation.components.SecondaryButton
import presentation.components.ToolBarWithBack
import presentation.custom_views.ChipQRPickerTextField
import presentation.custom_views.VerticalCustomText
import presentation.viewmodels.PutAwayViewModel


class PutAwayScreen(private val preComposeNavigator: Navigator, private val viewModel: PutAwayViewModel, val localSharedStorage: LocalSharedStorage, private val platformUtils: PlatformUtils) : Screen {

    private val warehouseOrders = ArrayList<String>()
    private val warehouseTasks = ArrayList<String>()
    private val purchaseOrders = ArrayList<String>()
    private val inboundDeliveries = ArrayList<String>()
    private val products = ArrayList<String>()

    @Composable
    override fun Content()
    {
        val navigator = LocalNavigator.currentOrThrow

        var openWarehouseTasks: List<WarehouseTaskModel> by remember { mutableStateOf(emptyList()) }

        var showDialog by  remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }


        Scaffold(topBar = {
            ToolBarWithBack({
                preComposeNavigator.popBackStack()
            }, StringResources.Apps.PutAway.name)
        }) {

            Column (modifier = StyleUtils.getStandardModifier()) {

                if (platformUtils.isTablet())
                {
                    Row (horizontalArrangement = Arrangement.SpaceBetween , modifier = Modifier.fillMaxWidth()){

                        Row( modifier = Modifier.wrapContentWidth(), horizontalArrangement = Arrangement.Start) {
                            SecondaryButton(StringResources.Open_WHO+" ("+openWarehouseTasks.size+")" ) {
                                if (openWarehouseTasks.isNotEmpty())
                                {
                                    showDialog=true
                                }else{
                                    platformUtils.makeToast(StringResources.NoDataFound)
                                }
                            }
                        }

                        ProfileHeaderView(localSharedStorage)

                    }
                }else{

                    Column (horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()){

                        ProfileHeaderView(localSharedStorage)

                        SecondaryButton(StringResources.Open_WHO+" ("+openWarehouseTasks.size+")") {
                            if (openWarehouseTasks.isNotEmpty())
                            {
                                showDialog=true
                            }else{
                                platformUtils.makeToast(StringResources.NoDataFound)
                            }
                        }

                    }
                }



                Spacer(modifier = Modifier.height(5.dp))

                LazyColumn {
                    item {
                        Column {
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
                                        viewModel.getLines(localSharedStorage.getWareHouse(),warehouseOrders,warehouseOrders,purchaseOrders,inboundDeliveries,products)
                                    }
                                }
                            }
                        }
                    }
                }

                if (showDialog)
                {
                    OpenWhoDialog({ showDialog=it },openWarehouseTasks,platformUtils,viewModel){
                        navigator.push(PutAwayDetailsScreen(it,viewModel,localSharedStorage,platformUtils))
                    }
                }

                if (isLoading)
                {
                    DialogCustomCircleProgressbar()
                }
            }
        }

        LaunchedEffect(Unit){

            viewModel.getOpenWareHouseTasks(localSharedStorage.getWareHouse(),"")

            viewModel._openWhoState.collect{
                when {
                    it.isLoading -> {
                        isLoading=true
                    }

                    it.error.isNotEmpty() -> {
                        isLoading=false
                    }

                    it.data!=null ->{
                        isLoading=false
                        openWarehouseTasks=it.data
                    }
                }
            }
        }

        LaunchedEffect(Unit)
        {
            viewModel._uiState.collect{

                when {
                    it.isLoading -> {
                        isLoading=true
                    }

                    it.error.isNotEmpty() -> {
                        isLoading=false
                    }

                    it.data!=null ->{
                        isLoading=false
                        navigator.push(PutAwayDetailsScreen(it.data,viewModel,localSharedStorage,platformUtils))
                    }

                }
            }
        }

    }

    @OptIn(ExperimentalAdaptiveApi::class)
    @Composable
    fun OpenWhoDialog(setShowDialog: (Boolean) -> Unit, opensTasks:List<WarehouseTaskModel>, platformUtils: PlatformUtils, viewModel: PutAwayViewModel, selectedLines: (List<WarehouseTaskModel>)->Unit) {

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

                            AdaptiveIconButton(onClick = {
                                setShowDialog(false)
                            }){
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        if (opensTasks.isNotEmpty())
                        {
                            LazyColumn (modifier = Modifier.fillMaxHeight(0.85f)){

                                itemsIndexed(opensTasks){ index, item ->

                                    var isChecked  by remember { mutableStateOf(item.isSelected) }

                                    Row (modifier = Modifier.fillMaxWidth().padding(2.dp).border(border =  BorderStroke(1.dp, ColorResources.ColorPrimary), shape = RoundedCornerShape(8.dp)).padding(5.dp)){

                                        Checkbox(isChecked,{
                                            isChecked=it
                                            item.isSelected=it
                                            opensTasks[index].isSelected=it
                                        })

                                        Spacer(modifier = Modifier.width(1.dp))

                                        LazyVerticalGrid(columns = GridCells.Fixed(viewModel.getOpenWHOColumnCount()), modifier = Modifier.fillMaxWidth().height(viewModel.getOpenWHOColumnHeight()))
                                        {
                                            item {
                                                VerticalCustomText(headerText = StringResources.WareHouseTechTerms.WarehouseOrder, valueText = item.wo)
                                            }

                                            item{
                                                VerticalCustomText(headerText = StringResources.WareHouseTechTerms.InboundDelivery, valueText = item.ewmDelivery)
                                            }
                                            item{
                                                VerticalCustomText(headerText = StringResources.WareHouseTechTerms.PurchaseOrder, valueText = item.purchasingDocument)
                                            }
                                            item {
                                                VerticalCustomText(headerText = StringResources.WareHouseTechTerms.WarehouseTask, valueText = item.woTaskId)
                                            }

                                            item {
                                                VerticalCustomText(headerText = StringResources.WareHouseTechTerms.Product, valueText = item.product)
                                            }

                                            item {
                                                VerticalCustomText(headerText = StringResources.WareHouseTechTerms.CreatedBy, valueText = item.createdBy)
                                            }
                                        }
                                    }
                                }
                            }

                            PrimaryButton(StringResources.Select) {
                                if (viewModel.getSelectedData(opensTasks.toList()).isNotEmpty())
                                {
                                   selectedLines(viewModel.getSelectedData(opensTasks.toList()))
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

}

