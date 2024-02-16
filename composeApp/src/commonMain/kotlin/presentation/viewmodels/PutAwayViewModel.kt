package presentation.viewmodels

import data.model.WarehouseTaskModel
import data.utils.NetworkResult
import domain.use_cases.MainUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class PutAwayViewModel(private val mainUseCase: MainUseCase) : ViewModel() {

    val _openWhoState = MutableSharedFlow<WarehouseTasksStateHolder>()

    fun getOpenWareHouseTasks(warehouse:String,status:String?) = mainUseCase.getPutAwayWarehouseTasks(warehouse,null,null,null,null,null,status).onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _openWhoState.emit( WarehouseTasksStateHolder(isLoading = true) )
            }

            is NetworkResult.Success -> {
                _openWhoState.emit( WarehouseTasksStateHolder(data = res.data) )
            }

            is NetworkResult.Error -> {
                _openWhoState.emit( WarehouseTasksStateHolder(error = res.message) )
            }
        }
    }.launchIn(viewModelScope)

    fun getSelectedData(items:List<WarehouseTaskModel>): List<WarehouseTaskModel> {
        return items.filter { it.isSelected }
    }


}