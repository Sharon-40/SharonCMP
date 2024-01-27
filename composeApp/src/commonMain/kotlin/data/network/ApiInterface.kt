package data.network

import data.model.ProductModel
import io.ktor.client.statement.HttpResponse

interface ApiInterface {

    suspend fun getProfile(userId:String): HttpResponse
    suspend fun getProducts(): List<ProductModel>

    suspend fun getPutAwayWarehouseTasks(warehouse: String, processCategory: String, warehouseOrder: String?, warehouseTask: String?, purchaseOrder: String?, inboundDelivery: String?, product: String?, status: String?): HttpResponse
}