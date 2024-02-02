package data.network

import StringResources
import data.model.BinTransferModel
import data.model.ProductModel
import data.preferences.LocalSharedStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json

class ApiInterfaceImpl(private val httpClient: HttpClient,private val localSharedStorage: LocalSharedStorage) : ApiInterface {
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

    override suspend fun getStockByBin(bin: String): HttpResponse {
        return httpClient.get {
            url("${StringResources.BASEURL}/getAvailStock")
            parameter("Plant",localSharedStorage.getPlant())
            parameter("EWMWarehouse", localSharedStorage.getWareHouse())
            parameter("StorageBin", bin)
            parameter("Language","EN")
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun postBinTransfer(transactions:ArrayList<BinTransferModel>): HttpResponse {
        return httpClient.post {
            url("${StringResources.BASEURL}/bin/binTransfer")
            parameter("Plant",localSharedStorage.getPlant())
            parameter("EWMWarehouse", localSharedStorage.getWareHouse())
            contentType(ContentType.Application.Json)
            setBody(transactions)
        }
    }

}