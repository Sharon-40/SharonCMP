package presentation.screens.bintobin

import ColorResources
import StringResources
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.PlatformUtils
import data.model.StockModel
import presentation.components.CustomCircleProgressbar
import presentation.components.PrimaryButton
import presentation.custom_views.QRPickerTextField
import presentation.custom_views.VerticalCustomText
import presentation.viewmodels.BinToBinViewModel

@Composable
fun BinToBinByBinScreen(viewModel: BinToBinViewModel, platformUtils: PlatformUtils)
{

    var isLoading by remember { mutableStateOf(false) }

    var enteredBin by remember { mutableStateOf("") }
    var stockData:ArrayList<StockModel> by remember { mutableStateOf(ArrayList()) }

    val uiState = viewModel.uiState.collectAsState()
    val uiBinTransferState = viewModel.uiBinTransferState.collectAsState()

    Column(modifier = Modifier.fillMaxWidth().padding(2.dp)) {


        Column(modifier = Modifier.fillMaxWidth().padding(top = 5.dp) , horizontalAlignment = Alignment.CenterHorizontally) {

            QRPickerTextField (headerText = StringResources.WareHouseTechTerms.Bin, valueText = enteredBin, onValueChange = {
                enteredBin=it
            })

            PrimaryButton(StringResources.Execute) {
                if (enteredBin.isNotEmpty())
                {
                    viewModel.getStockByBin(enteredBin)
                }else{
                    platformUtils.makeToast(StringResources.InvalidBin)
                }
            }

            if (isLoading)
            {
                CustomCircleProgressbar()
            }

            if (stockData.isNotEmpty())
            {
                LazyColumn (modifier = Modifier.fillMaxHeight(0.85f)){

                    itemsIndexed(stockData){ index, item ->

                        var isChecked  by remember { mutableStateOf(item.isSelected) }


                        Row (modifier = Modifier.fillMaxWidth().padding(3.dp).border(border =  BorderStroke(1.dp, ColorResources.ColorPrimary), shape = RoundedCornerShape(8.dp)).padding(5.dp)){

                            Checkbox(isChecked,{
                                isChecked=it
                                item.isSelected=it
                                stockData[index].isSelected=it
                            })

                            Spacer(modifier = Modifier.width(2.dp))

                            Column {
                                Row {
                                    VerticalCustomText(headerText = StringResources.WareHouseTechTerms.Product,modifier = Modifier.weight(1f), valueText = item.product)
                                    VerticalCustomText(headerText = StringResources.WareHouseTechTerms.ProductDesc,modifier = Modifier.weight(2f), valueText = item.productDesc)
                                }

                                Row {
                                    VerticalCustomText(headerText = StringResources.WareHouseTechTerms.Qty,modifier = Modifier.weight(1f), valueText = item.qty)
                                    VerticalCustomText(headerText = StringResources.WareHouseTechTerms.Uom,modifier = Modifier.weight(1f), valueText = item.uom)
                                    VerticalCustomText(headerText = StringResources.WareHouseTechTerms.StockType,modifier = Modifier.weight(1f), valueText = item.stockType)
                                }

                                Row {

                                    VerticalCustomText(headerText = StringResources.WareHouseTechTerms.Batch,modifier = Modifier.weight(1f), valueText = item.batch)

                                    QRPickerTextField ( headerText = StringResources.WareHouseTechTerms.TransferQty,modifier = Modifier.weight(1f), onValueChange = {
                                        stockData[index].enteredQty=it
                                    })

                                }


                                Row {
                                    QRPickerTextField (headerText = StringResources.WareHouseTechTerms.DestinationStorageType,modifier = Modifier.weight(1f), onValueChange = {
                                        stockData[index].selectedDestStorageType=it
                                    })

                                    QRPickerTextField (headerText = StringResources.WareHouseTechTerms.DestinationBin,modifier = Modifier.weight(1f), onValueChange = {
                                        stockData[index].selectedDestBin=it
                                    })
                                }
                            }
                        }
                    }
                }

                PrimaryButton(StringResources.Submit) {
                    if (viewModel.getSelectedData(stockData.toList()).isNotEmpty())
                    {
                        viewModel.prePareItemPayload("VERP",viewModel.getSelectedData(stockData.toList()))
                    }else{
                        platformUtils.makeToast(StringResources.SelectAtleastOne)
                    }
                }
            }
        }

    }

    when {
        uiState.value.isLoading-> {
            isLoading=true
        }

        uiState.value.error.isNotEmpty() -> {
            isLoading=false
            platformUtils.makeToast(uiState.value.error)
        }

        uiState.value.data!=null ->{
            isLoading=false
            stockData=uiState.value.data as ArrayList<StockModel>
            //stockData.addAll(uiState.value.data!!)
        }

    }

    when {
        uiBinTransferState.value.isLoading-> {
            isLoading=true
        }

        uiBinTransferState.value.error.isNotEmpty() -> {
            isLoading=false
            platformUtils.makeToast(uiBinTransferState.value.error)
        }

        uiBinTransferState.value.data!=null ->{
            isLoading=false
            if (uiBinTransferState.value.successCount>0)
            {
                platformUtils.makeToast(uiBinTransferState.value.successBuilder)
            }else if (uiBinTransferState.value.errorCount>0)
            {
                platformUtils.makeToast(uiBinTransferState.value.errorBuilder)
            }
            viewModel.getStockByBin(enteredBin)
        }

    }

}
