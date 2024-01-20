package data.network

import data.model.ProductDTO
import domain.mappers.toDomain
import domain.model.Product
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiInterfaceImpl(private val httpClient: HttpClient) : ApiInterface {

    override suspend fun getProducts(): List<Product> {
        return httpClient.get("https://fakestoreapi.com/products/").body<List<ProductDTO>>()
            .toDomain()
    }
}