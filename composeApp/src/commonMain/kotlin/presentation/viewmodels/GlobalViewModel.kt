package presentation.viewmodels

import data.model.StorageTypeModel
import data.utils.NetworkResult
import domain.use_cases.MainUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class StorageTypeStateHolder(
    val isLoading: Boolean = false,
    val data: List<StorageTypeModel>? = null,
    val error: String = ""
)

class GlobalViewModel(private val mainUseCase: MainUseCase) : ViewModel() {

    val _uiStorageTypeState = MutableSharedFlow<StorageTypeStateHolder>()

     fun getStorageTypes() = mainUseCase.getStorageTypes().onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _uiStorageTypeState.emit(StorageTypeStateHolder(isLoading = true))
            }

            is NetworkResult.Success -> {
                _uiStorageTypeState.emit(StorageTypeStateHolder(data = res.data) )

            }

            is NetworkResult.Error -> {
                _uiStorageTypeState.emit( StorageTypeStateHolder(error = res.message) )
            }
        }
    }.launchIn(viewModelScope)


    fun getStorageTypeList(data: List<StorageTypeModel>?):ArrayList<String>
    {
        val items=ArrayList<String>()
        data?.forEach {
            items.add(it.storageType)
        }
        return items
    }

}