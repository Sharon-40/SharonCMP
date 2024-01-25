package data.preferences

import com.liftric.kvault.KVault

class LocalSharedStorage(private val store: KVault) {

    fun saveUserId(name: String) {
        store.set(USER_ID, name)
    }

    fun getUserId(): String {
        return store.string(USER_ID) ?: ""
    }

    fun saveUserName(name: String) {
        store.set(USER_NAME, name)
    }


    fun getUserName(): String {
        return store.string(USER_NAME) ?: ""
    }


    fun savePlant(name: String) {
        store.set(PREF_PLANT, name)
    }


    fun getPlant(): String {
        return store.string(PREF_PLANT) ?: ""
    }


    fun saveWareHouse(name: String) {
        store.set(PREF_WAREHOUSE, name)
    }


    fun getWareHouse(): String {
        return store.string(PREF_WAREHOUSE) ?: ""
    }

    fun savePrinter(name: String) {
        store.set(PREF_PRINTER, name)
    }

    fun getPrinter(): String {
        return store.string(PREF_PRINTER) ?: ""
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
