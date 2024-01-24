package data.respository

import data.local_db.LocalDbDao
import data.model.ProductModel
import data.model.StandardResponse
import data.model.UserModel
import data.network.ApiInterfaceImpl

class MainRepository(private val apiInterfaceImpl: ApiInterfaceImpl,private val localDbDao: LocalDbDao) {

    suspend fun getProfile(userId:String): StandardResponse<ArrayList<UserModel>> {
        return apiInterfaceImpl.getProfile(userId)
    }

    suspend fun getProducts(): List<ProductModel> {
        return apiInterfaceImpl.getProducts()
    }
    suspend fun insert(id: Int, title: String, desc: String, image: String) {
        localDbDao.insert(id = id, title = title, desc = desc, image = image)
    }

    suspend fun delete(id: Int) {
        localDbDao.delete(id)
    }
}