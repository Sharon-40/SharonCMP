package domain.use_cases

import data.utils.NetworkResult
import data.respository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent

class MainUseCase(private val mainRepository: MainRepository) : KoinComponent {

    fun getProducts() = flow {
        emit(NetworkResult.Loading())
        emit(NetworkResult.Success(data = mainRepository.getProducts()))
    }.catch {
        emit(NetworkResult.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getProfile(userId:String) = flow {
        emit(NetworkResult.Loading())
        val result=mainRepository.getProfile(userId)
        if (result.status)
        {
            emit(NetworkResult.Success(data = result.data?.first() ))
        }else{
            emit(NetworkResult.Error(result.message))
        }
    }.catch {
        emit(NetworkResult.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

}