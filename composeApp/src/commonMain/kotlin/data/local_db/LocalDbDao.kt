package data.local_db

import app_db.AppDatabase

class LocalDbDao(private val appDatabase: AppDatabase) : LocalDBInterface {

    override suspend fun insert(id: Int, title: String, desc: String, image: String) {
        appDatabase.appDatabaseQueries.insert(
            id = id.toLong(),
            title = title,
            desc = desc,
            image = image
        )
    }

    override suspend fun delete(id: Int) {
        appDatabase.appDatabaseQueries.delete(id.toLong())
    }
}