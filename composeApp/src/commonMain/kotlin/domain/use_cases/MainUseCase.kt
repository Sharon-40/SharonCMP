package domain.use_cases

import StringResources
import data.logs.LogUtils
import data.model.bintobin.BinTransferModel
import data.model.container.ConfirmTaskResponse
import data.model.container.StandardResponse
import data.model.bintobin.StockModel
import data.model.UserModel
import data.model.putaway.ConfirmWareHouseTaskBatchResponseModel
import data.model.putaway.ConfirmWareHouseTaskModel
import data.model.putaway.WarehouseTaskModel
import data.utils.NetworkResult
import data.respository.MainRepository
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
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

        if (response.status== HttpStatusCode.OK)
        {
            LogUtils.logDebug(StringResources.RESPONSE_CODE,response.status.toString())

            val result=response.body<StandardResponse<ArrayList<UserModel>>>()

            LogUtils.logDebug(StringResources.RESPONSE,result.data.toString())

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

    fun getPutAwayWarehouseTasks(warehouse:String, warehouseOrder:String?, warehouseTask:String?, purchaseOrder:String?, inboundDelivery:String?, product:String?, status:String?) = flow {

        emit(NetworkResult.Loading())

        val response=mainRepository.getPutAwayWarehouseTasks(warehouse,warehouseOrder,warehouseTask,purchaseOrder,inboundDelivery,product,status)

        if (response.status== HttpStatusCode.OK)
        {
            LogUtils.logDebug(StringResources.RESPONSE_CODE,response.status.toString())

            val result=response.body<StandardResponse<ArrayList<WarehouseTaskModel>>>()

            LogUtils.logDebug(StringResources.RESPONSE,result.data.toString())

            if (result.status)
            {
                emit(NetworkResult.Success(data = result.data))
            }else{
                emit(NetworkResult.Error(result.message))
            }
        }else{
            emit(NetworkResult.Error(response.status.toString()))
        }

    }.catch {
        emit(NetworkResult.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getStockByBin(bin:String) = flow {

        emit(NetworkResult.Loading())

        val response=mainRepository.getStockByBin(bin)

        if (response.status== HttpStatusCode.OK)
        {
            LogUtils.logDebug(StringResources.RESPONSE_CODE,response.status.toString())

            val result=response.body<StandardResponse<ArrayList<StockModel>>>()

            LogUtils.logDebug(StringResources.RESPONSE,result.data.toString())

            if (result.status)
            {
                emit(NetworkResult.Success(data = result.data))
            }else{
                emit(NetworkResult.Error(result.message))
            }
        }else{
            emit(NetworkResult.Error(response.status.toString()))
        }

    }.catch {
        emit(NetworkResult.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun postBinTransfer(transactions:ArrayList<BinTransferModel>) = flow {

        emit(NetworkResult.Loading())

        val response=mainRepository.postBinTransfer(transactions)

        if (response.status== HttpStatusCode.OK)
        {
            LogUtils.logDebug(StringResources.RESPONSE_CODE,response.status.toString())

            val result=response.body<StandardResponse<ArrayList<ConfirmTaskResponse>>>()

            LogUtils.logDebug(StringResources.RESPONSE,result.data.toString())

            emit(NetworkResult.Success(data = result.data))

        }else{
            emit(NetworkResult.Error(response.status.toString()))
        }

    }.catch {
        emit(NetworkResult.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun postPutAway(transactions:ArrayList<ConfirmWareHouseTaskModel>) = flow {

        emit(NetworkResult.Loading())

        LogUtils.logDebug(StringResources.RESPONDED,transactions.toString())
        val response=mainRepository.postPutAway(transactions)

        if (response.status== HttpStatusCode.OK)
        {
            LogUtils.logDebug(StringResources.RESPONSE_CODE,response.status.toString())

            val result=response.body<StandardResponse<ConfirmWareHouseTaskBatchResponseModel>>()

            if (result.data!=null)
            {

                delay(10000)

                val responseLocationUrl=mainRepository.putAwayBatchLocationUrl(result.data!!)

                if (responseLocationUrl.status== HttpStatusCode.OK){

                    val resultLocationUrl=responseLocationUrl.body<StandardResponse<ArrayList<ConfirmTaskResponse>>>()

                    emit(NetworkResult.Success(data = resultLocationUrl.data))
                }else{
                    emit(NetworkResult.Error(response.status.toString()))
                }

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