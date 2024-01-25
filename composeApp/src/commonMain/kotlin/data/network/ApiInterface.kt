package data.network

import data.model.ProductModel
import io.ktor.client.statement.HttpResponse

interface ApiInterface {

    suspend fun getProfile(userId:String): HttpResponse
    suspend fun getProducts(): List<ProductModel>

}