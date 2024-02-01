package data.preferences

import com.russhwolf.settings.Settings

class LocalSharedStorage(private val store: Settings) {

    fun saveUserId(name: String) {
        store.putString(USER_ID, name)
    }

    fun getUserId(): String {
        return store.getString(USER_ID,"")
    }

    fun saveUserName(name: String) {
        store.putString(USER_NAME, name)
    }


    fun getUserName(): String {
        return store.getString(USER_NAME,"")
    }


    fun savePlant(name: String) {
        store.putString(PREF_PLANT, name)
    }


    fun getPlant(): String {
        return store.getString(PREF_PLANT,"")
    }


    fun saveWareHouse(name: String) {
        store.putString(PREF_WAREHOUSE, name)
    }


    fun getWareHouse(): String {
        return store.getString(PREF_WAREHOUSE,"")
    }

    fun savePrinter(name: String) {
        store.putString(PREF_PRINTER, name)
    }

    fun getPrinter(): String {
        return store.getString(PREF_PRINTER,"")
    }

    fun clearAll() {
        store.clear()
    }

    companion object {
        private const val USER_ID = "USER_ID"
        private const val USER_NAME = "USER_NAME"
        private const val PREF_PLANT = "PREF_PLANT"
        private const val PREF_WAREHOUSE = "PREF_WAREHOUSE"
        private const val PREF_PRINTER = "PREF_PRINTER"
    }

}
