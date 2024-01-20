package data.network

import domain.model.Product

interface ApiInterface {

    suspend fun getProducts(): List<Product>

}