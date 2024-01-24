package presentation.viewmodels

import data.model.UserModel
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

data class LoginStateHolder(
    val isLoading: Boolean = false,
    val data: UserModel? = null,
    val error: String = ""
)

class LoginViewModel(private val mainUseCase: MainUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginStateHolder())
    val uiState: StateFlow<LoginStateHolder> = _uiState.asStateFlow()

     fun getProfile(userId:String) = mainUseCase.getProfile(userId).onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _uiState.update { LoginStateHolder(isLoading = true) }
            }

            is NetworkResult.Success -> {
                _uiState.update { LoginStateHolder(data = res.data) }
            }

            is NetworkResult.Error -> {
                _uiState.update { LoginStateHolder(error = res.message) }
            }
        }
    }.launchIn(viewModelScope)

}