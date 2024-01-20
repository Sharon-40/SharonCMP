package data.respository

import data.local_db.LocalDbDao
import data.network.ApiInterfaceImpl
import domain.model.Product

class MainRepository(private val apiInterfaceImpl: ApiInterfaceImpl,private val localDbDao: LocalDbDao) {

    suspend fun getProducts(): List<Product> {
        return apiInterfaceImpl.getProducts()
    }
    suspend fun insert(id: Int, title: String, desc: String, image: String) {
        localDbDao.insert(id = id, title = title, desc = desc, image = image)
    }

    suspend fun delete(id: Int) {
        localDbDao.delete(id)
    }
}