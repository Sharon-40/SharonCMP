package data.network

import data.oauth.OAuthConfig
import StringResources
import data.model.bintobin.BinTransferModel
import data.model.ProductModel
import data.model.putaway.ConfirmWareHouseTaskBatchResponseModel
import data.model.putaway.ConfirmWareHouseTaskModel
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
import io.ktor.http.contentType

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

    override suspend fun postBinTransfer(transactions:ArrayList<BinTransferModel>): HttpResponse {
        return httpClient.post {
            url("${StringResources.BASEURL}/bin/binTransfer")
            parameter("Plant",localSharedStorage.getPlant())
            parameter("EWMWarehouse", localSharedStorage.getWareHouse())
            contentType(ContentType.Application.Json)
            setBody(transactions)
        }
    }

    override suspend fun getAccessTokenByCode(code:String): HttpResponse {
        return httpClient.post {
            url(OAuthConfig.TOKEN_END_POINT)
            parameter("grant_type","authorization_code")
            parameter("code",code)
            parameter("client_id", OAuthConfig.CLIENT_ID)
            parameter("redirect_uri", OAuthConfig.REDIRECT_URL)
        }
    }

    override suspend fun getAccessTokenByRefreshToken(refreshToken:String): HttpResponse {
        return httpClient.post {
            url(OAuthConfig.TOKEN_END_POINT)
            parameter("grant_type","refresh_token")
            parameter("refresh_token",refreshToken)
            parameter("client_id", OAuthConfig.CLIENT_ID)
            parameter("redirect_uri", OAuthConfig.REDIRECT_URL)
        }
    }

    override suspend fun postPutAway(transactions:ArrayList<ConfirmWareHouseTaskModel>): HttpResponse {
        return httpClient.post {
            url("${StringResources.BASEURL}/batch/confirmWarehouseTaskBatch")
            parameter("Plant",localSharedStorage.getPlant())
            parameter("EWMWarehouse", localSharedStorage.getWareHouse())
            contentType(ContentType.Application.Json)
            setBody(transactions)
        }
    }

    override suspend fun putAwayBatchLocationUrl(transactions: ConfirmWareHouseTaskBatchResponseModel): HttpResponse {
        return httpClient.post {
            url("${StringResources.BASEURL}/batch/callOdataLocationUrl")
            parameter("Plant",localSharedStorage.getPlant())
            parameter("EWMWarehouse", localSharedStorage.getWareHouse())
            contentType(ContentType.Application.Json)
            setBody(transactions)
        }
    }

}