package presentation.viewmodels

import data.model.TaskModel
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

data class TaskListStateHolder(
    val isLoading: Boolean = false,
    val data: List<TaskModel>? = null,
    val error: String = ""
)

class ProductListViewModel(private val mainUseCase: MainUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskListStateHolder())
    val uiState: StateFlow<TaskListStateHolder> = _uiState.asStateFlow()

    init {
        getTasks()
    }

    private fun getTasks() = mainUseCase.getTasks().onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _uiState.update { TaskListStateHolder(isLoading = true) }
            }

            is NetworkResult.Success -> {
                _uiState.update { TaskListStateHolder(data = res.data) }

            }

            is NetworkResult.Error -> {
                _uiState.update { TaskListStateHolder(error = res.message) }
            }
        }
    }.launchIn(viewModelScope)

}