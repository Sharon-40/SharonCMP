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

    fun saveAuthCode(name: String) {
        store.putString(AUTH_CODE, name)
    }

    fun getAuthCode(): String {
        return store.getString(AUTH_CODE,"")
    }

    fun saveAccessToken(name: String) {
        store.putString(ACCESS_TOKEN, name)
    }

    fun getAccessToken(): String {
        return store.getString(ACCESS_TOKEN,"")
    }

    fun saveRefreshToken(name: String) {
        store.putString(REFRESH_TOKEN, name)
    }

    fun getRefreshToken(): String {
        return store.getString(REFRESH_TOKEN,"")
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
        private const val AUTH_CODE = "AUTH_CODE"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
    }

}
