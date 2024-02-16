package presentation.viewmodels

import StringResources
import data.logs.LogUtils
import data.model.WarehouseTaskModel
import data.utils.NetworkResult
import domain.use_cases.MainUseCase
import join
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class PutAwayViewModel(private val mainUseCase: MainUseCase) : ViewModel() {

    val _openWhoState = MutableSharedFlow<WarehouseTasksStateHolder>()

    val _uiState = MutableSharedFlow<WarehouseTasksStateHolder>()

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


}