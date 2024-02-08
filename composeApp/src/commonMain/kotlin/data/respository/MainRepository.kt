package data.respository

import data.local_db.LocalDbDao
import data.model.BinTransferModel
import data.network.ApiInterfaceImpl
import io.ktor.client.statement.HttpResponse

class MainRepository(private val apiInterfaceImpl: ApiInterfaceImpl,private val localDbDao: LocalDbDao) {

    suspend fun getProfile(userId:String): HttpResponse {
        return apiInterfaceImpl.getProfile(userId)
    }

    suspend fun getTasks(): HttpResponse {
        return apiInterfaceImpl.getTasks()
    }

    suspend fun getPutAwayWarehouseTasks(warehouse:String, warehouseOrder:String?, warehouseTask:String?, purchaseOrder:String?, inboundDelivery:String?, product:String?, status:String?): HttpResponse {
        return apiInterfaceImpl.getPutAwayWarehouseTasks(warehouse,"1",warehouseOrder,warehouseTask,purchaseOrder,inboundDelivery,product,status)
    }

    suspend fun getStockByBin(bin:String): HttpResponse {
        return apiInterfaceImpl.getStockByBin(bin)
    }

    suspend fun postBinTransfer(transactions:ArrayList<BinTransferModel>): HttpResponse {
        return apiInterfaceImpl.postBinTransfer(transactions)
    }

    suspend fun getAccessTokenByCode(code:String): HttpResponse {
        return apiInterfaceImpl.getAccessTokenByCode(code)
    }

    suspend fun getAccessTokenByRefreshToken(refreshToken:String): HttpResponse {
        return apiInterfaceImpl.getAccessTokenByRefreshToken(refreshToken)
    }

    suspend fun insert(id: Int, title: String, desc: String, image: String) {
        localDbDao.insert(id = id, title = title, desc = desc, image = image)
    }

    suspend fun delete(id: Int) {
        localDbDao.delete(id)
    }
}