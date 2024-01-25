package data.local_db

interface LocalDBInterface {

    suspend fun insert(id: Int, title: String, desc: String, image: String)

    suspend fun delete(id: Int)

}