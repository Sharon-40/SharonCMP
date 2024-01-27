package data.network

import StringResources
import data.model.ProductModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

class ApiInterfaceImpl(private val httpClient: HttpClient) : ApiInterface {
    override suspend fun getProfile(userId:String): HttpResponse {
        return httpClient.get {
            url("${StringResources.BASEURL}/label/userDetail")
            parameter("username", userId)
        }
    }

    override suspend fun getProducts(): List<ProductModel> {
        return httpClient.get("https://fakestoreapi.com/products/").body<List<ProductModel>>()
    }

    override suspend fun getPutAwayWarehouseTasks(warehouse:String,processCategory:String, warehouseOrder:String?, warehouseTask:String?, purchaseOrder:String?, inboundDelivery:String?, product:String?, status:String?): HttpResponse {
        return httpClient.get {
            url("${StringResources.BASEURL}/allWarehouseTasksPutaway")
            parameter("EWMWarehouse", warehouse)
            parameter("WarehouseProcessCategory", processCategory)
            parameter("WarehouseOrder", warehouseOrder)
            parameter("WarehouseTask", warehouseTask)
            parameter("PurchasingDocument", purchaseOrder)
            parameter("EWMDelivery", inboundDelivery)
            parameter("Product", product)
            parameter("WarehouseTaskStatus", status)
        }
    }

}