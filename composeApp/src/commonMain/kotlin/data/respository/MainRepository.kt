package data.respository

import data.local_db.LocalDbDao
import data.model.ProductModel
import data.network.ApiInterfaceImpl
import io.ktor.client.statement.HttpResponse

class MainRepository(private val apiInterfaceImpl: ApiInterfaceImpl,private val localDbDao: LocalDbDao) {

    suspend fun getProfile(userId:String): HttpResponse {
        return apiInterfaceImpl.getProfile(userId)
    }

    suspend fun getProducts(): List<ProductModel> {
        return apiInterfaceImpl.getProducts()
    }

    suspend fun getPutAwayWarehouseTasks(warehouse:String, warehouseOrder:String?, warehouseTask:String?, purchaseOrder:String?, inboundDelivery:String?, product:String?, status:String?): HttpResponse {
        return apiInterfaceImpl.getPutAwayWarehouseTasks(warehouse,"1",warehouseOrder,warehouseTask,purchaseOrder,inboundDelivery,product,status)
    }

    suspend fun insert(id: Int, title: String, desc: String, image: String) {
        localDbDao.insert(id = id, title = title, desc = desc, image = image)
    }

    suspend fun delete(id: Int) {
        localDbDao.delete(id)
    }
}