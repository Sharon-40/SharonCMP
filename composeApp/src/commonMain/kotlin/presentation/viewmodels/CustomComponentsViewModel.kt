package presentation.viewmodels

import data.model.WarehouseTaskModel
import data.utils.NetworkResult
import domain.use_cases.MainUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class WarehouseTasksStateHolder(
    val isLoading: Boolean = false,
    val data: ArrayList<WarehouseTaskModel>? = null,
    val error: String = ""
)

class CustomComponentsViewModel(private val mainUseCase: MainUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(WarehouseTasksStateHolder())
    val uiState: StateFlow<WarehouseTasksStateHolder> = _uiState.asStateFlow()

    fun getPutAwayWarehouseTasks(warehouse:String, warehouseOrder:String?, warehouseTask:String?, purchaseOrder:String?, inboundDelivery:String?, product:String?, status:String?) = mainUseCase.getPutAwayWarehouseTasks(warehouse,warehouseOrder,warehouseTask,purchaseOrder,inboundDelivery,product,status).onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _uiState.update { WarehouseTasksStateHolder(isLoading = true) }
            }

            is NetworkResult.Success -> {
                _uiState.update { WarehouseTasksStateHolder(data = res.data) }

            }

            is NetworkResult.Error -> {
                _uiState.update { WarehouseTasksStateHolder(error = res.message) }
            }
        }
    }.launchIn(viewModelScope)

}