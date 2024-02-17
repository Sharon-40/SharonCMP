package presentation.viewmodels

import StringResources
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import data.PlatformUtils
import data.logs.LogUtils
import data.model.container.ConfirmTaskResponse
import data.model.putaway.ConfirmWareHouseTaskModel
import data.model.putaway.WarehouseTaskModel
import data.preferences.LocalSharedStorage
import data.utils.NetworkResult
import domain.use_cases.MainUseCase
import join
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class PutAwayTransferStateHolder(
    val isLoading: Boolean = false,
    val data: List<ConfirmTaskResponse>? = null,
    val error: String = "",
    val successBuilder : String="",
    var successCount:Int = 0,
    val errorBuilder: String="",
    var errorCount:Int = 0
)

class PutAwayViewModel(private val mainUseCase: MainUseCase,val localSharedStorage: LocalSharedStorage,val platformUtils: PlatformUtils) : ViewModel() {

    val _openWhoState = MutableSharedFlow<WarehouseTasksStateHolder>()

    val _uiState = MutableSharedFlow<WarehouseTasksStateHolder>()

    val _uiPutAwayTransferState = MutableSharedFlow<PutAwayTransferStateHolder>()

    fun getOpenWareHouseTasks(warehouse:String,status:String?) = mainUseCase.getPutAwayWarehouseTasks(warehouse,null,null,null,null,null,status).onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _openWhoState.emit( WarehouseTasksStateHolder(isLoading = true) )
            }

            is NetworkResult.Success -> {
                LogUtils.logDebug(StringResources.RESPONSE,res.data.toString())
                _openWhoState.emit( WarehouseTasksStateHolder(data = res.data) )
            }

            is NetworkResult.Error -> {
                LogUtils.logDebug(StringResources.RESPONSE, res.message)
                _openWhoState.emit( WarehouseTasksStateHolder(error = res.message) )
            }
        }
    }.launchIn(viewModelScope)

    fun getSelectedData(items:List<WarehouseTaskModel>): List<WarehouseTaskModel> {
        return items.filter { it.isSelected }
    }

    fun getLines(
        warehouse: String,
        warehouseOrders: ArrayList<String> = ArrayList(),
        warehouseTasks: ArrayList<String> = ArrayList(),
        purchaseOrders: ArrayList<String> = ArrayList(),
        inboundDelivery: ArrayList<String> = ArrayList(),
        products: ArrayList<String> = ArrayList(),
        status: String? = null
    ) {
        var wareHouseOrderPayload: String? = null
        var wareHouseTasksPayload: String? = null
        var purchaseOrderPayload: String? = null
        var inboundPayload: String? = null
        var productPayload: String? = null

        if (warehouseOrders.isNotEmpty()) {
            wareHouseOrderPayload = warehouseOrders.join(",")
        }

        if (warehouseTasks.isNotEmpty()) {
            wareHouseTasksPayload = warehouseTasks.join(",")
        }

        if (purchaseOrders.isNotEmpty()) {
            purchaseOrderPayload = purchaseOrders.join(",")
        }

        if (inboundDelivery.isNotEmpty()) {
            inboundPayload = inboundDelivery.join(",")
        }

        if (products.isNotEmpty()) {
            productPayload = products.join(",")
        }

        getPutAwayWarehouseTasks(warehouse,wareHouseOrderPayload,wareHouseTasksPayload,purchaseOrderPayload,inboundPayload,productPayload,status)

    }


     private fun getPutAwayWarehouseTasks(warehouse:String, warehouseOrder:String?, warehouseTask:String?, purchaseOrder:String?, inboundDelivery:String?, product:String?, status:String?) = mainUseCase.getPutAwayWarehouseTasks(warehouse,warehouseOrder,warehouseTask,purchaseOrder,inboundDelivery,product,status).onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _uiState.emit( WarehouseTasksStateHolder(isLoading = true) )
            }

            is NetworkResult.Success -> {
                _uiState.emit( WarehouseTasksStateHolder(data = res.data) )

            }

            is NetworkResult.Error -> {
                _uiState.emit( WarehouseTasksStateHolder(error = res.message) )
            }
        }
    }.launchIn(viewModelScope)

    fun preParePayload(items:List<WarehouseTaskModel>)
    {
        val payloads = ArrayList<ConfirmWareHouseTaskModel>()

        items.forEach { details ->
            ConfirmWareHouseTaskModel().apply {
                userId = localSharedStorage.getUserId()
                warehouse = details.warehouse
                warehouseTask = details.woTaskId
                warehouseOrder = details.wo
                if (details.destBin.lowercase() == details.selectedDestBin.toString().lowercase()) {
                    if (details.qty.toDouble()==details.enteredQty?.toDouble())
                    {
                        destinationStorageBin = ""
                        destinationStorageBinDummy = details.destBin
                        whseTaskExCodeDestStorageBin = ""
                    }else{
                        destinationStorageBin =  details.selectedDestBin.toString()
                        destinationStorageBinDummy = details.selectedDestBin.toString()
                        whseTaskExCodeDestStorageBin = ""
                    }

                } else {
                    destinationStorageBin = details.selectedDestBin.toString()
                    destinationStorageBinDummy = details.selectedDestBin.toString()
                    whseTaskExCodeDestStorageBin = StringResources.PutawayExceptionCodes.CHBD
                }
                alternativeUnit = details.comUom
                actualQuantityInAltvUnit = details.enteredQty.toString()
                isHandlingUnitWarehouseTask=details.isHandlingUnitWarehouseTask
                batch=details.batch
                serialNumbers=""
                differenceQuantityInAltvUnit=""
                sourceHandlingUnit=""
                whseTaskExceptionCodeQtyDiff=""
                isHandlingUnitWarehouseTask=false
                payloads.add(this)
            }
        }

        postPutAway(payloads)
    }

    private fun postPutAway(lines:ArrayList<ConfirmWareHouseTaskModel>) = mainUseCase.postPutAway(lines).onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _uiPutAwayTransferState.emit( PutAwayTransferStateHolder(isLoading = true) )
            }

            is NetworkResult.Success -> {

                val successBuilder = StringBuilder()
                var successCount = 0
                val errorBuilder = StringBuilder()
                var errorCount = 0


                res.data?.forEach {
                    if (it.status)
                    {
                        successCount++
                        successBuilder.append(it.warehouseTask+" - "+it.responseMsg)
                        successBuilder.append("\n")
                    }else{
                        errorCount++
                        errorBuilder.append(it.warehouseTask+" - "+it.responseMsg)
                        errorBuilder.append("\n")
                    }
                }

                _uiPutAwayTransferState.emit( PutAwayTransferStateHolder(data = res.data , successCount = successCount , successBuilder = successBuilder.toString() , errorCount = errorCount, errorBuilder = errorBuilder.toString()) )

            }

            is NetworkResult.Error -> {
                _uiPutAwayTransferState.emit( PutAwayTransferStateHolder(error = res.message) )
            }
        }
    }.launchIn(viewModelScope)

    fun getOpenWHOButtonWidth():Modifier
    {
        return if (platformUtils.isTablet()) {
            Modifier.width(200.dp).padding(5.dp)
        }else{
            Modifier.width(150.dp).padding(5.dp)
        }

    }

    fun getOpenWHOColumnCount():Int
    {
        return if (platformUtils.isTablet()) {
            3
        }else{
            2
        }
    }

    fun getOpenWHOColumnHeight(): Dp
    {
        return if (platformUtils.isTablet()) {
            100.dp
        }else{
            150.dp
        }
    }

}