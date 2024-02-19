package data.network

import data.model.bintobin.BinTransferModel
import data.model.ProductModel
import data.model.putaway.ConfirmWareHouseTaskBatchResponseModel
import data.model.putaway.ConfirmWareHouseTaskModel
import io.ktor.client.statement.HttpResponse

interface ApiInterface {

    suspend fun getProfile(userId:String): HttpResponse
    suspend fun getProducts(): List<ProductModel>

    suspend fun getPutAwayWarehouseTasks(warehouse: String, processCategory: String, warehouseOrder: String?, warehouseTask: String?, purchaseOrder: String?, inboundDelivery: String?, product: String?, status: String?): HttpResponse
    suspend fun getStockByBin(bin: String): HttpResponse
    suspend fun postBinTransfer(transactions: ArrayList<BinTransferModel>): HttpResponse
    suspend fun getAccessTokenByCode(code: String): HttpResponse
    suspend fun getAccessTokenByRefreshToken(refreshToken: String): HttpResponse
    suspend fun postPutAway(transactions: ArrayList<ConfirmWareHouseTaskModel>): HttpResponse
    suspend fun putAwayBatchLocationUrl(transactions: ConfirmWareHouseTaskBatchResponseModel): HttpResponse
    suspend fun getStorageTypes(): HttpResponse
}