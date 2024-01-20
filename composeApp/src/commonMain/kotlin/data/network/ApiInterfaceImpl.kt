package data.network

import data.model.ProductModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiInterfaceImpl(private val httpClient: HttpClient) : ApiInterface {

    override suspend fun getProducts(): List<ProductModel> {
        return httpClient.get("https://fakestoreapi.com/products/").body<List<ProductModel>>()
    }
}