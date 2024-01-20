package data.network

import data.model.ProductModel

interface ApiInterface {

    suspend fun getProducts(): List<ProductModel>

}