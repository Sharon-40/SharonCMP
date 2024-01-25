package domain.use_cases

import data.model.StandardResponse
import data.model.UserModel
import data.utils.NetworkResult
import data.respository.MainRepository
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
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

        val response=mainRepository.getProfile(userId)

        LogUtils.logDebug(LogUtils.RESPONSE_CODE,response.status.value.toString())

        if (response.status== HttpStatusCode.OK)
        {
            val result=response.body<StandardResponse<ArrayList<UserModel>>>()

            if (result.status)
            {
                emit(NetworkResult.Success(data = result.data?.first() ))
            }else{
                emit(NetworkResult.Error(result.message))
            }
        }else{
            emit(NetworkResult.Error(response.status.toString()))
        }

    }.catch {
        emit(NetworkResult.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

}