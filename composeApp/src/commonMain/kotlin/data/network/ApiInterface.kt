package data.network

import data.model.ProductModel
import data.model.StandardResponse
import data.model.UserModel

interface ApiInterface {

    suspend fun getProfile(userId:String): StandardResponse<ArrayList<UserModel>>
    suspend fun getProducts(): List<ProductModel>

}