package presentation.viewmodels

import data.model.putaway.WarehouseTaskModel
import data.utils.NetworkResult
import domain.use_cases.MainUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class WarehouseTasksStateHolder(
    val isLoading: Boolean = false,
    val data: ArrayList<WarehouseTaskModel>? = null,
    val error: String = ""
)

class CustomComponentsViewModel(private val mainUseCase: MainUseCase) : ViewModel() {

    val _uiState = MutableSharedFlow<WarehouseTasksStateHolder>()

    fun getPutAwayWarehouseTasks(warehouse:String, warehouseOrder:String?, warehouseTask:String?, purchaseOrder:String?, inboundDelivery:String?, product:String?, status:String?) = mainUseCase.getPutAwayWarehouseTasks(warehouse,warehouseOrder,warehouseTask,purchaseOrder,inboundDelivery,product,status).onEach { res ->
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