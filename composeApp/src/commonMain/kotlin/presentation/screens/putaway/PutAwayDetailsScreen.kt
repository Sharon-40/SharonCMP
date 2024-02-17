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
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.CommonUtils
import data.PlatformUtils
import data.model.putaway.WarehouseTaskModel
import data.preferences.LocalSharedStorage
import presentation.components.DialogCustomCircleProgressbar
import presentation.components.NoDataView
import presentation.components.PrimaryButton
import presentation.components.SecondaryButton
import presentation.components.ToolBarWithBack
import presentation.custom_views.HorizontalCustomText
import presentation.custom_views.QRPickerTextField
import presentation.custom_views.VerticalCustomText
import presentation.viewmodels.PutAwayViewModel

class PutAwayDetailsScreen(private val warehouseTasks: List<WarehouseTaskModel>, private val viewModel: PutAwayViewModel, val localSharedStorage: LocalSharedStorage, private val platformUtils: PlatformUtils) : Screen {

    private val openWareHouseTasks = ArrayList<WarehouseTaskModel>()
    private val confirmedWareHouseTasks = ArrayList<WarehouseTaskModel>()

    private val tabs = listOf("Open Warehouse Task", "Confirmed Warehouse Task")

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        var selectedTabIndex by remember { mutableStateOf(0) }
        var isLoading by remember { mutableStateOf(false) }

        segregateTasks()

        Scaffold(topBar = {
            ToolBarWithBack({
                navigator.pop()
            }, StringResources.Apps.PutAway.name)
        }) {

            Column(modifier = StyleUtils.getStandardModifier()) {


                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    HorizontalCustomText(
                        headerText = StringResources.Plant,
                        valueText = localSharedStorage.getPlant()
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    HorizontalCustomText(
                        headerText = StringResources.Warehouse,
                        valueText = localSharedStorage.getWareHouse()
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    backgroundColor = Color.White,
                    contentColor = ColorResources.ColorAccent,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = ColorResources.ColorAccent
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (selectedTabIndex == 0) {
                    OpenLines()
                } else {
                    ConfirmedLines()
                }

                if (isLoading)
                {
                    DialogCustomCircleProgressbar()
                }

            }
        }

        LaunchedEffect(Unit)
        {

            viewModel._uiPutAwayTransferState.collect{
                when {

                    it.isLoading-> {
                        isLoading=true
                    }

                    it.error.isNotEmpty() -> {
                        isLoading=false
                        platformUtils.makeToast(it.error)
                    }

                    it.data!=null -> {
                        isLoading=false
                        if (it.successCount>0)
                        {
                            platformUtils.makeToast(it.successBuilder)
                        }else if (it.errorCount>0)
                        {
                            platformUtils.makeToast(it.errorBuilder)
                        }
                        navigator.pop()
                    }
                }
            }
        }
    }

    private fun segregateTasks() {
        openWareHouseTasks.clear()
        confirmedWareHouseTasks.clear()

        warehouseTasks.forEach {
            if (it.status == StringResources.WarehouseTaskStatus.OPEN_TASK) {
                openWareHouseTasks.add(it)
            } else if (it.status == StringResources.WarehouseTaskStatus.COMPLETED_TASK) {
                confirmedWareHouseTasks.add(it)
            }
        }
    }

    @Composable
    private fun OpenLines() {

        Column {

            if (openWareHouseTasks.isNotEmpty()) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LazyColumn(modifier = Modifier.fillMaxHeight(0.85f)) {

                        itemsIndexed(openWareHouseTasks) { index, item ->

                            var isChecked by remember { mutableStateOf(item.isSelected) }

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(3.dp).border(
                                    border = BorderStroke(1.dp, ColorResources.ColorPrimary),
                                    shape = RoundedCornerShape(8.dp)
                                ).padding(5.dp)
                            ) {

                                Checkbox(isChecked, {
                                    isChecked = it
                                    item.isSelected = it
                                    openWareHouseTasks[index].isSelected = it
                                })

                                Spacer(modifier = Modifier.width(2.dp))

                                Column {

                                    Row {
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.WarehouseTask,
                                            modifier = Modifier.weight(1f),
                                            valueText = "${item.woTaskId} - ${item.woline}"
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.Product,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.product
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.ProductDesc,
                                            modifier = Modifier.weight(2f),
                                            valueText = item.productDesc
                                        )
                                    }

                                    Row {
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.WarehouseOrder,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.wo
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.CreatedOn,
                                            modifier = Modifier.weight(1f),
                                            valueText = CommonUtils.getParseTDate(item.createdOn)
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.CreatedBy,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.createdBy
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.Batch,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.batch
                                        )
                                    }

                                    Row {
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.SourceStorageType,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.sourceStorageType
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.SourceBin,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.sourceBin
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.StockType,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.stockType
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.Qty,
                                            modifier = Modifier.weight(1f),
                                            valueText = "${item.qty} ${item.uom}"
                                        )

                                    }

                                    Row {

                                        openWareHouseTasks[index].selectedDestStorageType = item.destStorageType
                                        QRPickerTextField(
                                            headerText = StringResources.WareHouseTechTerms.DestinationStorageType,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.selectedDestStorageType?:item.destStorageType,
                                            onValueChange = {
                                                openWareHouseTasks[index].selectedDestStorageType = it
                                            })

                                        openWareHouseTasks[index].selectedDestBin = item.destBin
                                        QRPickerTextField(
                                            headerText = StringResources.WareHouseTechTerms.DestinationBin,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.selectedDestBin?:item.destBin,
                                            onValueChange = {
                                                openWareHouseTasks[index].selectedDestBin = it
                                            })

                                        openWareHouseTasks[index].enteredQty = item.qty
                                        QRPickerTextField(
                                            headerText = StringResources.WareHouseTechTerms.TransferQty,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.enteredQty?:item.qty,
                                            onValueChange = {
                                                openWareHouseTasks[index].enteredQty = it
                                            })

                                    }
                                }
                            }
                        }
                    }

                    Row {
                        SecondaryButton(StringResources.RePrint) {
                            if (viewModel.getSelectedData(openWareHouseTasks.toList()).isNotEmpty()) {
                                rePrint(viewModel.getSelectedData(openWareHouseTasks.toList()))
                            } else {
                                platformUtils.makeToast(StringResources.SelectAtLeastOne)
                            }
                        }

                        PrimaryButton(StringResources.Submit) {
                            if (viewModel.getSelectedData(openWareHouseTasks.toList()).isNotEmpty()) {
                                preparePayload(viewModel.getSelectedData(openWareHouseTasks.toList()))
                            } else {
                                platformUtils.makeToast(StringResources.SelectAtLeastOne)
                            }
                        }
                    }
                }
            }else{
                NoDataView()
            }
        }
    }

    @Composable
    private fun ConfirmedLines() {

        Column{

            if (confirmedWareHouseTasks.isNotEmpty())
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LazyColumn(modifier = Modifier.fillMaxHeight(0.85f)) {

                        itemsIndexed(confirmedWareHouseTasks) { index, item ->

                            var isChecked by remember { mutableStateOf(item.isSelected) }

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(3.dp).border(
                                    border = BorderStroke(1.dp, ColorResources.ColorPrimary),
                                    shape = RoundedCornerShape(8.dp)
                                ).padding(5.dp)
                            ) {

                                Checkbox(isChecked, {
                                    isChecked = it
                                    item.isSelected = it
                                    confirmedWareHouseTasks[index].isSelected = it
                                })

                                Spacer(modifier = Modifier.width(2.dp))

                                Column {

                                    Row {
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.WarehouseTask,
                                            modifier = Modifier.weight(1f),
                                            valueText = "${item.woTaskId} - ${item.woline}"
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.Product,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.product
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.ProductDesc,
                                            modifier = Modifier.weight(2f),
                                            valueText = item.productDesc
                                        )
                                    }

                                    Row {
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.WarehouseOrder,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.wo
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.ConfirmedOn,
                                            modifier = Modifier.weight(1f),
                                            valueText = CommonUtils.getParseTDate(item.completedDate)
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.ConfirmedBy,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.confirmedByUser?:""
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.Batch,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.batch
                                        )
                                    }

                                    Row {
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.SourceStorageType,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.sourceStorageType
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.SourceBin,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.sourceBin
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.StockType,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.stockType
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.Qty,
                                            modifier = Modifier.weight(1f),
                                            valueText = "${item.qty} ${item.uom}"
                                        )

                                    }

                                    Row {
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.DestinationStorageType,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.destStorageType
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.DestinationBin,
                                            modifier = Modifier.weight(1f),
                                            valueText = item.destBin
                                        )
                                        VerticalCustomText(
                                            headerText = StringResources.WareHouseTechTerms.TransferQty,
                                            modifier = Modifier.weight(2f),
                                            valueText = item.qty
                                        )
                                    }
                                }
                            }
                        }
                    }

                    SecondaryButton(StringResources.RePrint) {
                        if (viewModel.getSelectedData(confirmedWareHouseTasks.toList()).isNotEmpty()) {
                            rePrint(viewModel.getSelectedData(confirmedWareHouseTasks.toList()))
                        } else {
                            platformUtils.makeToast(StringResources.SelectAtLeastOne)
                        }
                    }
                }
            }else{
                NoDataView()
            }
        }
    }

    private fun rePrint(lines: List<WarehouseTaskModel>)
    {
        platformUtils.makeToast("Success")
    }

    private fun preparePayload(lines: List<WarehouseTaskModel>)
    {
        viewModel.preParePayload(lines)
    }


}