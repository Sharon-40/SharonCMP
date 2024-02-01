package presentation.viewmodels

import data.model.BinTransferModel
import data.model.ConfirmTaskResponse
import data.model.StockModel
import data.preferences.LocalSharedStorage
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

data class StockStateHolder(
    val isLoading: Boolean = false,
    val data: List<StockModel>? = null,
    val error: String = ""
)

data class BinTransferStateHolder(
    val isLoading: Boolean = false,
    val data: List<ConfirmTaskResponse>? = null,
    val error: String = "",
    val successBuilder : String="",
    var successCount:Int = 0,
    val errorBuilder: String="",
    var errorCount:Int = 0

)

class BinToBinViewModel(private val mainUseCase: MainUseCase,private val localSharedStorage: LocalSharedStorage) : ViewModel() {

    private val _uiState = MutableStateFlow(StockStateHolder())
    val uiState: StateFlow<StockStateHolder> = _uiState.asStateFlow()

    private val _uiBinTransferState = MutableStateFlow(BinTransferStateHolder())
    val uiBinTransferState: StateFlow<BinTransferStateHolder> = _uiBinTransferState.asStateFlow()

     fun getStockByBin(bin:String) = mainUseCase.getStockByBin(bin).onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _uiState.update { StockStateHolder(isLoading = true) }
            }

            is NetworkResult.Success -> {
                _uiState.update { StockStateHolder(data = res.data) }

            }

            is NetworkResult.Error -> {
                _uiState.update { StockStateHolder(error = res.message) }
            }
        }
    }.launchIn(viewModelScope)

    fun getSelectedData(items:List<StockModel>): List<StockModel> {
        return items.filter { it.isSelected }
    }


    fun prePareItemPayload(center:String,items:List<StockModel>)
    {

        val payloads= ArrayList<BinTransferModel>()

        items.forEach { item->

            if (item.isSelected)
            {
                BinTransferModel().apply {
                    warehouse=item.warehouse
                    workCenter=center
                    product= item.product
                    targetQuantityInAltvUnit=item.enteredQty?.toDouble()?:0.0
                    alternativeUnit=item.uom
                    sourceStorageType=item.storageType
                    sourceStorageBin=item.storageBin
                    destinationStorageType=item.selectedDestStorageType?:""
                    destinationStorageBin=item.selectedDestBin?:""
                    batch=item.batch
                    isAutoPutaway=true
                    ewmdocumentCategory=item.ewmdocumentCategory
                    entitledToDisposeParty=item.entitledToDisposeParty
                    userId= localSharedStorage.getUserId()
                    warehouseProcessType="9999"
                    eWMStockType=item.stockType
                    eWMStockOwner=item.ewmstockOwner
                    payloads.add(this)
                }
            }
        }

        postBinTransfer(payloads)

    }

    private fun postBinTransfer(lines:ArrayList<BinTransferModel>) = mainUseCase.postBinTransfer(lines).onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _uiBinTransferState.update { BinTransferStateHolder(isLoading = true) }
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
                        successBuilder.append(it.responseMsg)
                        successBuilder.append("\n")
                    }else{
                        errorCount++
                        errorBuilder.append(it.responseMsg)
                        errorBuilder.append("\n")
                    }
                }

                _uiBinTransferState.update { BinTransferStateHolder(data = res.data , successCount = successCount , successBuilder = successBuilder.toString() , errorCount = errorCount, errorBuilder = errorBuilder.toString()) }

            }

            is NetworkResult.Error -> {
                _uiBinTransferState.update { BinTransferStateHolder(error = res.message) }
            }
        }
    }.launchIn(viewModelScope)


}